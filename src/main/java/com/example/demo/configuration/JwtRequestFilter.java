package com.example.demo.configuration;


import com.example.demo.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
final String requestTokenHeader=request.getHeader("Authorization");
String username=null;
String jwtToken=null;
if(requestTokenHeader !=null && requestTokenHeader.startsWith("Bearer")){
    jwtToken=requestTokenHeader.substring(7);
try{
    username=jwtUtil.getUsernameFromToken(jwtToken);

}catch (IllegalArgumentException e){
    System.out.println("unable");

}

}
    }
}
