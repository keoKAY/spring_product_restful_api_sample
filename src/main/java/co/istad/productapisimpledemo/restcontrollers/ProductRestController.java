package co.istad.productapisimpledemo.restcontrollers;

import co.istad.productapisimpledemo.dto.product.ProductRequest;
import co.istad.productapisimpledemo.dto.product.ProductResponse;
import co.istad.productapisimpledemo.dto.product.UpdateProductRequest;
import co.istad.productapisimpledemo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductRestController {
    private final ProductService productService;

    @GetMapping
    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productService.findAllProducts(pageable);
    }

    // find product by id
    // localhost:8080/api/v1/products/1001
    @GetMapping("/{id}")
    public ProductResponse getProductByID(@PathVariable Integer id) {
        return productService.findProductById(id);
    }
    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request){
        return productService.createProduct(request);
    }

    // PATCH localhost:8080/api/v1/products
    // Content-Type JSON
    @PatchMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Integer id, @RequestBody UpdateProductRequest request){
        return productService.updateProduct(id, request);
    }

}
