package com.example.rectifierBackend.repository;


import com.example.rectifierBackend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findById(Long id);
    List<Client> findAll();
    //List<Client> findAll(Pageable pageable);
    List<Client> findByCompanyNameContaining(String companyName);
    //List<Client> findByCompanyNameContaining(String companyName, Pageable pageable);
    Client save(Client client);
    void deleteById(Long id);
}
