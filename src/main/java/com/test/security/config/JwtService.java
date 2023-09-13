package com.test.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


// retrieve username from token
@Service
public class JwtService {

    // create a secret key so a user can verify themselves

    // move this to application.properties
    private final String SECRET_KEY = "\n" +
            "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY5NDAwMjA0NCwiaWF0IjoxNjk0MDAyMDQ0fQ.mmHGqXi0Hnk7dsNfMEr6fa6iFIniZXgE0wf_ynSifuA";


    // extract Username from the token: Subject is the username
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // extract a claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
            final Claims claims =extractAllClaims(token);
            return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){

        return Jwts.parser()
                .setSigningKey(getSignInkey())
                .parseClaimsJwt(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    //generate token
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *48))
                .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInkey() {
        byte[] keyByptes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByptes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
