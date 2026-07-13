package co.istad.productapisimpledemo.config;


// Write the code to determine/config the security of spring

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    // SecurityFilterChain
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 1. disable csrf  -> for stateless application
        http.csrf(AbstractHttpConfigurer::disable);
        // 2. disable form login
        http.formLogin(AbstractHttpConfigurer::disable);
        // 3. make it become stateless for REST constraint
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // to be able to work with keycloak authorization server
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        //http.httpBasic(Customizer.withDefaults());
        // endpoint to be allowed or protected
        http.authorizeHttpRequests(
                request->

           request
                   // enable scalar
                   .requestMatchers("/api/v1/auth/register").permitAll()
                   .requestMatchers("/scalar/**", "/v3/api-docs/**").permitAll()
                   .requestMatchers("/api/v1/files/**","/files/**").permitAll()
                   .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                   // file uploads

            .requestMatchers(HttpMethod.GET, "/api/v1/products/**","/api/v1/tags/**").permitAll()
              // login successfully first to access it
                .anyRequest().authenticated()
        );
        return http.build();
    }

    // Configuration for the users and role (as in-memory usage)
    @Bean
    public UserDetailsService userDetailsService() {
        // user in security as known as userdetails
        UserDetails developer =
                User.withUsername("developer")
                        .password(passwordEncoder().encode("developer"))
                        .roles("USER").build();
        return new InMemoryUserDetailsManager(developer);
    }


    //user login
    // username , password
    // passwordEncoder.verify(hashString, rawPassword)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
