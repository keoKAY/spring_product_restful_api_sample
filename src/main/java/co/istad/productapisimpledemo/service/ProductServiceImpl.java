package co.istad.productapisimpledemo.service;
import co.istad.productapisimpledemo.dto.ProductRequest;
import co.istad.productapisimpledemo.dto.ProductResponse;
import co.istad.productapisimpledemo.dto.UpdateProductRequest;
import co.istad.productapisimpledemo.entity.Product;
import co.istad.productapisimpledemo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultLifecycleProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    // inject the repository here
    private final ProductRepository productRepository;
    private final DefaultLifecycleProcessor defaultLifecycleProcessor;
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
    public ProductResponse findProductById(Integer id) {
       var product =   productRepository.findProductById(id);
       if(product == null) {
           // throw not found exception, but skip it for now
           log.info("Product with id {} not found", id);
           return null;
       }
        return mapToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Integer id , UpdateProductRequest request) {
        // find existing product
        var existingProduct = productRepository.findProductById(id);

        if(existingProduct == null) {
            log.info("Product with id {} not found", id);
            // throw exception
            return null;
        }
        if(request.name()!=null)
            existingProduct.setName(request.name());
        if(request.description()!=null)
            existingProduct.setDescription(request.description());
        if(request.price()!=null)
            existingProduct.setPrice(request.price());
        // update product
        productRepository.updateProduct(existingProduct);
        return mapToResponse(existingProduct);
    }


    @Override
    public boolean deleteProduct(Integer id) {
        return false;
    }


}
