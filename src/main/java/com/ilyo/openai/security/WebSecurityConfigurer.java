package com.ilyo.openai.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfigurer {

    private final CsrfTokenRepository csrfTokenRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf(csrfConfigurer -> csrfConfigurer.csrfTokenRepository(csrfTokenRepository)
                        .ignoringRequestMatchers("/users/1/**"))

                .securityMatcher(new MediaTypeRequestMatcher(MediaType.APPLICATION_JSON))
                .headers()
                // helps to detect certain types of attacks, like XSS, data injection attacks...
                .contentSecurityPolicy("script-src 'self'");

        //TODO : Increase security for monitoring actuator
        http.authorizeHttpRequests()
                .requestMatchers("/users/**").hasAuthority("USER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //TODO: This is temporary for DEMO use
    @Bean
    public UserDetailsService userDetailsService(final BCryptPasswordEncoder bCryptPasswordEncoder) {
        var manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(bCryptPasswordEncoder.encode("userPass"))
                .authorities("USER")
                .build());
        return manager;
    }

}
