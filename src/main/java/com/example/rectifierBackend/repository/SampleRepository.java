package com.example.rectifierBackend.repository;

import com.example.rectifierBackend.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {
    Optional<Sample> findById(long id);
    List<Sample> findAll();
    List<Sample> findAllByProcessIdOrderByTimestampAsc(long processId);
    Sample save(Sample sample);
    long deleteById(long id);
}
