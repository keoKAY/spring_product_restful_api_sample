package co.istad.productapisimpledemo.dto.auth;


import lombok.Builder;

@Builder
public record UpdateUserRequest (

        String firstName,
        String lastName,
        String profileUrl,
        String gender,
        String biography

){
}
