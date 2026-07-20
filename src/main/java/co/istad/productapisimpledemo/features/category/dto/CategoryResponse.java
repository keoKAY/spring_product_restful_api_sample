package co.istad.productapisimpledemo.features.category.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponse(
        Integer id,
        String name,
        String description,
        // just added
        List<CategoryResponse> subCategories
) {
}
