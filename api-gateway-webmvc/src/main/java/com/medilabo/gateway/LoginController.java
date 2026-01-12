package com.medilabo.gateway;

import com.medilabo.gateway.model.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("Attempting login for user: {} and pwd {}", loginRequest.username(), loginRequest.password());

        // TEMPORARY DEBUG: Fetch user manually to see if passwords match
        userService.repo.findByUsername(loginRequest.username()).ifPresent(user -> {
            boolean matches = passwordEncoder.matches(loginRequest.password(), user.getPassword());
            log.info("Manual password match check: {}", matches);
        });

        try {
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                    .unauthenticated(loginRequest.username(), loginRequest.password());
            Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationResponse);
            SecurityContextHolder.setContext(securityContext);
            securityContextRepository.saveContext(securityContext, request, response);
            log.info("Authentification was successful");
            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            log.error("Authentification failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error("Unexpected error during authentification");
            return ResponseEntity.internalServerError().build();
        }
    }

}