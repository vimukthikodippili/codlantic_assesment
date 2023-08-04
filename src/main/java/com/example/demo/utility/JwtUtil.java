package com.example.demo.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final String SECRET_KEY ="12345678";
    private static final int TOKEN_VALIDITY  =3600*5;

    public String getUsernameFromToken(String token){
return getClaimFromToken(token, Claims::getSubject);

    }
    public <T> T getClaimFromToken(String token, Function<Claims,T> cliamResolver){

        final Claims claims= getAllClaimsFromToken(token);
return cliamResolver.apply(claims);
    }
  private Claims  getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

  }
  public Boolean validateToken(String token, UserDetails userDetails){

final String userName=getUsernameFromToken(token);
return (userName.equals(userDetails.getUsername()) && isTokenExpired(token));

  }
 public boolean   isTokenExpired(String token){
        final Date expiration =getClaimFromToken(token,Claims::getExpiration);
        return expiration.before(new Date());


 }
 public String generaToken(UserDetails userDetails){
     Map<String,Object> claims=new HashMap<>();
     return Jwts.builder()
             .setClaims(claims)
             .setSubject(userDetails.getUsername())
             .setIssuedAt(new Date(System.currentTimeMillis()))
             .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
             .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
             .compact();



 }
}
