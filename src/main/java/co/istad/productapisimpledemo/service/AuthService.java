package co.istad.productapisimpledemo.service;

import co.istad.productapisimpledemo.dto.auth.RegisterRequest;
import co.istad.productapisimpledemo.dto.auth.RegisterResponse;

public interface AuthService {

    // register the new user
    RegisterResponse register(RegisterRequest request );
}
