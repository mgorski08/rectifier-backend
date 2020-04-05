package com.example.rectifierBackend.repository;


import com.example.rectifierBackend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findById(long id);
    Client save(Client client);
    long deleteById(long id);
}
