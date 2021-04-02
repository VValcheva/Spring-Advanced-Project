package bg.softuni.accommodation.service;

import bg.softuni.accommodation.model.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {
    void seedRolesInDB();

    //void assignUserRoles(UserServiceModel userServiceModel, long numbersOfUsers);

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
