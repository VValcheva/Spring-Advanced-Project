package bg.softuni.accommodation.web;


import bg.softuni.accommodation.model.binding.ReservationBindingModel;
import bg.softuni.accommodation.model.service.PropertyServiceModel;
import bg.softuni.accommodation.model.service.ReservationServiceModel;
import bg.softuni.accommodation.model.service.UserServiceModel;
import bg.softuni.accommodation.model.view.PropertyAllViewModel;
import bg.softuni.accommodation.model.view.ReservationAllViewModel;
import bg.softuni.accommodation.service.PropertyService;
import bg.softuni.accommodation.service.ReservationService;
import bg.softuni.accommodation.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservations")
public class ReservationController extends BaseController{

    private final ReservationService reservationService;
    private final PropertyService propertyService;
    private final UserService userService;
    private final ModelMapper modelMapper;


    public ReservationController(ReservationService reservationService,
                                 PropertyService propertyService,
                                 UserService userService,
                                 ModelMapper modelMapper) {
        this.reservationService = reservationService;
        this.propertyService = propertyService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/property/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView reserveProperty(@PathVariable String id, Model model,
                                     Principal principal) {

        if (!model.containsAttribute("reservationBindingModel")){
            model.addAttribute("reservationBindingModel", new ReservationBindingModel());
        }


        PropertyAllViewModel propertyAllViewModel = this.modelMapper
                .map(this.propertyService.findPropertyById(id), PropertyAllViewModel.class);

        model.addAttribute("property", propertyAllViewModel);
        //modelAndView.addObject("customerName", principal.getName());
        //Make this with th:text="'Customer: ' + ${#authentication.getName()}" in html input element

        return super.view("reservation/reservation");
    }


    @PostMapping("/property/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView reservePropertyConfirm(@PathVariable String id, @Valid @ModelAttribute(name = "reservationBindingModel")
            ReservationBindingModel reservationBindingModel,
                                               BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes,
                                        Principal principal) throws Exception {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("reservationBindingModel", reservationBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.reservationBindingModel", bindingResult);

            return super.redirect("/reservations/property/" + id);
        }

        ReservationServiceModel reservationServiceModel = this.modelMapper
                .map(reservationBindingModel, ReservationServiceModel.class);

        PropertyServiceModel propertyServiceModel = propertyService.findPropertyById(id);
        String username = principal.getName();
        UserServiceModel userServiceModel = userService.findUserByUsername(username);

        reservationServiceModel.setProperty(propertyServiceModel);
        reservationServiceModel.setRentFrom(LocalDate.parse(reservationBindingModel.getRentFrom()));
        reservationServiceModel.setTotalPrice(propertyServiceModel.getPricePerMonth().multiply(BigDecimal.valueOf(12)));
        reservationServiceModel.setTenant(userServiceModel);

        propertyService.disable(id);
//        propertyServiceModel.setAvailable(false);

        this.reservationService
                .createReservation(reservationServiceModel);

//        return super.redirect("/orders/my/" + model.getCustomerName());
        return super.redirect("/reservations/my");
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView allOrdersByCustomer(Principal principal, ModelAndView modelAndView) {
        List<ReservationAllViewModel> reservations = this.reservationService
                .findAllReservationsByTenantUsername(principal.getName())
                .stream()
                .map(r -> {
                    ReservationAllViewModel reservationAllViewModel =
                        this.modelMapper.map(r, ReservationAllViewModel.class);
                    reservationAllViewModel.setProperty(r.getProperty().getName());
                    return reservationAllViewModel;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("reservations", reservations);

        return view("reservation/my-reservations", modelAndView);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allOrders(ModelAndView modelAndView) {

        List<ReservationAllViewModel> reservations = this.reservationService
                .findAllReservations()
                .stream()
                .map(r -> {
                    ReservationAllViewModel reservationAllViewModel =
                            this.modelMapper.map(r, ReservationAllViewModel.class);
                    reservationAllViewModel.setProperty(r.getProperty().getName());
                    return reservationAllViewModel;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("reservations", reservations);

        return view("reservation/all-reservations", modelAndView);
    }
}
