package com.emazon.stock_v1.infrastructure.out.jwt.config;

import com.emazon.stock_v1.infrastructure.input.rest.util.PathDefinition;
import com.emazon.stock_v1.infrastructure.out.jwt.config.filter.JwtTokenValidator;
import com.emazon.stock_v1.infrastructure.out.jwt.entry.point.AuthenticationEntryPointImpl;
import com.emazon.stock_v1.infrastructure.out.jwt.handler.AccessDeniedHandlerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.emazon.stock_v1.infrastructure.input.rest.util.PathDefinition.*;
import static com.emazon.stock_v1.utils.GlobalConstants.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenValidator jwtTokenValidator;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers(HttpMethod.POST, CATEGORIES.concat(ROOT)).hasRole(ADMIN);

                    authorizeRequests.requestMatchers(HttpMethod.GET, CATEGORIES.concat(ROOT)).hasAnyRole(
                            ADMIN, WAREHOUSE_WORKER, CUSTOMER
                    );

                    authorizeRequests.requestMatchers(HttpMethod.POST, BRANDS.concat(ROOT)).hasRole(ADMIN);

                    authorizeRequests.requestMatchers(HttpMethod.GET, BRANDS.concat(ROOT)).hasAnyRole(
                            ADMIN, WAREHOUSE_WORKER, CUSTOMER
                    );

                    authorizeRequests.requestMatchers(HttpMethod.POST, ITEMS.concat(ROOT)).hasRole(ADMIN);

                    authorizeRequests.requestMatchers(HttpMethod.GET, ITEMS.concat(ROOT)).hasAnyRole(
                            ADMIN, WAREHOUSE_WORKER, CUSTOMER
                    );

                    authorizeRequests.requestMatchers(
                            HttpMethod.GET, ITEMS.concat(CATEGORY_WITH_ID_PARAM)
                    ).permitAll();

                    authorizeRequests.requestMatchers(HttpMethod.GET, ITEMS.concat(PathDefinition.BRAND)).permitAll();

                    authorizeRequests.requestMatchers(SWAGGER_UI).permitAll();
                    authorizeRequests.requestMatchers(HttpMethod.GET, OPEN_API).permitAll();

                    authorizeRequests.anyRequest().denyAll();
                })
                .addFilterBefore(jwtTokenValidator, UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(customizer -> {
                    customizer.accessDeniedHandler(accessDeniedHandler);
                    customizer.authenticationEntryPoint(authenticationEntryPoint);
                })
                .build();
    }
}
