package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.dto.user.CreateUserRequest;
import co.istad.productapisimpledemo.dto.user.UserResponse;
import co.istad.productapisimpledemo.entity.Profile;
import co.istad.productapisimpledemo.mapper.UserMapper;
import co.istad.productapisimpledemo.repository.ProfileRepository;
import co.istad.productapisimpledemo.repository.UserRepository;
import co.istad.productapisimpledemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final Keycloak keycloak;


    public void createUserInKeycloak(String realm , String username, String email, String password ){
        // Define the user profile
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setEmail(email);
        user.setEmailVerified(true);

        // Define the password credentials
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        user.setCredentials(Collections.singletonList(passwordCred));

        // send the creation request to the keycloak user
        var response = keycloak.realm(realm).users().create(user);
        if(response.getStatus() == 201 ){
            System.out.println("User created successfully inside the keycloak ");
        }else {
            System.out.println("Failed to create the user, status code: "+response.getStatus());
        }
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        var user = userMapper.toUser(request);
        var profile = new Profile();

        profile.setBio(request.bio());
        profile.setProfileUrl(request.profileUrl());
         // linked profile to user
        profile.setUser(user);
        user.setProfile(profile);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::toUserResponse)
                .toList();
    }
}
