package co.istad.productapisimpledemo.config;


// Write the code to determine/config the security of spring

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    // SecurityFilterChain
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
         http.sessionManagement(
                 session ->
                         session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

      //   http.oauth2Login(Customizer.withDefaults());

        http.oauth2ResourceServer(
                oauth2 -> oauth2.jwt(Customizer.withDefaults())
        );

        http.authorizeHttpRequests(
                request->
           request
                   // 1. ALWAYS permit OPTIONS for CORS pre-flights (crucial for frontend clients)
                   .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                   .requestMatchers(
                           "/api/v1/authenticate",
                           "/api/v1/authenticate/**","/error").permitAll()
                   .requestMatchers(
                           "/scalar/**",
                           "/v3/api-docs/**").permitAll()
                   .requestMatchers(
                           "/api/v1/files/**",
                           "/files/**").permitAll()
                   .requestMatchers(HttpMethod.GET,
                           "/api/v1/categories/**").permitAll()

                   .requestMatchers(HttpMethod.GET,
                           "/api/v1/products/**", "/api/v1/tags",
                           "/api/v1/tags/**").permitAll()
              // login successfully first to access it
                .anyRequest().authenticated()
        );

/*        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverterForKeycloak())
                )
        );*/
        return http.build();
    }



    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak(){
        Converter<Jwt, Collection<GrantedAuthority>> converter = jwt-> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            var roles = realmAccess.get("roles");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                    .collect(Collectors.toSet());

        };

        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtAuthenticationConverter;

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
