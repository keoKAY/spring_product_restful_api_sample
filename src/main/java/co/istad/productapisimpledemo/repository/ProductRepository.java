package co.istad.productapisimpledemo.repository;


import co.istad.productapisimpledemo.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    // because we don't work with database yet
    // productList = represent the data storage
    private final List<Product> productList = new ArrayList<>(){{
        add( new Product(1001,"Cocacola","Nice when cool",23.4f,2));
        add( new Product(1002,"Fanta"," Fantastic Drink",0.75f,4));
        add( new Product(1003,"Sting","Unlimited Sweetness ",0.65f,4));
    }};

    public List<Product> getProductList() {
        return productList;
    }

}
