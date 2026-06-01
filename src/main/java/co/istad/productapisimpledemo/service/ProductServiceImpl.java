package co.istad.productapisimpledemo.service;
import co.istad.productapisimpledemo.dto.ProductRequest;
import co.istad.productapisimpledemo.dto.ProductResponse;
import co.istad.productapisimpledemo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    // inject the repository here
    private final ProductRepository productRepository;
    @Override
    public ProductResponse createProduct(ProductRequest product) {
        return null;
    }

    @Override
    public List<ProductResponse> findAllProducts() {
        return List.of();
    }

    @Override
    public ProductResponse updateProduct(ProductRequest product) {
        return null;
    }

    @Override
    public boolean deleteProduct(int id) {
        return false;
    }
}
