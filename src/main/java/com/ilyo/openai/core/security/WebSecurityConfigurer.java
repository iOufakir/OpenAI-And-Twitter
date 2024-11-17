package com.ilyo.openai.core.security;

import static org.springframework.security.config.Customizer.withDefaults;

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
    http.csrf(csrfConfigurer -> csrfConfigurer.csrfTokenRepository(csrfTokenRepository)
            .ignoringRequestMatchers("/**"))
        .cors(withDefaults())
        .securityMatcher(new MediaTypeRequestMatcher(MediaType.APPLICATION_JSON))
        // helps to detect certain types of attacks, like XSS, data injection attacks...
        .headers(header -> header.contentSecurityPolicy(
            contentSecurityPolicyConfig -> contentSecurityPolicyConfig.policyDirectives("script-src 'self'")));

    //TODO : Increase security for monitoring actuator
    http.authorizeHttpRequests(authz -> authz
            .requestMatchers("/**").hasRole("USER")
            .anyRequest()
            .authenticated())
        .httpBasic(withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

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
        .roles("USER")
        .build());
    return manager;
  }

}
