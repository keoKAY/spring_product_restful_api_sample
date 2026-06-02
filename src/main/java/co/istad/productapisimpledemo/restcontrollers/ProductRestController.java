package co.istad.productapisimpledemo.restcontrollers;

import co.istad.productapisimpledemo.dto.ProductRequest;
import co.istad.productapisimpledemo.dto.ProductResponse;
import co.istad.productapisimpledemo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductRestController {
    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.findAllProducts();
    }

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest request){
        return productService.createProduct(request);
    }

}
