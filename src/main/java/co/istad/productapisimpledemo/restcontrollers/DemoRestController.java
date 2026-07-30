package co.istad.productapisimpledemo.restcontrollers;

import co.istad.productapisimpledemo.service.impl.AuthServiceImpl;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/data")
public class DemoRestController {

    private final AuthServiceImpl authServiceImpl;

    public DemoRestController(AuthServiceImpl authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email ){
        authServiceImpl.sendPasswordLinkReset(email);
        return ResponseEntity.ok("If the email matches an active account, a password reset link has been sent.");
    }
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('CUSTOMER','SELLER','ADMIN')")
    public ResponseEntity<Map<String, Object>> getUserProfile(@AuthenticationPrincipal Jwt jwt ) {

        //
        String keycloakId = jwt.getSubject();
        String username = jwt.getClaimAsString("preferred_username");
        String email = jwt.getClaimAsString("email");
        String fullName = jwt.getClaimAsString("name");

        // 3. Package the data (or pass the 'userId' to a database service)
        Map<String, Object> profileData = new HashMap<>();
        profileData.put("userId", keycloakId);
        profileData.put("username", username);
        profileData.put("email", email);
        profileData.put("fullName", fullName);

        return ResponseEntity.ok(profileData);
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> getCustomer() {
        return ResponseEntity.ok("Welcome! , So you are the customer ");
    }

    @GetMapping("/admin")
   // @PreAuthorize("hasRole('ADMIN')")
    // Using the Spring SpEL (Spring Expression Language)
    @PreAuthorize("hasAuthority('product:create') and hasRole('ADMIN')")
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok("Welcome! , So you are the admin ");
    }


    @GetMapping("/seller")
    @PreAuthorize("hasRole('SELLER')")
        public ResponseEntity<String> getSeller() {
        return ResponseEntity.ok("Welcome! , So you are the seller ");
    }

    @GetMapping("/announcements")
    @PreAuthorize("hasAnyRole('SELLER','CUSTOMER','ADMIN')")
    public ResponseEntity<String> getAnnouncement() {
        return ResponseEntity.ok("Welcome TO General annoucement , totally visible for everyone ! ");
    }

}
