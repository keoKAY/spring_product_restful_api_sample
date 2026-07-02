package co.istad.productapisimpledemo.config;


// Write the code to determine/config the security of spring

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // SecurityFilterChain
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // disable csrf  -> for stateless application
        http.csrf(AbstractHttpConfigurer::disable);
        // enable the http basic authentication , JWT
        // user:password
        // base64 -> username:password
        http.httpBasic(Customizer.withDefaults());
        // endpoint to be allowed or protected
        http.authorizeHttpRequests(
                request-> request.requestMatchers("/api/v1/products/**").permitAll()
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
                        .password("{noop}secured12345")
                        .roles("USER").build();

        return new InMemoryUserDetailsManager(developer);
    }
}
