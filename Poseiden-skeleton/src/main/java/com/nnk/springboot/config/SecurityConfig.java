package com.nnk.springboot.config;

import com.nnk.springboot.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        private final CustomUserDetailsService userDetailsService;
        private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
        private final CustomAuthenticationFailureHandler authenticationFailureHandler;
        private final LoginRequestLoggingFilter loginRequestLoggingFilter;

        public SecurityConfig(CustomUserDetailsService userDetailsService,
                        CustomAuthenticationSuccessHandler authenticationSuccessHandler,
                        CustomAuthenticationFailureHandler authenticationFailureHandler,
                        LoginRequestLoggingFilter loginRequestLoggingFilter) {
                this.userDetailsService = userDetailsService;
                this.authenticationSuccessHandler = authenticationSuccessHandler;
                this.authenticationFailureHandler = authenticationFailureHandler;
                this.loginRequestLoggingFilter = loginRequestLoggingFilter;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
                requestHandler.setCsrfRequestAttributeName("_csrf");

                http.authenticationProvider(authenticationProvider())
                                .authorizeHttpRequests(authz -> authz
                                                .requestMatchers("/register", "/csrf").permitAll()
                                                .requestMatchers("/app/login", "/login").permitAll()
                                                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/app/login")
                                                .loginProcessingUrl("/login")
                                                .successHandler(authenticationSuccessHandler)
                                                .failureHandler(authenticationFailureHandler)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/app/logout")
                                                .logoutSuccessUrl("/app/login?logout=true")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID"))
                                .csrf(csrf -> csrf
                                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                                .csrfTokenRequestHandler(requestHandler))
                                .httpBasic(httpBasic -> httpBasic.disable())
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .sessionManagement(session -> session
                                                .sessionFixation().migrateSession()
                                                .sessionConcurrency(concurrency -> concurrency
                                                                .maximumSessions(1)
                                                                .maxSessionsPreventsLogin(false)
                                                                .expiredUrl("/app/login?expired=true")))
                                .addFilterAfter(new com.nnk.springboot.config.CsrfCookieFilter(), CsrfFilter.class)
                                .addFilterBefore(loginRequestLoggingFilter,
                                                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public com.nnk.springboot.validation.PasswordValidator passwordValidator() {
                return new com.nnk.springboot.validation.PasswordValidator();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailsService);
                authProvider.setPasswordEncoder(passwordEncoder());
                return authProvider;
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authConfig) throws Exception {
                return authConfig.getAuthenticationManager();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                final List<String> FRONTEND_URLS = Arrays.asList("http://localhost:8080");
                configuration.setAllowedOriginPatterns(FRONTEND_URLS);

                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE",
                                "OPTIONS"));

                configuration.setAllowedHeaders(Arrays.asList(
                                "Content-Type",
                                "Accept",
                                "X-Requested-With",
                                "X-XSRF-TOKEN"));

                configuration.setAllowCredentials(true);

                configuration.setExposedHeaders(Arrays.asList(
                                "Content-Type",
                                "X-XSRF-TOKEN"));

                configuration.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);

                return source;
        }

        /**
         * Configure SameSite attribute for CSRF cookie
         * Note: The Secure attribute is managed by application.properties
         */
        @Bean
        public CookieSameSiteSupplier cookieSameSiteSupplier() {
                return CookieSameSiteSupplier.ofLax().whenHasName("XSRF-TOKEN");
        }
}
