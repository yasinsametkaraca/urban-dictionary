package com.ysk.urbandictionary.configuration;

import com.ysk.urbandictionary.error.ApiError;
import com.ysk.urbandictionary.user.User;
import com.ysk.urbandictionary.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User inDatabase = userRepository.findByUsername(username);
        if (inDatabase == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return inDatabase;
    }
}
