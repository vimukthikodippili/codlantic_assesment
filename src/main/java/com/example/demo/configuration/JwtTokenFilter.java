//package com.example.demo.configuration;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import com.example.demo.utility.JwtUtil;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class JwtTokenFilter extends BasicAuthenticationFilter {
//    private JwtUtil jwtUtil;
//
//    public JwtTokenFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
//        super(authenticationManager);
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        String authorizationHeader = request.getHeader("Authorization");
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String jwtToken = authorizationHeader.substring(7); // Remove "Bearer " prefix
//            if (jwtUtil.validateToken(jwtToken)) {
//                Authentication authentication = jwtUtil.getAuthenticationFromToken(jwtToken);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}
