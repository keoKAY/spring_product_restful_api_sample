package co.istad.productapisimpledemo.features.auth.restcontroller;


import co.istad.productapisimpledemo.features.auth.AuthService;
import co.istad.productapisimpledemo.features.auth.dto.RegisterRequest;
import co.istad.productapisimpledemo.features.auth.dto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestRestController{
    private final AuthService authService;


    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
}
