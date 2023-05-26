package com.java.basics.controller;

import com.java.basics.exception.JWTSecurityException;
import com.java.basics.model.ERole;
import com.java.basics.model.RefreshToken;
import com.java.basics.model.Role;
import com.java.basics.model.Users;
import com.java.basics.repository.RoleRepository;
import com.java.basics.request.LoginRequest;
import com.java.basics.request.SignupRequest;
import com.java.basics.request.TokenRefreshRequest;
import com.java.basics.response.MessageResponse;
import com.java.basics.response.ResponseHandler;
import com.java.basics.response.TokenRefreshResponse;
import com.java.basics.response.UserInfoResponse;
import com.java.basics.security.jwt.JwtUtils;
import com.java.basics.security.services.RefreshTokenService;
import com.java.basics.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.java.basics.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(), refreshToken.getToken(), userDetails.getUsername(),
                        userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Users users = new Users(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        List<String> strRoles = signUpRequest.getRoles();
        List<Role> roles = new ArrayList<>();

        if (strRoles == null) {
            System.out.println("null role so user");
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    case "user":
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        users.setRoles(roles);
        userRepository.save(users);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser).map(users -> {
                    String token = jwtUtils.generateTokenFromUsername(users.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new JWTSecurityException(requestRefreshToken, "Refresh token is not in database!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<Object> processForgotPassword(@RequestParam String username) {
        NativeEntryPoint RandomString = null;
        String token = RandomString.make(30);

        try {
            String passwordResetToken = updateResetPasswordToken(token, username);
            return ResponseHandler.generateResponse("You can now reset your password", true, HttpStatus.OK, passwordResetToken);

        } catch (Exception ex) {
            return ResponseHandler.generateResponse("Could not reset password", false, HttpStatus.OK,null);
        }
    }

    private String updateResetPasswordToken(String token, String username){
        Optional<User> user= userRepository.findByUsername(username);
        if (user.isPresent()) {
            user.get().getPassword(token);
            userRepository.save(user.get());
            return user.get().getPassword();
        } else {
            throw new JWTSecurityException(token,"Could not find any customer with the username " + username);
        }
    }

    @PostMapping("/reset_password")
    public void updatePassword(@RequestBody Users users, @RequestParam String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        users.setPassword(encodedPassword);

        users.setPasswordResetToken(null);
        S save = userRepository.save(users);
    }

    @GetMapping("/get_user_by_reset_token")
    public User getByResetPasswordToken(@RequestParam String token) {
        return userRepository.findByPasswordResetToken(token).get();
    }
}