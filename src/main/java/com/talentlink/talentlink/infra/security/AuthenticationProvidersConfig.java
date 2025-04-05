package com.talentlink.talentlink.infra.security;

import com.talentlink.talentlink.services.company.CompanyService;
import com.talentlink.talentlink.services.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class AuthenticationProvidersConfig {

    @Bean
    public AuthenticationProvider userAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationProvider companyAuthenticationProvider(CompanyService companyService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(companyService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Primary
    @Bean("companyAuthenticationManager")
    public AuthenticationManager companyAuthenticationManager(AuthenticationProvider companyAuthenticationProvider) {
        return new ProviderManager(List.of(companyAuthenticationProvider));
    }

    @Bean("userAuthenticationManager")
    public AuthenticationManager userAuthenticationManager(AuthenticationProvider userAuthenticationProvider) {
        return new ProviderManager(List.of(userAuthenticationProvider));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider userAuthenticationProvider,
                                                       AuthenticationProvider companyAuthenticationProvider) {
        return new ProviderManager(List.of(userAuthenticationProvider, companyAuthenticationProvider));
    }
}
