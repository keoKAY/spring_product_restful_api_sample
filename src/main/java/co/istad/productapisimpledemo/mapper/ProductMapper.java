package co.istad.productapisimpledemo.mapper;


import co.istad.productapisimpledemo.dto.ProductRequest;
import co.istad.productapisimpledemo.dto.ProductResponse;
import co.istad.productapisimpledemo.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = {CategoryMapper.class})
public interface ProductMapper {
    // turn tags object into pure string
    // ["iphone","17 pro max" , "apple"]
    // [{"id":1,....
    ProductResponse mapToResponse(Product request);
    Product mapToProduct(ProductRequest request);
}
