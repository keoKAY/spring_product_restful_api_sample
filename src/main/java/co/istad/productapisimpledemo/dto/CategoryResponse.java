package co.istad.productapisimpledemo.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Integer id,
        String name,
        String description
) {
}
