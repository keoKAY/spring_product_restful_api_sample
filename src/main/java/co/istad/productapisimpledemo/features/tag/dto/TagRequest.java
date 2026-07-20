package co.istad.productapisimpledemo.features.tag.dto;

import lombok.Builder;

@Builder
public record TagRequest(
        String name
) {
}
