package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.dto.order.CreateOrderRequest;
import co.istad.productapisimpledemo.dto.order.OrderResponse;
import co.istad.productapisimpledemo.entity.Order;
import co.istad.productapisimpledemo.entity.OrderLine;
import co.istad.productapisimpledemo.mapper.OrderMapper;
import co.istad.productapisimpledemo.repository.OrderRepository;
import co.istad.productapisimpledemo.repository.ProductRepository;
import co.istad.productapisimpledemo.repository.UserRepository;
import co.istad.productapisimpledemo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Order savedOrder = orderRepository.save(order);
        // subTotal
        // total
        // ....

        return null ;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return List.of();
    }

    @Override
    public List<OrderResponse> getAllOrdersByCustomerId(Integer customerId) {
        return List.of();
    }
}
