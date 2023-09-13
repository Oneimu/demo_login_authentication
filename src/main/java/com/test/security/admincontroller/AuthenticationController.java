package com.test.security.admincontroller;

import com.test.security.auth.AuthenticationRequest;
import com.test.security.auth.AuthenticationResponse;
import com.test.security.auth.AuthenticationService;
import com.test.security.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
// has two end-point for login and signup!
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authenticationService.register(request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(authenticationService.authenticate(request));

    }
}
