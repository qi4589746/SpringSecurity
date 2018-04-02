package com.mycena.repository;

import com.mycena.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by jihung on 3/31/18.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {


    Optional<Role> findById(Long id);


}
