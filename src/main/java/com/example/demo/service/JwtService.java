package com.example.demo.service;


import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepositry;
import com.example.demo.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {
@Autowired
private JwtUtil util;
  @Autowired
  private UserRepositry userRepositry;
  @Autowired
  private AuthenticationManager authenticationManager;
    // This method is part of the UserDetailsService interface and is responsible for loading user details based on the provided username.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      User user=userRepositry.findById(username).get();
        if (user !=null){
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthority(user)

            );

        }else {
        throw new UsernameNotFoundException("not found");

        }

    }
    // Helper method to extract authorities (roles) from the User object.

    private Set getAuthority(User user){
        Set<SimpleGrantedAuthority> authorities =new HashSet<>();
        for(Role role: user.getRole()){
          authorities.add(new SimpleGrantedAuthority("ROLE"+role.getId()));

        }
        return authorities;

    }
    // This method creates a JWT token based on the provided login request.

    public LoginResponse createJwtToken(LoginRequest loginRequest) throws Exception {

        String username=loginRequest.getUserName();
        String userPassword=loginRequest.getPassord();
        authenticate(username,userPassword);
        UserDetails userDetails=loadUserByUsername(username);
        String newGeneratedToken =util.generaToken(userDetails);

        User user=userRepositry.findById(username).get();
        LoginResponse loginResponse=new LoginResponse(user,newGeneratedToken);

        return loginResponse;
    }
    // Helper method to perform user authentication using Spring Security's authenticationManager.
    private void authenticate(String username,String userPassword)    throws Exception{
        try {
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,userPassword));

        }catch (BadCredentialsException e){
            throw new Exception("invalid");

        }


    }
}
