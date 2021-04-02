package bg.softuni.accommodation.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryAddBindingModel {
    private String name;

    public CategoryAddBindingModel() {
    }

    @NotBlank(message = "Category can not br empty!")
    @Size(min = 3, max = 20, message = "Category must be minimum 3 characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
