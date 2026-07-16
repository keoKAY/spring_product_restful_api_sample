package co.istad.productapisimpledemo.service;

import co.istad.productapisimpledemo.dto.auth.RegisterRequest;
import co.istad.productapisimpledemo.dto.auth.RegisterResponse;
import co.istad.productapisimpledemo.dto.auth.UpdateUserRequest;
import co.istad.productapisimpledemo.dto.user.UserResponse;

public interface AuthService {
    RegisterResponse register (RegisterRequest registryRequest );

    UserResponse updateUser(String  keycloakId , UpdateUserRequest updateUserRequest );
}
