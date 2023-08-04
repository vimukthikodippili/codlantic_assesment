package com.example.demo.cotroller;


import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private JwtService jwtService;
    // Endpoint for user authentication and token generation
    @PostMapping("/authentication")
    public LoginResponse createjwtTokenAndLogin(@RequestBody LoginRequest loginRequest) throws Exception{

         return jwtService.createJwtToken(loginRequest);


    }
}
