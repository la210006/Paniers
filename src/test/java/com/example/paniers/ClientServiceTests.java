package com.example.paniers;

import com.example.paniers.Dao.ClientDao;
import com.example.paniers.Models.Client;
import com.example.paniers.Service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTests {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setId(1L);
        client.setPrenom("Houssam eddine ");
        client.setNom("lamhamdi");
    }

    @Test
    void testGetAllClients() {
        when(clientDao.findAll()).thenReturn(Arrays.asList(client));
        assertNotNull(clientService.getAllClients());
        verify(clientDao, times(1)).findAll();
    }

    @Test
    void testGetClientById() {
        when(clientDao.findById(1L)).thenReturn(Optional.of(client));
        assertEquals(client, clientService.getClientById(1L));
    }

    @Test
    void testAddClient() {
        when(clientDao.save(client)).thenReturn(client);
        clientService.addClient(client);
        verify(clientDao, times(1)).save(client);
    }

    @Test
    void testDeleteClient() {
        clientService.deleteClient(1L);
        verify(clientDao, times(1)).deleteById(1L);
    }
}
