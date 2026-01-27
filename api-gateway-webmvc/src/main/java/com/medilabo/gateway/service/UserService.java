package com.medilabo.gateway.service;

import com.medilabo.gateway.model.User;
import com.medilabo.gateway.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService , CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities()
        );
    }

    private static Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public void run(String... args) {
        int maxRetries = 10;
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                userRepository.save(new User("user", passwordEncoder.encode("password")));
                log.info("Saved our user in DB, Username : user, Password : password");
                return;
            } catch (Exception e) {
                attempt++;
                log.error("Attempt {}/{} to insert our user in DB. Retrying in 5 seconds...", attempt, maxRetries);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    log.error("Interrupted during wait");
                    return;
                }
            }
        }
        log.error("Failed to insert our mock user after {} attempts. The dependent service might be down", maxRetries);
    }
}
