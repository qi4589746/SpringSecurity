package com.mycena.repository;

import com.mycena.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by jihung on 3/31/18.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUserName(String username);

}
