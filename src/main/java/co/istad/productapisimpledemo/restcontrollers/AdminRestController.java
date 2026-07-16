package co.istad.productapisimpledemo.restcontrollers;


import co.istad.productapisimpledemo.service.AuthService;
import co.istad.productapisimpledemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final UserService userService;
    // we should separate this
    private final AuthService authService;

    @PutMapping("{keycloakId}/promote-to-seller")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void promoteToSeller(@PathVariable String keycloakId) {

    }
}
