package co.istad.productapisimpledemo.features.user;

import co.istad.productapisimpledemo.features.auth.dto.RegisterResponse;
import co.istad.productapisimpledemo.features.user.dto.CreateUserRequest;
import co.istad.productapisimpledemo.features.user.dto.UserResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "profileUrl", source = "profile.profileUrl")
    @Mapping(target="bio", source = "profile.bio")
    UserResponse toUserResponse(User user);
    User toUser(CreateUserRequest request);



    // for register
    RegisterResponse toRegisterResponse(UserRepresentation user);

    // entity to registerResponse
    @Mapping(target = "firstName", source = "profile.firstName")
    @Mapping(target ="lastName", source = "profile.lastName")
    @Mapping(target = "biography" , source="profile.bio")
    @Mapping(target = "gender", source = "profile.gender")
    RegisterResponse toRegisterResponse(User user);
}
