package com.mycena.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by jihung on 4/2/18.
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    //Access Token 時效
    private int accessTokenValiditySeconds = 60 * 60 * 3;
    //Refresh Token 時效
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 30;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("mycena")
    private String signingKey;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(signingKey);
        return accessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore(){
//        return new InMemoryTokenStore();
        return new JwtTokenStore(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)//Important! support grant type: password
                .tokenServices(tokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client1")
                .secret("client1")
                .authorities("ROLE_ADMIN")
                .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                .scopes("app1")
                .autoApprove("app1")
                .accessTokenValiditySeconds(2000)
                .refreshTokenValiditySeconds(2000);
    }
    //===Can get token by postman tool.===
    //Auth URL: http://localhost:8080/oauth/authorize
    //Access Token URL: http://localhost:8080/oauth/token10000
    //Client ID: client1
    //Client Secret: client1
    //Scope(Optional): app1
    //Grant Type: Client Credentials

//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security
//                .tokenKeyAccess("hasAuthority('ROLE_ADMIN')")
//                .checkTokenAccess("hasAuthority('ROLE_ADMIN')");
//    }


    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(tokenStore());
        services.setSupportRefreshToken(true);
        services.setTokenEnhancer(accessTokenConverter());
        services.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
        services.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
        return services;
    }

}