package co.istad.productapisimpledemo.features.order;

import co.istad.productapisimpledemo.features.order.dto.CreateOrderRequest;
import co.istad.productapisimpledemo.features.order.dto.OrderItemResponse;
import co.istad.productapisimpledemo.features.order.dto.OrderResponse;
import co.istad.productapisimpledemo.features.order.entity.Order;
import co.istad.productapisimpledemo.features.order.entity.OrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.email")
    @Mapping(target = "total", expression = "java(calculateTotal(order))")
    @Mapping(target = "subTotal", expression = "java(calculateSubTotal(order))")
    OrderResponse toOrderResponse(Order order);


    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "thumbnail", source = "product.thumbnail")
    @Mapping(target = "lineTotal", expression = "java(orderLine.getUnitPrice().multiply(java.math.BigDecimal.valueOf(orderLine.getQty())))")
    OrderItemResponse toOrderItemResponse(OrderLine orderLine);

    default BigDecimal calculateSubTotal(Order order) {
            if (order.getItems() == null) {
                return BigDecimal.ZERO;
            }

        return order.getItems().stream()
          // qty x unitPrice
          .map(line-> line.getUnitPrice().multiply(BigDecimal.valueOf(line.getQty())) )
          .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    default BigDecimal calculateTotal(Order order) {
       // check discount if it's null return zero
        BigDecimal discount = order.getDiscount() == null ?
                    BigDecimal.ZERO
                    : order.getDiscount();
       return calculateSubTotal(order).subtract(discount);
    }


    Order toOrderEntity(CreateOrderRequest request);
}
