package co.istad.productapisimpledemo.entity;

import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private int id;
    private String name;
    private String description;
    private float price;
    private int userId; // user that create the product !

}
