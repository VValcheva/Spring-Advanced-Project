package bg.softuni.accommodation.web;

import bg.softuni.accommodation.model.binding.CategoryAddBindingModel;
import bg.softuni.accommodation.model.binding.PropertyAddBindingModel;
import bg.softuni.accommodation.model.service.CategoryServiceModel;
import bg.softuni.accommodation.model.view.CategoryAllViewModel;
import bg.softuni.accommodation.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCategory(Model model) {

        if (!model.containsAttribute("categoryAddBindingModel")) {
            model.addAttribute("categoryAddBindingModel",new CategoryAddBindingModel());
        }

        return "category/add-category";
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addCategoryConfirm(@Valid @ModelAttribute(name = "categoryAddBindingModel") CategoryAddBindingModel categoryAddBindingModel,
                                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryAddBindingModel", categoryAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryAddBindingModel",bindingResult);

            return super.redirect("redirect:add");
        }

        this.categoryService
                .addCategory(this.modelMapper.map(categoryAddBindingModel, CategoryServiceModel.class));

        return super.redirect("/categories/add");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allCategory(ModelAndView modelAndView) {
        List<CategoryAllViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryAllViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("categories", categories);

        return super.view("category/all-categories", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editCategory(@PathVariable String id, ModelAndView modelAndView) {
        CategoryAllViewModel categoryAllViewModel = this.modelMapper
                .map(this.categoryService.findCategoryById(id), CategoryAllViewModel.class);

        modelAndView.addObject("category", categoryAllViewModel);

        return super.view("category/edit-category", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editCategoryConfirm(@PathVariable String id, @ModelAttribute(name = "model")
            CategoryAddBindingModel model) {

        this.categoryService.editCategory(id, this.modelMapper.map(model, CategoryServiceModel.class));

        return super.redirect("/categories/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteCategory(@PathVariable String id, ModelAndView modelAndView) {
        CategoryAllViewModel categoryAllViewModel = this.modelMapper
                .map(this.categoryService.findCategoryById(id), CategoryAllViewModel.class);

        modelAndView.addObject("category", categoryAllViewModel);

        return super.view("/category/delete-category", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteCategoryConfirm(@PathVariable String id) {
        this.categoryService
                .deleteCategory(id);

        return super.redirect("/categories/all");
    }

//    @GetMapping("/fetch")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @ResponseBody
//    public List<CategoryAllViewModel> fetchCategories() {
//        return this.categoryService
//                .findAllCategories()
//                .stream()
//                .map(c -> this.modelMapper.map(c, CategoryAllViewModel.class))
//                .collect(Collectors.toList());
//    }
}
