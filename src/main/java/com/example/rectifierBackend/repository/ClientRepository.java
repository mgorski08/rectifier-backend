package com.example.rectifierBackend.repository;


import com.example.rectifierBackend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findById(long id);
    List<Client> findAll();
    Client save(Client client);
    long deleteById(long id);
}
