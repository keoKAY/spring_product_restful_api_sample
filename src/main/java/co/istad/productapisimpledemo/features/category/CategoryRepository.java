package co.istad.productapisimpledemo.features.category;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Boolean existsByName(String name);

    // pagination
    List<Category> findByParentCategoryIsNull(Sort sort );
}
