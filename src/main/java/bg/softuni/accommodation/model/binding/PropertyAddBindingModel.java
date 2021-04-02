package bg.softuni.accommodation.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PropertyAddBindingModel {

    private String name;
    private MultipartFile imageUrl;
    private String description;
    private BigDecimal pricePerMonth;
    private String category;

    public PropertyAddBindingModel() {
    }

    @NotBlank(message = "Name can not be empty!")
    @Size(min = 3, message = "Name must be minimum 3 characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Please upload image")
    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotBlank(message = "Description can not be empty!")
    @Size(min = 5, max = 500, message = "Description must be minimum 5 and maximum 500 characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Price can not be empty!")
    @DecimalMin(value = "0", message = "Price must be positive number!")
    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    @NotBlank(message = "Select category!")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
