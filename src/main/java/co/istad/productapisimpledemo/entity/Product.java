package co.istad.productapisimpledemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.nio.channels.FileLock;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "product_tbl")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Float price;

    private Integer userId; // user that create the product !
   // private Integer categoryId;



    @ManyToMany
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    // determined foreign key
    @JoinColumn(name = "category_id")
    private Category category;

}
