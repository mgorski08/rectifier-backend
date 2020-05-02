package com.example.rectifierBackend.repository;

import com.example.rectifierBackend.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {
    Optional<Process> findById(long id);
    List<Process> findAll();
    Process save(Process process);
    long deleteById(long id);
}
