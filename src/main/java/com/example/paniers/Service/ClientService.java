package com.example.paniers.Service;

import com.example.paniers.Dao.ClientDao;
import com.example.paniers.Models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientDao clientDao;

    @Autowired
    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public List<Client> getAllClients() {
        return clientDao.findAll();
    }

    public void addClient(Client client) {
        clientDao.save(client);
    }

    public Client getClientById(Long id) {
        return clientDao.findById(id);
    }

    public void deleteClient(Long id) {
        clientDao.delete(id);
    }
}
