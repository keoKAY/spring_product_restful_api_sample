package co.istad.productapisimpledemo.features.order.dto;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest (
        Integer customerId,
        String address ,
        String remark,
        BigDecimal discount ,
        List<OrderItemRequest> items
){
}
