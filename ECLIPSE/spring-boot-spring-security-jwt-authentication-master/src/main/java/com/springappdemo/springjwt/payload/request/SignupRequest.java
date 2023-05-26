package com.springappdemo.springjwt.payload.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest 
{
  private String username;
  private String email;
  private List<String> roles;
  private String password;
}
