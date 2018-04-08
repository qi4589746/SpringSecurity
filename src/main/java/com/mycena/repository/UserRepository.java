package com.mycena.repository;

import com.mycena.domain.security.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User save(User account);

    int deleteAccountById(Long id);

}
