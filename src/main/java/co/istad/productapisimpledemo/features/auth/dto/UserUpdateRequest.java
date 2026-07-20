package co.istad.productapisimpledemo.features.auth.dto;


import lombok.Builder;


// Note: please use the validation package
@Builder
public record UserUpdateRequest(
        String firstName,
        String lastName,
        String gender,
        String biography,
        String profileUrl
        // can be more ...
) {
}
