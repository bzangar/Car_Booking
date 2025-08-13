package org.example.configuration;


import lombok.RequiredArgsConstructor;
import org.example.configuration.filters.JwtAuthFilter;
import org.example.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(auth->
                        auth.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/cars").permitAll()
                                .requestMatchers("/login.html",
                                        "/register.html",
                                        "/css/**",
                                        "/js/**",
                                        "/owner/**",
                                        "/client/**",
                                        "/admin/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "/api/cars").hasRole("OWNER")
                                .requestMatchers(HttpMethod.PUT, "/api/cars/**").hasRole("OWNER")
                                .requestMatchers(HttpMethod.DELETE, "/api/cars/**").hasAnyRole("OWNER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/cars/owner").hasRole("OWNER")
                                .requestMatchers(HttpMethod.GET, "/api/owner/bookings").hasRole("OWNER")


                                .requestMatchers(HttpMethod.PUT, "/api/bookings/**").hasRole("OWNER")
                                .requestMatchers(HttpMethod.POST, "/api/bookings").authenticated()

                                .requestMatchers(HttpMethod.GET, "/api/cars/*/reviews", "/api/cars", "/api/cars/**").permitAll()

                                .requestMatchers(HttpMethod.DELETE, "/api/users/me").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/users/me/bookings").authenticated()

                                .requestMatchers(HttpMethod.GET, "/api/admin/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/admin/users/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/api/cars/*/reviews").authenticated()



                                .anyRequest().authenticated())

                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
