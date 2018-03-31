package com.mycena.repository;

import com.mycena.domain.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jihung on 3/31/18.
 */
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    List<UserRole> findByUserId(Long id);

}
