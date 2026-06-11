package co.istad.productapisimpledemo.service;

import co.istad.productapisimpledemo.advisor.ResourceAlreadyExistException;
import co.istad.productapisimpledemo.dto.CategoryRequest;
import co.istad.productapisimpledemo.dto.CategoryResponse;
import co.istad.productapisimpledemo.entity.Category;
import co.istad.productapisimpledemo.mapper.CategoryMapper;
import co.istad.productapisimpledemo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        // map from request to entity
        Category category = categoryMapper.toEntity(request);
// derived query
        if(categoryRepository.existsByName(request.name())){
            throw new ResourceAlreadyExistException("Category with name = "+request.name()+" already exists");
        }

        var newCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(newCategory);
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request) {
        return null;
    }


    @Override
    public void deleteCategory(Integer id) {
        if(!categoryRepository.existsById(id)) {
          throw new NoSuchElementException("Category with id = " + id + " does not exist");
        }
        categoryRepository.deleteById(id);

    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse findById(Integer id) {
        return null;
    }

    @Override
    public List<CategoryResponse> findByName(String name) {
        return List.of();
    }
}
