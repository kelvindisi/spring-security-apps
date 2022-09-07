package com.devkiu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.devkiu.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.antMatchers("/login").permitAll();
                })
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/courses")
                .and().build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails student = User.builder()
                .username("student")
                .password(passwordEncoder.encode("password"))
//                .roles("STUDENT")
                .authorities(STUDENT.getGrantedAuthority())
                .build();
        UserDetails admin = User.builder()
                .username("kelvindisi")
                .password(passwordEncoder.encode("password"))
//                .roles("ADMIN")
                .authorities(ADMIN.getGrantedAuthority())
                .build();
        UserDetails admin_trainee = User.builder()
                .username("trainee")
                .password(passwordEncoder.encode("password"))
//                .roles("ADMIN_TRAINEE")
                .authorities(ADMIN_TRAINEE.getGrantedAuthority())
                .build();

        return new InMemoryUserDetailsManager(
                admin,
                admin_trainee,
                student
        );
    }
}
