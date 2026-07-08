package co.istad.productapisimpledemo.restcontrollers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/home")
    public Map<String, Object> getHomeData(
            @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OidcUser principal) {

        // 1. Get user profile details from the ID Token
        String username = principal.getPreferredUsername();
        String email = principal.getEmail();

        // 2. Fetch the Access Token
        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        // 3. Fetch the Refresh Token (Will be present because of 'offline_access' scope)
        String refreshToken = authorizedClient.getRefreshToken() != null
                ? authorizedClient.getRefreshToken().getTokenValue()
                : "No refresh token returned";

        return Map.of(
                "message", "Welcome " + username + " (" + email + ")!",
                "access_token", accessToken,
                "refresh_token", refreshToken
        );
    }
}