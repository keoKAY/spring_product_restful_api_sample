package co.istad.productapisimpledemo.service;

import co.istad.productapisimpledemo.dto.auth.RegisterRequest;
import co.istad.productapisimpledemo.dto.auth.RegisterResponse;

public interface AuthService {
    RegisterResponse register (RegisterRequest registryRequest );
}
