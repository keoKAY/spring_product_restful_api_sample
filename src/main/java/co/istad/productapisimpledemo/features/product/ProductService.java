package co.istad.productapisimpledemo.features.product;

import co.istad.productapisimpledemo.features.product.dto.ProductFilter;
import co.istad.productapisimpledemo.features.product.dto.ProductRequest;
import co.istad.productapisimpledemo.features.product.dto.ProductResponse;
import co.istad.productapisimpledemo.features.product.dto.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// For the loosely coupling design
// This interface will be implemented by other class

public interface ProductService {
    ProductResponse createProduct(ProductRequest product);
    List<ProductResponse> findAllProducts();

    // for the pagination support when get all products
    Page<ProductResponse> findAllProducts(Pageable pageable, ProductFilter filter);
    // Page<ProductResponse> name(String keywords, Pageable page);

    ProductResponse findProductById(Integer id);
    ProductResponse updateProduct(Integer id, UpdateProductRequest request);
    boolean deleteProduct(Integer id);
}
