package com.mycena.config.securityService;

import com.mycena.domain.security.User;
import com.mycena.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("in MyUserDetailService..." + "userName: " + username);
        User user = userRepository.findByUsername(username).get();
        if (user == null) throw new UsernameNotFoundException(username + " not found");
        return user;
    }
}
