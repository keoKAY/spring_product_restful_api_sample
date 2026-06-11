package co.istad.productapisimpledemo.dto;

public record ProductResponse(
        Integer id ,
        String name,
        String description,
        Float price,
        CategoryResponse category
) {
}
