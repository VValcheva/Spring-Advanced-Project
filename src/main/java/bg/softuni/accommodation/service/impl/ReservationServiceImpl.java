package bg.softuni.accommodation.service.impl;

import bg.softuni.accommodation.model.entity.Property;
import bg.softuni.accommodation.model.entity.Reservation;
import bg.softuni.accommodation.model.entity.User;
import bg.softuni.accommodation.model.service.ReservationServiceModel;
import bg.softuni.accommodation.repository.ReservationRepository;
import bg.softuni.accommodation.service.PropertyService;
import bg.softuni.accommodation.service.ReservationService;
import bg.softuni.accommodation.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final PropertyService propertyService;
    private final UserService userService;
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    public ReservationServiceImpl(PropertyService propertyService, UserService userService, ReservationRepository reservationRepository, ModelMapper modelMapper) {
        this.propertyService = propertyService;
        this.userService = userService;
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createReservation(ReservationServiceModel reservationServiceModel) {

        User user = this.modelMapper.map(reservationServiceModel.getTenant(), User.class);

        Property property = this.modelMapper.map(reservationServiceModel.getProperty(), Property.class);

        Reservation reservation = this.modelMapper.map(reservationServiceModel, Reservation.class);

        //ToDO make the controller thinner
//        reservation.setFirstName(reservationServiceModel.getFirstName());
//        reservation.setLastName(reservationServiceModel.getLastName());
//        reservation.setRentFrom(reservationServiceModel.getRentFrom());
//        reservation.setTotalPrice(reservationServiceModel.getTotalPrice());
        reservation.setTenant(user);
        reservation.setProperty(property);
        System.out.println();
        this.reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> findAllReservationsByTenantUsername(String username) {
        return this.reservationRepository.findAllByTenantUsernameOrderByRentFromDesc(username);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return this.reservationRepository.findAll();
    }
}
