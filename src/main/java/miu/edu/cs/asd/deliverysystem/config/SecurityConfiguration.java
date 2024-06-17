package miu.edu.cs.asd.deliverysystem.config;

import lombok.RequiredArgsConstructor;
import miu.edu.cs.asd.deliverysystem.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtFilter;
    private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
            "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui","/swagger-ui/*",
            "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/api/auth/**",
            "/api/test/**", "/authenticate" , "http://localhost:8080/swagger-ui.html" };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http
              .csrf(CsrfConfigurer::disable)
              .authorizeHttpRequests(
                      req -> req
                              .requestMatchers(WHITE_LIST_URL).permitAll()
                              .requestMatchers("/api/v1/management/**")
                              .hasAnyRole(Role.ADMIN.name(), Role.MEMBER.name())
                              .anyRequest()
                              .authenticated()
              )
              .sessionManagement(session ->   session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authenticationProvider(authenticationProvider)
              .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
              .build();
    }

}
