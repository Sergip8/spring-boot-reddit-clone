package com.demojava.redditdemo.controller;

import com.demojava.redditdemo.auth.AuthRequest;
import com.demojava.redditdemo.auth.AuthService;
import com.demojava.redditdemo.auth.RegisterRequest;
import com.demojava.redditdemo.dto.AuthenticationResponse;
import com.demojava.redditdemo.dto.RefreshTokenRequest;
import com.demojava.redditdemo.dto.ResponseModel;
import com.demojava.redditdemo.model.VerificationToken;
import com.demojava.redditdemo.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

@RestController
@RequestMapping("/api/v1/userAuth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){

        return  ResponseEntity.ok( authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthRequest request) {

        return ResponseEntity.ok(authService.login(request));

    }
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token){
        authService.verifyEmail(token);
        return ResponseEntity.ok("Account Verified");
    }

    @PostMapping("refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshToken){
        System.out.println(refreshToken);
        return authService.refreshToken(refreshToken);
    }
    @PostMapping("logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest request){
        refreshTokenService.deleteRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok("Refresh token borrao");
    }
    @GetMapping("subscribe/r/{name}")
    public ResponseEntity<ResponseModel> subredditSubscribe(@PathVariable String name){
       return ResponseEntity.accepted().body(authService.subredditSubscribe(name)) ;
    }
    @GetMapping("subs")
    public ResponseEntity<List<String>> getSubs(){
        return ResponseEntity.accepted().body(authService.getAllSubs());
    }
}

