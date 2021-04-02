package bg.softuni.accommodation.web;

import bg.softuni.accommodation.model.binding.PropertyAddBindingModel;
import bg.softuni.accommodation.model.service.CategoryServiceModel;
import bg.softuni.accommodation.model.service.PropertyServiceModel;
import bg.softuni.accommodation.model.view.PropertyAllViewModel;
import bg.softuni.accommodation.service.CategoryService;
import bg.softuni.accommodation.service.CloudinaryService;
import bg.softuni.accommodation.service.PropertyService;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/properties")
public class PropertyController extends BaseController{

    private final PropertyService propertyService;
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public PropertyController(PropertyService propertyService, CategoryService categoryService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.propertyService = propertyService;
        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProperty(Model model) {

        if (!model.containsAttribute("propertyAddBindingModel")) {
            model.addAttribute("propertyAddBindingModel",new PropertyAddBindingModel());
        }
        model.addAttribute("categories", categoryService.findAllCategoryNames());

        return "property/add-property";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addPropertyConfirm(@Valid @ModelAttribute(name = "propertyAddBindingModel") PropertyAddBindingModel propertyAddBindingModel,
                                           BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes) throws Exception {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("propertyAddBindingModel", propertyAddBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.propertyAddBindingModel", bindingResult);

            return super.redirect("/properties/add");
        }


        PropertyServiceModel propertyServiceModel = this.modelMapper
                .map(propertyAddBindingModel, PropertyServiceModel.class);

        propertyServiceModel.setCategory(
                this.categoryService.findByCategoryName(propertyAddBindingModel.getCategory())
        );

        propertyServiceModel.setAvailable(true);

        propertyServiceModel.setImageUrl(this.cloudinaryService.uploadImage(propertyAddBindingModel.getImageUrl()));

        this.propertyService.addProperty(propertyServiceModel);

        return super.redirect("/properties/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allProperties(@ModelAttribute(name = "model") PropertyAddBindingModel propertyAddBindingModel,
                                    ModelAndView modelAndView) {

        List<PropertyAllViewModel> properties = this.propertyService
                .findAllProperties()
                .stream()
                .map(p -> this.modelMapper.map(p, PropertyAllViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("properties", properties);

        return super.view("property/all-properties", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView modelAndView) {
        PropertyServiceModel propertyServiceModel = this.propertyService.findPropertyById(id);
        PropertyAllViewModel propertyAllViewModel = this.modelMapper
                .map(propertyServiceModel, PropertyAllViewModel.class);

        propertyAllViewModel.setCategory(propertyServiceModel.getCategory().getName());

        modelAndView.addObject("property", propertyAllViewModel);

        return super.view("property/details-property", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) {

        PropertyServiceModel propertyServiceModel = this.propertyService.findPropertyById(id);

        PropertyAllViewModel propertyAllViewModel = this.modelMapper
                .map(propertyServiceModel, PropertyAllViewModel.class);

        propertyAllViewModel.setCategory(propertyServiceModel.getCategory().getName());

        modelAndView.addObject("property", propertyAllViewModel);
        modelAndView.addObject("categories", categoryService.findAllCategoryNames());

        return super.view("property/edit-property", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editProductConfirm(@PathVariable String id, @ModelAttribute PropertyAddBindingModel propertyAddBindingModel,
                                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        PropertyServiceModel propertyServiceModel = this.modelMapper.map(propertyAddBindingModel, PropertyServiceModel.class);

        propertyServiceModel.setCategory(this.modelMapper
                .map(this.categoryService
                        .findByCategoryName(propertyAddBindingModel.getCategory()), CategoryServiceModel.class));

        this.propertyService
                .editProperty(id, propertyServiceModel);

        return super.redirect("/properties/details/" + id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView modelAndView) {
        PropertyAllViewModel propertyAllViewModel = this.modelMapper
                .map(this.propertyService.findPropertyById(id), PropertyAllViewModel.class);

        modelAndView.addObject("property", propertyAllViewModel);

        return super.view("property/delete-property", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteProductConfirm(@PathVariable String id) {
        this.propertyService
                .deleteProperty(id);

        return super.redirect("/properties/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseBody
    public List<PropertyAllViewModel> allAvailableProperties() {

        List<PropertyAllViewModel> properties = this.propertyService
                .findAllAvailableProperties()
                .stream()
                .map(p -> {
                    PropertyAllViewModel propertyAllViewModel = this.modelMapper.map(p, PropertyAllViewModel.class);
                    propertyAllViewModel.setCategory(p.getCategory().getName());
                    return propertyAllViewModel;
                })
                .collect(Collectors.toList());

        return properties;
    }

//    @GetMapping("/all-available")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ModelAndView allAvailableProperties(@ModelAttribute(name = "model") PropertyAddBindingModel propertyAddBindingModel,
//                                               ModelAndView modelAndView) {
//
//        List<PropertyAllViewModel> properties = this.propertyService
//                .findAllAvailableProperties()
//                .stream()
//                .map(p -> {
//                    PropertyAllViewModel propertyAllViewModel = this.modelMapper.map(p, PropertyAllViewModel.class);
//                    propertyAllViewModel.setCategory(p.getCategory().getName());
//                    return propertyAllViewModel;
//                })
//                .collect(Collectors.toList());
//
//
//        modelAndView.addObject("properties", properties);
//
//        return super.view("property/all-properties", modelAndView);
//    }
}
