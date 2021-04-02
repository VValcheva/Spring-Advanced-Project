package bg.softuni.accommodation.service.impl;

import bg.softuni.accommodation.model.entity.Category;
import bg.softuni.accommodation.model.entity.Property;
import bg.softuni.accommodation.model.service.PropertyServiceModel;
import bg.softuni.accommodation.repository.PropertyRepository;
import bg.softuni.accommodation.service.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    public PropertyServiceImpl(PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PropertyServiceModel addProperty(PropertyServiceModel propertyServiceModel) {
        Property property = modelMapper.map(propertyServiceModel, Property.class);
        this.propertyRepository.save(property);
        return propertyServiceModel;
    }

    @Override
    public List<PropertyServiceModel> findAllProperties() {

        return this.propertyRepository
                .findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PropertyServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyServiceModel> findAllAvailableProperties() {
        return this.propertyRepository
                .findAllByAvailable(true)
                .stream()
                .map(p -> this.modelMapper.map(p, PropertyServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PropertyServiceModel findPropertyById(String id) {
        return this.propertyRepository
                .findById(id)
                .map(p -> this.modelMapper.map(p, PropertyServiceModel.class))
                .orElseThrow(() -> new IllegalArgumentException("Property not found!"));
    }

    @Override
    public PropertyServiceModel editProperty(String id, PropertyServiceModel propertyServiceModel) {
        Property property = this.propertyRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));

        property.setName(propertyServiceModel.getName());
        property.setDescription(propertyServiceModel.getDescription());
        property.setPricePerMonth(propertyServiceModel.getPricePerMonth());
        property.setCategory(this.modelMapper.map(propertyServiceModel.getCategory(), Category.class));

        return this.modelMapper.map(this.propertyRepository.saveAndFlush(property), PropertyServiceModel.class);
    }

    @Override
    public void deleteProperty(String id) {
        propertyRepository.deleteById(id);
    }

    @Override
    public void disable(String id) {
        Property property = propertyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        property.setAvailable(false);
        this.propertyRepository.saveAndFlush(property);
    }
}
