package com.delivery.api.repositories;

import com.delivery.api.entities.User;
import com.delivery.api.entities.UserType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmailAndType(String email, UserType type);
    boolean existsByEmailAndTypeAndIdNot(String email, UserType type, Long id);
}
