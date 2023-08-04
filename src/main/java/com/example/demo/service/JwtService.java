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
  @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // User user=userRepositry.findById(username).get();
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
    private Set getAuthority(User user){
        Set<SimpleGrantedAuthority> authorities =new HashSet<>();
        for(Role role: user.getRole()){
          authorities.add(new SimpleGrantedAuthority("ROLE"+role.getId()));

        }
        return authorities;

    }

    public LoginResponse createJwtToken(LoginRequest loginRequest) throws Exception {
        System.out.println(loginRequest);
        String username=loginRequest.getUserName();
        String userPassword=loginRequest.getPassord();
        authenticate(username,userPassword);
        UserDetails userDetails=loadUserByUsername(username);
        String newGeneratedToken =util.generaToken(userDetails);
        System.out.println(newGeneratedToken);
        User user=userRepositry.findById(username).get();
        LoginResponse loginResponse=new LoginResponse(user,newGeneratedToken);
        System.out.println("2"+loginResponse);
        return loginResponse;
    }
    private void authenticate(String username,String userPassword)    throws Exception{
        try {
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,userPassword));

        }catch (BadCredentialsException e){
            throw new Exception("invalid");

        }


    }
}
