package ke.co.phyno.learn.r2dbc.config.security;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import reactor.core.publisher.Mono;

import java.util.logging.Level;

@Log
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class HttpSecurityConfigurer {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromFuture(() -> {
                    log.log(Level.WARNING, String.format("HTTP exception handling authentication entry point [ method=%s, path=%s, message=%s ]", swe.getRequest().getMethodValue().toLowerCase(), swe.getRequest().getPath().value(), e.getMessage()));
                    swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    throw new AccessDeniedException(String.format("%s Unauthorized access denied", HttpStatus.UNAUTHORIZED.value()));
                }))
                .accessDeniedHandler((swe, e) -> Mono.fromFuture(() -> {
                    log.log(Level.WARNING, String.format("HTTP exception handling access denied [ method=%s, path=%s, message=%s ]", swe.getRequest().getMethodValue().toLowerCase(), swe.getRequest().getPath().value(), e.getMessage()));
                    swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    throw new AccessDeniedException(String.format("%s Unauthorized access denied", HttpStatus.UNAUTHORIZED.value()));
                }))
                .and()
                .cors().configurationSource(exchange -> corsConfiguration())
                .and()
                .requestCache().requestCache(NoOpServerRequestCache.getInstance())
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/", "/help", "/swagger-*/**", "/v2/api-docs/**", "/v3/api-docs/**").permitAll()
                /* customer */
                .pathMatchers(HttpMethod.POST, "/customer/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/customer/**").permitAll()

                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyExchange()
                .authenticated();
        return http.build();
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowCredentials(false);
        log.log(Level.INFO, String.format("Cors config [ credentials=%s, origins=%s, allowedHeaders=%s, exposedHeaders=%s, methods=%s ]", corsConfig.getAllowCredentials(), corsConfig.getAllowedOrigins(), corsConfig.getAllowedHeaders(), corsConfig.getExposedHeaders(), corsConfig.getAllowedMethods()));
        return corsConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
