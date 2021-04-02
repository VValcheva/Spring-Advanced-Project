package bg.softuni.accommodation.model.view;

import bg.softuni.accommodation.model.entity.Property;
import bg.softuni.accommodation.model.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationAllViewModel {

    private String id;
    private String firstName;
    private String lastName;
    private LocalDate rentFrom;
    private BigDecimal totalPrice;
    private String property;

    public ReservationAllViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
