package bg.softuni.accommodation.service.impl;

import bg.softuni.accommodation.model.entity.Category;
import bg.softuni.accommodation.model.service.CategoryServiceModel;
import bg.softuni.accommodation.repository.CategoryRepository;
import bg.softuni.accommodation.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) {
        this.categoryRepository
                .saveAndFlush(this.modelMapper
                        .map(categoryServiceModel, Category.class));

        return categoryServiceModel;
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        List<CategoryServiceModel> categoryServiceModels = this.categoryRepository
                .findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryServiceModel.class))
                .collect(Collectors.toList());

        return categoryServiceModels;
    }

    @Override
    public CategoryServiceModel findByCategoryName(String name) {
        Category category = this.categoryRepository.findByName(name);
        return modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public List<String> findAllCategoryNames() {
        return categoryRepository
                .findAllCategoryNames();
    }

    @Override
    public CategoryServiceModel findCategoryById(String id) {

        return this.categoryRepository
                .findById(id)
                .map(c -> this.modelMapper.map(c, CategoryServiceModel.class))
                .orElseThrow(() -> new IllegalArgumentException("Invalid category"));
    }

    @Override
    public CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel) {
        Category category = this.categoryRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);

        category.setName(categoryServiceModel.getName());

        return this.modelMapper
                .map(this.categoryRepository.saveAndFlush(category),
                        CategoryServiceModel.class);
    }

    @Override
    public void deleteCategory(String id) {
        this.categoryRepository
                .deleteById(id);
    }
}
