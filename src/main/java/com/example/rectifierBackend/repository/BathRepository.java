package com.example.rectifierBackend.repository;

import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BathRepository extends JpaRepository<Bath, Long> {
    Bath findById(long id);
    List<Bath> findAll();
    Bath save(Bath bath);
    long deleteById(long id);
}
