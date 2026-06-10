package co.istad.productapisimpledemo.service;

import co.istad.productapisimpledemo.dto.CategoryRequest;
import co.istad.productapisimpledemo.dto.CategoryResponse;
import co.istad.productapisimpledemo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        return null;
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request) {
        return null;
    }

    @Override
    public CategoryResponse deleteCategory(Integer id) {
        return null;
    }

    @Override
    public List<CategoryResponse> findAll() {
        return List.of();
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
