package com.example.rectifierBackend.repository;

import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    Optional<User> findByUsername(String username);
    User save(User user);
    long deleteById(long id);
    boolean existsByUsername(String username);
}
