package com.example.rectifierBackend.repository;

import com.example.rectifierBackend.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {
    Sample findById(long id);
    Sample save(Sample sample);
}
