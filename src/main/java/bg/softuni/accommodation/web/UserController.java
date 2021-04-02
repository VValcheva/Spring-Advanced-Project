package bg.softuni.accommodation.web;

import bg.softuni.accommodation.model.binding.UserRegisterBindingModel;
import bg.softuni.accommodation.model.service.UserServiceModel;
import bg.softuni.accommodation.model.view.UserAllViewModel;
import bg.softuni.accommodation.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {


    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register() {
        return super.view("users/register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute(name = "model") UserRegisterBindingModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return super.view("users/register");
        }

        this.userService
                .registerUser(this.modelMapper.map(model, UserServiceModel.class));

        return super.view("users/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return super.view("users/login");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView all(ModelAndView modelAndView) {
        List<UserAllViewModel> userAllViewModels = this.userService
                .findAllUsers()
                .stream()
                .map(u -> {
                    UserAllViewModel userAllViewModel = this.modelMapper.map(u, UserAllViewModel.class);
                    userAllViewModel.setAuthorities(
                            u.getAuthorities()
                                    .stream()
                                    .map(a -> a.getAuthority())
                                    .collect(Collectors.toSet()));

                    return userAllViewModel;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("users", userAllViewModels);

        return super.view("users/all-users", modelAndView);
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView setUser(@PathVariable String id) {
        this.userService.setUserRole(id, "user");

        return super.redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id) {
        this.userService.setUserRole(id, "admin");

        return super.redirect("/users/all");
    }
}
