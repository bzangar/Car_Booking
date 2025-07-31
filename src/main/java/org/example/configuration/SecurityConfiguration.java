package org.example.configuration;


import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(auth->
                        auth.requestMatchers("admin-*html").hasRole("ADMIN")
                                .requestMatchers("/auth/register",
                                        "/login.html",
                                        "register.html").permitAll()
                                .anyRequest().authenticated())

                .csrf(csrf -> csrf.disable())
                //.formLogin(Customizer.withDefaults()) // чтобы видеть дефолтный логин страницу
                .formLogin(form -> form
                        .loginPage("/login.html")              // собственная HTML-страница логина
                        .loginProcessingUrl("/auth/login")// URL для отправки формы
                        .defaultSuccessUrl("/cars.html") // куда перенаправлять после успешного входа
                        .permitAll()
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){

        return username -> {
            User user = userRepository.findByUsername(username);
            if(user != null) return user;

            throw new UsernameNotFoundException("User '" +  username + "' not found");
        };


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
