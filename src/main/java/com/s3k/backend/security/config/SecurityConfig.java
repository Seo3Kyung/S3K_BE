package com.s3k.backend.security.config;

import com.s3k.backend.security.handler.OAuth2LoginFailureHandler;
import com.s3k.backend.security.handler.OAuth2LoginSuccessHandler;
import com.s3k.backend.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/oauth2/authorization/**", "/login/oauth2/code/**").permitAll()
            .anyRequest().authenticated()
        )

        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(
                new LoginUrlAuthenticationEntryPoint("http://localhost:3000/login")
            ))

        .oauth2Login(oauth -> oauth
            .authorizationEndpoint(a -> a.baseUri("/oauth2/authorization"))
            .redirectionEndpoint(r -> r.baseUri("/login/oauth2/code/*"))
            .userInfoEndpoint(u -> u.userService(customOAuth2UserService))
            .successHandler(oAuth2LoginSuccessHandler())
            .failureHandler(oAuth2LoginFailureHandler())
        )
        .build();
  }

  @Bean
  public OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler() {
    return new OAuth2LoginSuccessHandler();
  }

  @Bean
  public OAuth2LoginFailureHandler oAuth2LoginFailureHandler() {
    return new OAuth2LoginFailureHandler();
  }
}
