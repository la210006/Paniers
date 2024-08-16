package com.example.paniers.Dao;

import com.example.paniers.Models.Client;
import java.util.List;

public interface ClientDao {
    Client findById(Long id);
    List<Client> findAll();
    void save(Client client);
    void update(Client client);
    void delete(Long id);

}
