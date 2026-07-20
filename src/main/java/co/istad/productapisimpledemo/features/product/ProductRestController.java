package co.istad.productapisimpledemo.features.product;

import co.istad.productapisimpledemo.features.product.dto.ProductFilter;
import co.istad.productapisimpledemo.features.product.dto.ProductRequest;
import co.istad.productapisimpledemo.features.product.dto.ProductResponse;
import co.istad.productapisimpledemo.features.product.dto.UpdateProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductRestController {
    private final ProductService productService;

    @GetMapping
    @PreAuthorize("hasAuthority('product:view')")
    public Page<ProductResponse> getProducts(ProductFilter filter, Pageable pageable) {
        return productService.findAllProducts(pageable, filter);
    }


    // find product by id
    // localhost:8080/api/v1/products/1001
    @GetMapping("/{id}")
    public ProductResponse getProductByID(@PathVariable Integer id) {
        return productService.findProductById(id);
    }


    @PreAuthorize("hasAuthority('product:create')")
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
