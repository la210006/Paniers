package com.example.paniers;

import com.example.paniers.Controleurs.ClientControleur;
import com.example.paniers.Models.Client;
import com.example.paniers.Service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClientControleurTests {

    @InjectMocks
    private ClientControleur clientControleur;

    @Mock
    private ClientService clientService;

    private MockMvc mockMvc;
    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientControleur).build();
        client = new Client();
        client.setId(1L);
        client.setPrenom("Houssam edd");
        client.setNom("LAMHA");
    }

    @Test
    void testGetAllClients() throws Exception {
        when(clientService.getAllClients()).thenReturn(Arrays.asList(client));
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].prenom").value("Houssam edd"))
                .andExpect(jsonPath("$[0].nom").value("LAMHA"));
    }

    @Test
    void testGetClientById() throws Exception {
        when(clientService.getClientById(1L)).thenReturn(client);
        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenom").value("Houssam edd"))
                .andExpect(jsonPath("$.nom").value("LAMHA"));
    }

    @Test
    void testAddClient() throws Exception {
        doNothing().when(clientService).addClient(any(Client.class));
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prenom\":\"Houssam edd\", \"nom\":\"LAMHA\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteClient() throws Exception {
        doNothing().when(clientService).deleteClient(1L);
        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isOk());
    }
}
