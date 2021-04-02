package bg.softuni.accommodation.repository;

import bg.softuni.accommodation.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Category findByName(String name);

    @Query("SELECT c.name FROM Category c")
    List<String> findAllCategoryNames();
}
