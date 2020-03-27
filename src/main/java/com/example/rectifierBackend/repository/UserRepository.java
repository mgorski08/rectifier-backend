package com.example.rectifierBackend.repository;

import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    User save(User user);
    long deleteById(long id);
}
