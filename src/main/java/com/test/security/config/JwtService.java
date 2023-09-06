package com.test.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;


// retrieve username from token
@Service
public class JwtService {

        // create a secret key so a user can verify themselves

        // move this to application.properties
        private final String SECRET_KEY = "\n" +
                "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY5NDAwMjA0NCwiaWF0IjoxNjk0MDAyMDQ0fQ.mmHGqXi0Hnk7dsNfMEr6fa6iFIniZXgE0wf_ynSifuA";


        public String extractUsername(String token){
            return null;
        }


        private Claims extractAllClaims(String token){

            return Jwts.parser()
                    .setSigningKey(getSignInkey(token))
                    .parseClaimsJwt(token)
                    .getBody();
        }

        private Key getSignInkey() {
            byte[] keyByptes = Decoder.(SECRET_KEY);
        }

}
