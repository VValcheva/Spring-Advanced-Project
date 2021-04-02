package bg.softuni.accommodation.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;

    public UserRegisterBindingModel() {
    }

    @NotBlank(message = "Username name can not be empty!")
    @Size(min = 3, max = 20, message = "Username must be minimum 3 characters!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "Password can not be empty!")
    @Size(min = 5, max = 20, message = "Password must be minimum 5 characters!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank(message = "Confirm password can not be empty!")
    @Size(min = 3, max = 20, message = "Password did not match")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotBlank(message = "Email can not be empty!")
    @Size(min = 5, max = 20, message = "Email must be minimum 5 characters!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
