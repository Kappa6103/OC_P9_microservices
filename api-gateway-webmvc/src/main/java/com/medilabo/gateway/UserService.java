package com.medilabo.gateway;

import com.medilabo.gateway.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository repo;

    @PostConstruct
    void init() {
        repo.save(new User("username", passwordEncoder.encode("password")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = repo.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        final User user = optionalUser.get();
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(username)
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();
    }
}
