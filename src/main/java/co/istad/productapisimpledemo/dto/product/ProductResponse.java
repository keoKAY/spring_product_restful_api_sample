package co.istad.productapisimpledemo.dto.product;

import co.istad.productapisimpledemo.dto.CategoryResponse;

import java.util.Set;

public record ProductResponse(
        Integer id ,
        String name,
        String description,
        Float price,
        CategoryResponse category,
      // Set<TagResponse> tags
        Set<String> tags
) {
}
