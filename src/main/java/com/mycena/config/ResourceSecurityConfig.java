package com.mycena.config;

import com.mycena.config.securityService.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by jihung on 3/31/18.
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceSecurityConfig extends ResourceServerConfigurerAdapter {



    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        //authenticationProvider，兩樣設置：1. userList  2. 加密方法PasswordEncoder
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager());
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        //重要，AuthenticationManagerBuilder設置Provider會附蓋掉PasswordEncoder的Bean
        return authenticationProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http
//                .csrf().disable()
//                .formLogin().disable()
//                .anonymous().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }

    @Autowired
    public  void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        //maybe： authenticationProvider() = userDetailsService() + inMemoryAuthentication().passwordEncoder()
        //priority： authenticationProvider() > userDetailsService() & inMemoryAuthentication().passwordEncoder()
        auth.authenticationProvider(authenticationProvider());
   }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailService();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user").password("user").authorities("ROLE_USER").build());
//        manager.createUser(User.withUsername("admin").password("admin").authorities("ROLE_ADMIN").build());
//        return manager;
//    }

    //5.0後必須要設定PasswordEncoder
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}
