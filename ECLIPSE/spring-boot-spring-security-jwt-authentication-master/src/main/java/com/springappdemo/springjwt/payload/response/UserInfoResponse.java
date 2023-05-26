package com.springappdemo.springjwt.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse 
{
    private Long id;
	private String refreshToken;
	private String username;
	private String password;
	private List<String> roles;
}
