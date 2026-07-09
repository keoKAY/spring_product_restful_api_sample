package co.istad.productapisimpledemo.restcontrollers;


import co.istad.productapisimpledemo.dto.auth.RegisterRequest;
import co.istad.productapisimpledemo.dto.auth.RegisterResponse;
import co.istad.productapisimpledemo.service.AuthService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Authorization Code Flow
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authenticate")
public class AuthenticationRestController {
    //private final OAuth2AuthorizedClientService authorizedClientService;
    private final AuthService authService;
    //private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponse createNewAccount(@Valid @RequestBody RegisterRequest request ){
        return authService.register(request);
    }
    @GetMapping
    public String test(){
        return "test";
    }
 /*   @GetMapping("/get-token")
    public Map<String, String> getTokens(@AuthenticationPrincipal OidcUser principal) {
        // Fetch the authorized client using the registration ID (ex. keycloak)
        var client = authorizedClientService.loadAuthorizedClient(
                "spring-boot-app",
                principal.getName()
        );
        String accessToken = client.getAccessToken().getTokenValue();
        String refreshToken = client.getRefreshToken()!= null ? client.getRefreshToken().getTokenValue() : "No Refresh Token";

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }*/
}
