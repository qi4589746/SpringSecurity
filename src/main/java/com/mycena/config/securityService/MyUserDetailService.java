package com.mycena.config.securityService;

import com.mycena.domain.User;
import com.mycena.domain.UserRole;
import com.mycena.repository.RoleRepository;
import com.mycena.repository.UserRepository;
import com.mycena.repository.UserRoleRepository;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jihung on 3/31/18.
 */
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("in MyUserDetailService..." + "userName: " + username);
        User user = userRepository.findByUserName(username).get();
        if (user == null) throw new UsernameNotFoundException(username + " not found");
        System.out.println(user.userPassword);
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<UserRole> userRoles = userRoleRepository.findByUserId(user.id);

        for (UserRole userRole : userRoles) {
            long roleId = userRole.roleId;
            String roleName = roleRepository.findById(roleId).get().role;
            if (!StringUtils.isNullOrEmpty(roleName))
                authorities.add(new SimpleGrantedAuthority(roleRepository.findById(userRole.roleId).get().role));
            System.out.println("username is " + username + ", " + roleName);
        }

        return new org.springframework.security.core.userdetails.User(username, user.userPassword, authorities);
    }
}
