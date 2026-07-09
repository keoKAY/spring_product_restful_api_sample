package co.istad.productapisimpledemo.mapper;

import co.istad.productapisimpledemo.dto.auth.RegisterRequest;
import co.istad.productapisimpledemo.dto.auth.RegisterResponse;
import co.istad.productapisimpledemo.dto.user.CreateUserRequest;
import co.istad.productapisimpledemo.dto.user.UserResponse;
import co.istad.productapisimpledemo.entity.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "profileUrl", source = "profile.profileUrl")
    @Mapping(target="bio", source = "profile.bio")
    UserResponse toUserResponse(User user);
    User toUser(CreateUserRequest request);


    // for the mapping with the keycloaks

    @Mapping(target = "gender", expression = "java(getGender(request))")
    @Mapping(target = "biography", expression = "java(getBiography(request))")
    RegisterResponse toRegisterResponse(UserRepresentation request);


    default String getGender(UserRepresentation request) {

        if(request.getAttributes()== null || request.getAttributes().isEmpty()) {
            return null;
        }
        return request.getAttributes().get("gender").getFirst();
    }
    default String getBiography(UserRepresentation request) {

        if(request.getAttributes()== null || request.getAttributes().isEmpty()) {
            return null;
        }
        return request.getAttributes().get("biography").getFirst();
    }
}
