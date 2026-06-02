package co.istad.productapisimpledemo.service;
import co.istad.productapisimpledemo.dto.ProductRequest;
import co.istad.productapisimpledemo.dto.ProductResponse;
import co.istad.productapisimpledemo.entity.Product;
import co.istad.productapisimpledemo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    // inject the repository here
    private final ProductRepository productRepository;
    private Integer nextId = 1004;
    // mapToEntity
    private Product mapToEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());

        return product;
    }
    // mapToResponse -> convert Entity to Response
    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        // create entity product from the request
        var product = mapToEntity(request);
        // set static userID
        product.setUserId(1);
        product.setId(nextId++);
        return mapToResponse(productRepository.createProduct(product));

    }


    @Override
    public List<ProductResponse> findAllProducts() {
      return productRepository.getAllProducts()
              .stream()
              .map(this::mapToResponse)
              .toList();
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
