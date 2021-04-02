package bg.softuni.accommodation.service;

import bg.softuni.accommodation.model.service.PropertyServiceModel;

import java.util.List;

public interface PropertyService {
    PropertyServiceModel addProperty(PropertyServiceModel propertyServiceModel);

    List<PropertyServiceModel> findAllProperties();

    List<PropertyServiceModel> findAllAvailableProperties();

    PropertyServiceModel findPropertyById(String id);

    PropertyServiceModel editProperty(String id, PropertyServiceModel propertyServiceModel);

    void deleteProperty(String id);

    void disable(String id);
}
