package com.example.rectifierBackend.repository;

import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BathRepository extends JpaRepository<Bath, Long> {
    Bath findById(long id);
    Bath save(Bath bath);
    long deleteById(long id);
}
