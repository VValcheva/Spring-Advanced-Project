package bg.softuni.accommodation.service;

import bg.softuni.accommodation.model.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {
    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel);

    List<CategoryServiceModel> findAllCategories();

    CategoryServiceModel findByCategoryName(String name);

    List<String> findAllCategoryNames();

    CategoryServiceModel findCategoryById(String id);

    CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel);

    void deleteCategory(String id);
}
