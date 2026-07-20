package co.istad.productapisimpledemo.features.order.dto;

public record OrderItemRequest(
        Integer productId,
        Integer qty
) {
}
