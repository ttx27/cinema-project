package com.example.cinemaapi.repository.user;

import com.example.cinemaapi.model.user.Role;
import com.example.cinemaapi.model.user.UserRoles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * .
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByCode(UserRoles code);

    Iterable<Role> findByIdIn(List<Long> ids);

    Set<Role> findByCodeIn(List<UserRoles> codes);
}
