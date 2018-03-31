package com.mycena.config;

import com.mycena.config.securityService.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * Created by jihung on 3/30/18.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        /*default in super.configure(http)*/
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().and()
//                .httpBasic();

        http
                .csrf().disable()
                .formLogin().disable()
                .anonymous().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }


    //可不設定，下面的userDetailsService Bean會自動DI。（主要看UserDetailsService interface => userDetailsService 名稱改掉也行）
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user").password("user").authorities("ROLE_USER").build());
//        manager.createUser(User.withUsername("admin").password("admin").authorities("ROLE_ADMIN").build());
//        return manager;
//    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailService();
    }


    //5.0後必須要設定PasswordEncoder
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}