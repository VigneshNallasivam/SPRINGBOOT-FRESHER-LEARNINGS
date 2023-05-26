package com.springappdemo.springjwt.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.springappdemo.springjwt.exception.JWTSecurityException;
import com.springappdemo.springjwt.models.ERole;
import com.springappdemo.springjwt.models.RefreshToken;
import com.springappdemo.springjwt.models.Role;
import com.springappdemo.springjwt.models.User;
import com.springappdemo.springjwt.payload.request.LoginRequest;
import com.springappdemo.springjwt.payload.request.SignupRequest;
import com.springappdemo.springjwt.payload.request.TokenRefreshRequest;
import com.springappdemo.springjwt.payload.response.MessageResponse;
import com.springappdemo.springjwt.payload.response.ResponseHandler;
import com.springappdemo.springjwt.payload.response.TokenRefreshResponse;
import com.springappdemo.springjwt.payload.response.UserInfoResponse;
import com.springappdemo.springjwt.repository.RoleRepository;
import com.springappdemo.springjwt.repository.UserRepository;
import com.springappdemo.springjwt.security.jwt.JwtUtils;
import com.springappdemo.springjwt.security.services.RefreshTokenService;
import com.springappdemo.springjwt.security.services.UserDetailsImpl;

import net.bytebuddy.utility.RandomString;

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
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
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

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
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
			user.get().setPasswordResetToken(token);
			userRepository.save(user.get());
			return user.get().getPasswordResetToken();
		} else {
			throw new JWTSecurityException(token,"Could not find any customer with the username " + username);
		}
	}
	
	@PostMapping("/reset_password")
	public void updatePassword(@RequestBody User user,@RequestParam String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
         
        user.setPasswordResetToken(null);
        userRepository.save(user);
    }
	
	@GetMapping("/get_user_by_reset_token")
	public User getByResetPasswordToken(@RequestParam String token) {
        return userRepository.findByPasswordResetToken(token).get();
    }
}
