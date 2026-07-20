package co.istad.productapisimpledemo.features.order;

import co.istad.productapisimpledemo.features.order.dto.CreateOrderRequest;
import co.istad.productapisimpledemo.features.order.dto.OrderResponse;
import co.istad.productapisimpledemo.features.order.entity.Order;
import co.istad.productapisimpledemo.features.order.entity.OrderLine;
import co.istad.productapisimpledemo.features.order.entity.OrderStatus;
import co.istad.productapisimpledemo.features.product.ProductRepository;
import co.istad.productapisimpledemo.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    // bad request
    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        // mapping
        Order order = orderMapper.toOrderEntity(request);
        order.setOrderedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        // 1. validation on customer existence
        var customer = userRepository.findById(request.customerId())
                .orElseThrow(()->new NoSuchElementException("Customer with id ="+request.customerId()+" not found!"));
        order.setCustomer(customer);

        // 2. validation on orderItems
        var orderLines = request.items().stream()
                .map(item-> {
                    // validate on product id
                    var product = productRepository.findById(item.productId())
                            .orElseThrow(()-> new NoSuchElementException("Product with id ="+item.productId()+" not found!"));
                    // validation if the product available  or not
                            if(!product.getIsAvailable())
                        throw new NoSuchElementException("Product with id ="+item.productId()+" not available!");
                    // update qty
                    // stock - order qty
                    // Update product in stock
                    product.setQty(product.getQty()- item.qty());
                    var orderLine = new OrderLine();
                        orderLine.setProduct(product);
                        orderLine.setUnitPrice(product.getPrice());
                        orderLine.setQty(item.qty());
                        orderLine.setOrder(order);
                    return orderLine;
                }

                ).toList();
            // save all the orderItem inside our order
            order.setItems(orderLines);

        BigDecimal discount = request.discount()==null ?
                                BigDecimal.ZERO :
                            request.discount();
        // product1 5$    10units
        // product2 10$   10units
        // 10x5 = 50
        // 10x10 = 100

       // BigDecimal total = subTotal.subtract(discount);

        Order savedOrder = orderRepository.save(order);
       // return orderMapper.toOrderResponse(savedOrder);
        /*return new OrderResponse(
            savedOrder.getId(),
            customer.getId(),
            customer.getEmail(),
            savedOrder.getStatus(),
            subTotal,
            discount,
            total,
            savedOrder.getOrderedAt(),
                savedOrder.getItems().stream()
                        .map(item-> new OrderItemResponse(
                               item.getId(),
                               item.getProduct().getName(),
                               item.getProduct().getThumbnail(),
                               item.getQty(),
                                item.getUnitPrice(),
                                item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQty()))
                                )
                ).toList()
        );*/

        return orderMapper.toOrderResponse(savedOrder);
    }
    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream().map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getAllOrdersByCustomerId(Integer customerId) {
        return List.of();
    }
}
