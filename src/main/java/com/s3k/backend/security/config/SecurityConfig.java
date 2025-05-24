package com.s3k.backend.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s3k.backend.member.service.MemberService;
import com.s3k.backend.security.filter.CustomJwtAuthenticationFilter;
import com.s3k.backend.security.handler.OAuth2LoginFailureHandler;
import com.s3k.backend.security.handler.OAuth2LoginSuccessHandler;
import com.s3k.backend.security.jwt.JwtTokenIssuer;
import com.s3k.backend.security.jwt.JwtTokenValidator;
import com.s3k.backend.security.service.CustomOAuth2UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomOAuth2UserService customOAuth2UserService;
  private final MemberService memberService;
  private final JwtTokenIssuer jwtTokenIssuer;
  private final JwtTokenValidator jwtTokenValidator;
  private final ObjectMapper objectMapper;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {
    return http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/oauth2/authorization/**", "/login/oauth2/code/**",
                "/members/sign-up/**")
            .permitAll()
            .anyRequest().authenticated()
        )

        .oauth2Login(oauth -> oauth
            .authorizationEndpoint(a -> a.baseUri("/oauth2/authorization"))
            .redirectionEndpoint(r -> r.baseUri("/login/oauth2/code/*"))
            .userInfoEndpoint(u -> u.userService(customOAuth2UserService))
            .successHandler(oAuth2LoginSuccessHandler())
            .failureHandler(oAuth2LoginFailureHandler())
        )

        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler() {
    return new OAuth2LoginSuccessHandler(jwtTokenIssuer);
  }

  @Bean
  public OAuth2LoginFailureHandler oAuth2LoginFailureHandler() {
    return new OAuth2LoginFailureHandler();
  }

  @Bean
  public CustomJwtAuthenticationFilter jwtAuthenticationFilter() {
    return new CustomJwtAuthenticationFilter(jwtTokenValidator, memberService, objectMapper);
  }
}
