package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.advisor.KeycloakOperationException;
import co.istad.productapisimpledemo.dto.auth.RegisterRequest;
import co.istad.productapisimpledemo.dto.auth.RegisterResponse;
import co.istad.productapisimpledemo.dto.auth.UpdateUserRequest;
import co.istad.productapisimpledemo.dto.user.UserResponse;
import co.istad.productapisimpledemo.entity.Profile;
import co.istad.productapisimpledemo.entity.User;
import co.istad.productapisimpledemo.mapper.UserMapper;
import co.istad.productapisimpledemo.repository.ProfileRepository;
import co.istad.productapisimpledemo.repository.UserRepository;
import co.istad.productapisimpledemo.service.AuthService;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.internal.ClientResponse;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ProfileRepository profileRepository;
    private final UserMapper userMapper;
    private final Keycloak keycloak;
    private final UserRepository userRepository;
    @Value("${keycloak.realm}")
    private String realm;

   // private Boolean validateClient(){}
    // private validate the roleExists(){}
    public ClientRepresentation getClientById(String clientId){
        return keycloak.realm(realm)
                .clients()
                .findByClientId(clientId).stream()
                .findFirst()
                .orElseThrow(()-> new KeycloakOperationException(
                        HttpStatus.NOT_FOUND, String.format("client with id %s not found", clientId)
                ));
    }
    public RegisterResponse createUserInKeycloak(String realm ,
                                                 RegisterRequest userRequest ){
        // Define the user profile
        UserRepresentation user = new UserRepresentation();

        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());

        // DEV-ONLY: Keycloak system data
//        user.setEmailVerified(true);
//        user.setEnabled(true);

        // PROD-APPROACH
        user.setEnabled(true);
        user.setEmailVerified(false );
        user.setRequiredActions(List.of("VERIFY_EMAIL"));

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
        String createdUserId = null;

       try(var response = userResource.create(user)){
            int status = response.getStatus(); // getting the status values
            log.info("Response status code: {}", response.getStatus());

            if(response.getStatus() == HttpStatus.CREATED.value()){

                // update the data
                createdUserId = CreatedResponseUtil.getCreatedId(response);
//                UserResource createdUser =
//                        keycloak.realm(realm).users().get(userId);
                // instead of getting the full user
//                var createdUser = userResource.search(user.getUsername())
//                        .getFirst();

                // ---------------- for the REALM ROLE --------------------
                /*RoleRepresentation role = keycloak.realm(realm)
                                .roles()
                                 .get("CUSTOMER")
                                 .toRepresentation();
                log.info("Created user: {}", createdUser);
                createdUser.roles()
                        .realmLevel()
                        .add(List.of(role));*/

                ClientRepresentation client = getClientById("spring-boot-app");
                // set the default role to be CUSTOMER or SELLER
                RoleRepresentation role = keycloak
                        .realm(realm).clients()
                        .get(client.getId())
                        .roles()
                        .get("CUSTOMER").toRepresentation();
                // determine the role to be on the client level
                UserResource createdUser = userResource.get(createdUserId);
                // force keycloak to send the email immediately
               // createdUser.executeActionsEmail(List.of("VERIFY_EMAIL"))

                createdUser.roles()
                        .clientLevel(client.getId())
                        .add(List.of(role));
                // sending email after created the user
                log.info("Sending the verification email by keycloak");
                createdUser.sendVerifyEmail();

                return userMapper
                        .toRegisterResponse(
                               createdUser.toRepresentation()
                        );
            }

            String error= "";
            try{
                error = response.readEntity(String.class);
            }catch(Exception ignored){}

           switch(status){
            case 409 -> throw new KeycloakOperationException(HttpStatus.CONFLICT, "Username or email already exists "
            );
            case 400 -> throw new KeycloakOperationException(HttpStatus.BAD_REQUEST, error
            );
            default ->
                throw new KeycloakOperationException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create the user in keycloak "
                );

           }
       } catch (WebApplicationException e) {
           String keycloakErrorResponseBody = e.getResponse().readEntity(String.class);
           log.error("Keycloak executeActionsEmail failed. Raw body text: {}", keycloakErrorResponseBody);
           throw new RuntimeException("Keycloak Email Action Failure: " + keycloakErrorResponseBody, e);
       }
        catch(KeycloakOperationException ex ){
           throw ex ;
       }catch (Exception ex ){
           log.error("Keycloak error : ", ex );

           // something goes wrong, it will delete the user
           if(createdUserId != null){
               deleteUserFromKeycloak(realm, createdUserId);
           }
           throw new KeycloakOperationException(
                   HttpStatus.INTERNAL_SERVER_ERROR,
                   "Unable to communicate with keycloak"
           );
       }

    }

    @Transactional // Ensure the Spring DB transaction roll back if the DB operations failed
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

            // 2. Critical: use saveAndFlush to execute SQL statements immediately.
            // this forces any database level constraint violates to throw inside this block
           // userRepository.save(user);
            userRepository.saveAndFlush(user);
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

            log.error("CRITICAL CRASH: Failed to rollback Keycloak user: {}", keycloakId, e);

        }
    }



    @Override
    @Transactional
    // int id = user id (spring user id ) should be using uuid though for better approach
    public UserResponse updateUser(String keycloakId , UpdateUserRequest request ){
        log.info("Update user: {}", keycloakId);
        User user = userRepository.findUserByKeycloakId(keycloakId)
                                    .orElseThrow(()-> new NoSuchElementException("User not found"));
       var profile = user.getProfile();
        // update the local database fields
        if(request.gender()!= null ) profile.setGender(request.gender());
        if(request.biography()!=null ) profile.setBio(request.biography());
        if(request.firstName()!= null) profile.setFirstName(request.firstName());
        if(request.lastName()!= null) profile.setLastName(request.lastName());

        user.setProfile(profile); // perform the partial updates
        var updatedUser = userRepository.save(user);

        // UPDATE: the user  from keycloak side
       try {
           var kcResource = keycloak.realm(realm)
                   .users()
                   .get(user.getKeycloakId());
           var kcUser = kcResource.toRepresentation();
           // prevent keycloak misunderstood us trying to change the username
           kcUser.setUsername(kcUser.getUsername());
           // updated the fields inside the keycloak
           if(request.firstName()!=null) kcUser.setFirstName(request.firstName());
           if(request.lastName()!=null) kcUser.setLastName(request.lastName());

           Map<String, List<String>> attributes = kcUser.getAttributes() != null
                   ? new HashMap<>(kcUser.getAttributes())
                   : new HashMap<>();

           if (request.gender() != null) {
               attributes.put("gender", List.of(request.gender()));
           }
           if (request.biography() != null) {
               attributes.put("biography", List.of(request.biography()));
           }
           // add the attributes
           kcUser.setAttributes(attributes);
           // Will call to the KC api in order to udpate the data
           kcResource.update(kcUser);
       } catch (WebApplicationException e) {
           e.printStackTrace();
           String keycloakErrorBody = e.getResponse().readEntity(String.class);
           log.error("Keycloak 400 Bad Request Reason: {}", keycloakErrorBody);

           throw new KeycloakOperationException(
                   HttpStatus.BAD_REQUEST,
                   "Keycloak validation failed: " + keycloakErrorBody);
//           throw new KeycloakOperationException(
//                   HttpStatus.INTERNAL_SERVER_ERROR,
//                   "Failed to update user, Transaction Rollback. ");
       }catch (Exception e) {
           log.error("Generic exception during Keycloak sync", e);
           throw new KeycloakOperationException(
                   HttpStatus.INTERNAL_SERVER_ERROR,
                   "Failed to update user, Transaction Rollback.");
       }

        return userMapper.toUserResponse(updatedUser);
    }
}
