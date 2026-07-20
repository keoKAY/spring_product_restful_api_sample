package co.istad.productapisimpledemo.features.order;

import co.istad.productapisimpledemo.features.order.dto.CreateOrderRequest;
import co.istad.productapisimpledemo.features.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    List<OrderResponse> getAllOrders();
    List<OrderResponse> getAllOrdersByCustomerId(Integer customerId);

    // updateOrder
    // cancelOrder
    // deleteOrder

}
