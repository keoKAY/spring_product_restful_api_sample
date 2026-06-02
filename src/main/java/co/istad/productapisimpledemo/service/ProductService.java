package co.istad.productapisimpledemo.service;

import co.istad.productapisimpledemo.dto.ProductRequest;
import co.istad.productapisimpledemo.dto.ProductResponse;
import co.istad.productapisimpledemo.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

// For the loosely coupling design
// This interface will be implemented by other class

public interface ProductService {
    ProductResponse createProduct(ProductRequest product);
    List<ProductResponse> findAllProducts();
    ProductResponse updateProduct(ProductRequest product);
    boolean deleteProduct(int id);
}
