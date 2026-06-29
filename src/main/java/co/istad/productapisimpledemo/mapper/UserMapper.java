package co.istad.productapisimpledemo.mapper;

import co.istad.productapisimpledemo.dto.user.CreateUserRequest;
import co.istad.productapisimpledemo.dto.user.UserResponse;
import co.istad.productapisimpledemo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // might need fixing
    UserResponse toUserResponse(User user);
    User toUser(CreateUserRequest request);
}
