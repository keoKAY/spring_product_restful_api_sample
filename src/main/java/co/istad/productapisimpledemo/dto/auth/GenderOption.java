package co.istad.productapisimpledemo.dto.auth;

import lombok.Getter;

import java.util.Arrays;

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

    public static boolean isValid(String value) {
         return Arrays.stream(values())
                 .anyMatch(g -> g.gender.equalsIgnoreCase(value));
    }
}