package com.s3k.backend.security.config;

import com.s3k.backend.security.filter.CustomJwtAuthenticationFilter;
import com.s3k.backend.security.handler.CustomAccessDeniedHandler;
import com.s3k.backend.security.handler.CustomAuthenticationEntryPoint;
import com.s3k.backend.security.handler.OAuth2LoginFailureHandler;
import com.s3k.backend.security.handler.OAuth2LoginSuccessHandler;
import com.s3k.backend.security.service.CustomOidcUserService;
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

  private final CustomOidcUserService customOidcUserService;
  private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
  private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
  private final CustomJwtAuthenticationFilter customJwtAuthenticationFilter;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;

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

        .oauth2Login(oauth -> oauth
            .authorizationEndpoint(a -> a.baseUri("/oauth2/authorization"))
            .redirectionEndpoint(r -> r.baseUri("/login/oauth2/code/*"))
            .userInfoEndpoint(u -> u.oidcUserService(customOidcUserService))
            .successHandler(oAuth2LoginSuccessHandler)
            .failureHandler(oAuth2LoginFailureHandler)
        )

        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/swagger", "/api/swagger-ui/**", "/v3/api-docs/**", "/webjars/**")
            .permitAll()
            .requestMatchers("/api/members/sign-up").hasAuthority("CHECK")
            .requestMatchers("/oauth2/authorization/**", "/login/oauth2/code/**"
            )
            .permitAll()
            .anyRequest().authenticated()
        )

        .exceptionHandling(ex -> {
          ex.authenticationEntryPoint(customAuthenticationEntryPoint);
          ex.accessDeniedHandler(customAccessDeniedHandler);
        })

        .addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
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
}
