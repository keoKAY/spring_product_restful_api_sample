package co.istad.productapisimpledemo.features.user.dto;


import lombok.Builder;

@Builder
public record UserResponse(
        Long id ,
        String email,
        String profileUrl,
        String bio
) {
}
