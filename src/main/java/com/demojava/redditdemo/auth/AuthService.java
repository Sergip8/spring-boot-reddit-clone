package com.demojava.redditdemo.auth;


import com.demojava.redditdemo.config.JwtService;
import com.demojava.redditdemo.dto.AuthenticationResponse;
import com.demojava.redditdemo.dto.RefreshTokenRequest;
import com.demojava.redditdemo.dto.ResponseModel;
import com.demojava.redditdemo.exception.SpringRedditException;
import com.demojava.redditdemo.model.NotificationEmail;
import com.demojava.redditdemo.model.Role;
import com.demojava.redditdemo.model.User;
import com.demojava.redditdemo.model.VerificationToken;
import com.demojava.redditdemo.repository.UserRepository;
import com.demojava.redditdemo.repository.VerificationTokenRepository;
import com.demojava.redditdemo.service.MailService;
import com.demojava.redditdemo.service.RefreshTokenService;
import com.mongodb.MongoWriteException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final VerificationTokenRepository verificationTokenRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;
    private final RefreshTokenService refreshTokenService;

    @Transactional

    public String register(RegisterRequest request) {
      if(repository.findByUsername(request.getUsername()).isPresent()){
          return "nombre de usuario ya existe";
      }
        var user = User.builder()

                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .role(Role.USER)
                .created(Instant.now())
                .enabled(0)
                .build();
        try{
            repository.save(user);
            generateVerificationToken(user);


        }catch (Exception e){
            if(e.getLocalizedMessage().contains("email")){
                return "Ya existe el Email";
            }
            if(e.getLocalizedMessage().contains("username")){
                return "Ya existe el nombre de usuario";

            }
            return "No se sabe";
        }

        System.out.println(user.getEmail());

        return"ok";
    }

    public AuthenticationResponse login(AuthRequest request) {
        System.out.println(request);
        Authentication authenticate =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwt = jwtService.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(jwt)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(jwtService.extractExpiration(jwt).toInstant())
                .username(request.getUsername())
                .build();
    }
    private  void generateVerificationToken(User user){
        var token = jwtService.generateToken(user.getUsername());
        var verificationToken = VerificationToken.builder()
                .token(token)
                .expiryDate(jwtService.extractExpiration(token).toInstant())
                .userId(user.getId())
                .build();
        verificationTokenRepository.save(verificationToken);
        sendMail(token);

       // return verificationToken;
    }
    private void sendMail(String token){
        mailService.sendMail(new NotificationEmail(
                "Activa tu cuenta",
                jwtService.extractUsername(token),
                "Gracias por registrarse en esta mierda para activar su cuenta haga click aqui " +
                        "http://localhost:8080/api/v1/userAuth/accountVerification/" + token));
    }

    @Transactional
    public void verifyEmail(String token) {


       String username = jwtService.extractUsername(token);
       var user =  repository.findByUsername(username).orElseThrow(() -> new SpringRedditException("Invalid Token"));
       if(jwtService.isTokenValid(token, user)){
           user.setEnabled(1);
           repository.save(user);
       }

    }


    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken) {
        refreshTokenService.validateRefreshToken(refreshToken.getRefreshToken());
        String token = jwtService.generateToken(refreshToken.getUsername());

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshToken.getRefreshToken())
                .username(refreshToken.getUsername())
                .expiresAt(jwtService.extractExpiration(token).toInstant())
                .build();
    }

    public ResponseModel subredditSubscribe(String name) {

        try {
            User user = repository.findByUsername(jwtService.getCurrentUser())
                    .orElseThrow(() -> new SpringRedditException("No se encontro al usuario"));
            user.getSubredditSubscribed().add(name);

            repository.save(user);

            return new ResponseModel("suscripción añadida", true, "USER");
        }catch (Exception e){
            return new ResponseModel("suscripción fallida", false, "USER");

        }

    }

    public List<String> getAllSubs() {
        User user = repository.findByUsername(jwtService.getCurrentUser())
                .orElseThrow(() -> new SpringRedditException("No se encontro al usuario"));
        return user.getSubredditSubscribed();
    }
}
