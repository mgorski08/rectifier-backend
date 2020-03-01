package com.example.rectifierBackend.repository;

import com.example.rectifierBackend.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {
    Process findById(long id);
    Process save(Process process);
}
