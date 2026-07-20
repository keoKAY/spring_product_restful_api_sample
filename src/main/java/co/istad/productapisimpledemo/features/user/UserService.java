package co.istad.productapisimpledemo.features.user;

// Specification

import co.istad.productapisimpledemo.features.user.dto.CreateUserRequest;
import co.istad.productapisimpledemo.features.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request );
    List<UserResponse> getAllUsers();
    UserResponse getUserByKeycloakId(String keycloakId);
}
