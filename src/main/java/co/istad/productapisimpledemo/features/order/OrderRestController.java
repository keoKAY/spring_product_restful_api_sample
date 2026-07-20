package co.istad.productapisimpledemo.features.order;

import co.istad.productapisimpledemo.features.order.dto.CreateOrderRequest;
import co.istad.productapisimpledemo.features.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/orders")
public class OrderRestController {
    private final OrderService orderService;
    @GetMapping
    public List<OrderResponse> getOrders() {
        return orderService.getAllOrders();
    }
    @PostMapping
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request){
        return orderService.createOrder(request);
    }
    // get all orders of customer with id
    // typically recide in the customer rest controller
    // api/v1/customers/12/orders
}
