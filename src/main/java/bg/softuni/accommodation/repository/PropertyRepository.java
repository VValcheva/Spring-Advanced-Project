package bg.softuni.accommodation.repository;

import bg.softuni.accommodation.model.entity.Property;
import bg.softuni.accommodation.model.service.PropertyServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {

    List<Property> findAllByAvailable(boolean available);
}
