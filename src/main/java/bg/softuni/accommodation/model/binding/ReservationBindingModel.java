package bg.softuni.accommodation.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ReservationBindingModel {

    private String firstName;
    private String lastName;
    private String rentFrom;

    public ReservationBindingModel() {
    }

    @NotBlank(message = "First name can not be empty!")
    @Size(min = 3, max = 20, message = "First name must be minimum 3 characters!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank(message = "Last name can not be empty!")
    @Size(min = 3, max = 20, message = "Last name must be minimum 3 characters!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotBlank(message = "Date can not be empty!")
    public String getRentFrom() {
        return rentFrom;
    }

    public void setRentFrom(String rentFrom) {
        this.rentFrom = rentFrom;
    }
}
