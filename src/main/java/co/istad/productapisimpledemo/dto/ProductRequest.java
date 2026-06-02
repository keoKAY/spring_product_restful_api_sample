package co.istad.productapisimpledemo.dto;

public record ProductRequest(
        String name,
        String description,
        Float price
) {
}
