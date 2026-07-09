package co.istad.productapisimpledemo.dto.auth;

import lombok.Getter;

// better way to validate the gender
@Getter
public enum GenderOption {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");
    private final String gender;
     GenderOption(String gender) {
        this.gender = gender;
    }
}