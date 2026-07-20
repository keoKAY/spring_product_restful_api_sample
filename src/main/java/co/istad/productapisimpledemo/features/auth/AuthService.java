package co.istad.productapisimpledemo.features.auth;

import co.istad.productapisimpledemo.features.auth.dto.RegisterRequest;
import co.istad.productapisimpledemo.features.auth.dto.RegisterResponse;
import co.istad.productapisimpledemo.features.auth.dto.UserUpdateRequest;
import co.istad.productapisimpledemo.features.user.dto.UserResponse;

public interface AuthService {

    // register the new user
    RegisterResponse register(RegisterRequest request );
    UserResponse updateUser(String keycloakID, UserUpdateRequest request);

    void forgotPassword(String email);
}
