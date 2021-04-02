package bg.softuni.accommodation.model.service;


import bg.softuni.accommodation.model.entity.Property;
import bg.softuni.accommodation.model.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationServiceModel extends BaseServiceModel{

    private String firstName;
    private String lastName;
    private LocalDate rentFrom;
    private BigDecimal totalPrice;
    private UserServiceModel tenant;
    private PropertyServiceModel property;

    public ReservationServiceModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getRentFrom() {
        return rentFrom;
    }

    public void setRentFrom(LocalDate rentFrom) {
        this.rentFrom = rentFrom;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UserServiceModel getTenant() {
        return tenant;
    }

    public void setTenant(UserServiceModel tenant) {
        this.tenant = tenant;
    }

    public PropertyServiceModel getProperty() {
        return property;
    }

    public void setProperty(PropertyServiceModel property) {
        this.property = property;
    }
}
