package co.istad.productapisimpledemo.restcontrollers;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// Authorization Code Flow
@RestController
@RequiredArgsConstructor
public class AuthenticationRestController {
    private final OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/get-token")
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
    }
}
