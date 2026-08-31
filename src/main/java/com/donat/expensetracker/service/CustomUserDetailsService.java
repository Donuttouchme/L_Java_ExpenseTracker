package com.donat.expensetracker.service;

import com.donat.expensetracker.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository _userRepository){
        userRepository = _userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.donat.expensetracker.model.User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("An error has occurred!"));
        return User.builder().username(username).password(user.getPassword()).roles(user.getRole()).build();
    }
}
