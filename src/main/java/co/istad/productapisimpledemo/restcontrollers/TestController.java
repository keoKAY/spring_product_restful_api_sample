package co.istad.productapisimpledemo.restcontrollers;


import co.istad.productapisimpledemo.dto.user.UserResponse;
import co.istad.productapisimpledemo.repository.UserRepository;
import co.istad.productapisimpledemo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/test")
@Slf4j
public class TestController {
    private final UserService userService;

    @GetMapping("/profile")
    public UserResponse getProfile(@AuthenticationPrincipal Jwt jwt) {
        String keycloakId = (String) jwt.getSubject();
        log.info("Profile keycloakId: {}", keycloakId);
        return userService.getUserByKeycloakId(keycloakId);

    }
    // only customer user can access this !
    //@PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
    @GetMapping("/customer")
    public String customer() {
        return "Hello! I am the customer ";
    }

    // role -> authorities
    // CUSTOMER -> product:create, product:view , product:delete
    // only seller user can access this
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/seller")
    public String seller() {
        return "Hello! I am the seller ";
    }
}
