package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.advisor.KeycloakOperationException;
import co.istad.productapisimpledemo.dto.auth.RegisterRequest;
import co.istad.productapisimpledemo.dto.auth.RegisterResponse;
import co.istad.productapisimpledemo.entity.Profile;
import co.istad.productapisimpledemo.entity.User;
import co.istad.productapisimpledemo.mapper.UserMapper;
import co.istad.productapisimpledemo.repository.ProfileRepository;
import co.istad.productapisimpledemo.repository.UserRepository;
import co.istad.productapisimpledemo.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ProfileRepository profileRepository;
    private final UserMapper userMapper;
    private final Keycloak keycloak;
    private final UserRepository userRepository;


    public RegisterResponse createUserInKeycloak(String realm , RegisterRequest userRequest ){
        // Define the user profile
        UserRepresentation user = new UserRepresentation();

        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());

        // Keycloak system data
        user.setEmailVerified(true);
        user.setEnabled(true);

        // customize the attributes
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("gender", List.of(userRequest.gender().getGender()));
        attributes.put("biography", List.of(userRequest.biography()));
        user.setAttributes(attributes);

        // Define the password credentials
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(userRequest.password());
        user.setCredentials(Collections.singletonList(passwordCred));

        // send the creation request to the keycloak user
        var userResource  = keycloak.realm(realm).users();
       try(var response = userResource.create(user)){
           int status = response.getStatus(); // getting the status values
            log.info("Response status code: {}", response.getStatus());
            if(response.getStatus() == HttpStatus.CREATED.value()){
                var createdUser = userResource.search(user.getUsername())
                        .getFirst();

                log.info("Created user: {}", createdUser);
                return userMapper.toRegisterResponse(createdUser);
            }
        String error= "";
            try{
                error = response.readEntity(String.class);
            }catch(Exception ignored){}

           switch(status){
            case 409 -> throw new KeycloakOperationException(
                    HttpStatus.CONFLICT,
                    "Username or email already exists "
            );
            case 400 -> throw new KeycloakOperationException(
                    HttpStatus.BAD_REQUEST,
                    error
            );
            default ->
                throw new KeycloakOperationException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to create the user in keycloak "
                );

           }
       }catch(KeycloakOperationException ex ){
           throw ex ;
       }catch (Exception ex ){
           log.error("Keycloak error : ", ex );
           throw new KeycloakOperationException(
                   HttpStatus.INTERNAL_SERVER_ERROR,
                   "Unable to communicate with keycloak"
           );
       }

    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(!request.password().equals(request.confirmedPassword()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Password doesn't much");
        var kcResponse = createUserInKeycloak("ecommerce_realm", request);

        try{
            User user = new User();
            user.setKeycloakId(kcResponse.id());
            user.setUsername(request.username());
            user.setEmail(request.email());
            Profile profile = new Profile();
            // linked profile to the user
            profile.setUser(user);
            user.setProfile(profile);


            userRepository.save(user);
            return kcResponse;
        }catch(Exception ex){
            deleteUserFromKeycloak("ecommerce_realm", kcResponse.id());
            throw ex;
        }
       // return null;
    }

    private void deleteUserFromKeycloak(String realm, String keycloakId) {

        try {
            keycloak.realm(realm)
                    .users()
                    .get(keycloakId)
                    .remove();

            log.info("Rollback: deleted Keycloak user {}", keycloakId);

        } catch (Exception e) {

            log.error("Failed to rollback Keycloak user {}", keycloakId, e);

        }
    }
}
