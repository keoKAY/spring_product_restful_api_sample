package co.istad.productapisimpledemo.features.user.dto;


import lombok.Builder;

@Builder
public record CreateUserRequest(
        String email,
        String password,
        String profileUrl ,
        String bio

) {
}
