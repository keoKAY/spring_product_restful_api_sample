package co.istad.productapisimpledemo.features.category;

import co.istad.productapisimpledemo.features.category.dto.CategoryRequest;
import co.istad.productapisimpledemo.features.category.dto.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);
    Category toEntity(CategoryRequest request);
}
