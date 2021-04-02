package bg.softuni.accommodation.service;

import bg.softuni.accommodation.model.entity.Reservation;
import bg.softuni.accommodation.model.service.ReservationServiceModel;

import java.util.Arrays;
import java.util.List;

public interface ReservationService {
    void createReservation(ReservationServiceModel reservationServiceModel);

    List<Reservation> findAllReservationsByTenantUsername(String username);

    List<Reservation> findAllReservations();
}
