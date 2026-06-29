package co.istad.productapisimpledemo.mapper;

import co.istad.productapisimpledemo.dto.user.CreateUserRequest;
import co.istad.productapisimpledemo.dto.user.UserResponse;
import co.istad.productapisimpledemo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "profileUrl", source = "profile.profileUrl")
    @Mapping(target="bio", source = "profile.bio")
    UserResponse toUserResponse(User user);
    User toUser(CreateUserRequest request);
}
