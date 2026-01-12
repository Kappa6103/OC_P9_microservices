package com.medilabo.patient_service_front.controllers;

import com.medilabo.patient_service_front.client.AuthClient;
import com.medilabo.patient_service_front.models.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthClient authClient;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @GetMapping()
    public String loginPage(Model model) {
        boolean error = false;
        boolean logout = false;
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        model.addAttribute("loginRequest", new LoginRequest("", ""));
        return "login";
    }

    @PostMapping
    public String login(@ModelAttribute LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authClient.isValidCredential(loginRequest)) {
            // 1. Create authentication for the Frontend session
            // We store the raw password here so we can use it later for Basic Auth to the Gateway
            //TODO encrypt password here before persisting it
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    loginRequest.username(),
                    loginRequest.password(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );

            // 2. Save to Security Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            // 3. Persist the context in the Session so it survives redirects
            securityContextRepository.saveContext(context, request, response);
            return "redirect:/patient/list";
        }
        return "redirect:login?error=true";
    }

}
