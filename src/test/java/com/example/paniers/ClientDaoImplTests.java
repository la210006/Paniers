package com.example.paniers;

import com.example.paniers.DaoImpl.ClientDaoImpl;
import com.example.paniers.Models.Client;
import com.example.paniers.Models.Reservation;
import com.example.paniers.Util.Persistence;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
public class ClientDaoImplTests {

    private static ClientDaoImpl clientDao;

    @BeforeAll
    public static void setup() {
        clientDao = new ClientDaoImpl();

        // S'assurer que la base de données est vide avant de commencer
        try (Connection conn = Persistence.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM reservations");
            stmt.execute("DELETE FROM clients");
        } catch (Exception e) {
            fail("Erreur lors de la préparation de la base de données pour les tests: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    public void testSaveAndFindById() {
        // Créer un nouveau client
        Client client = new Client();
        client.setPrenom("Houssam");
        client.setNom("lamhamdi");
        client.setCommune("Nador");

        // Sauvegarder le client
        clientDao.save(client);

        // Récupérer le client par son ID
        Client savedClient = clientDao.findAll().get(0);

        // Vérifier les données
        assertNotNull(savedClient, "Le client devrait être trouvé dans la base de données.");
        assertEquals("Houssam", savedClient.getPrenom(), "Le prénom devrait être Houssam.");
        assertEquals("lamhamdi", savedClient.getNom(), "Le nom devrait être lamhamdi.");
        assertEquals("Nador", savedClient.getCommune(), "La commune devrait être Nador.");
    }

    @Test
    @Order(2)
    public void testUpdate() {
        // Récupérer le client
        Client client = clientDao.findAll().get(0);

        // Mettre à jour le client
        client.setPrenom("samir");
        client.setNom("lail");
        client.setCommune("Lyon");
        clientDao.update(client);

        // Récupérer le client mis à jour
        Client updatedClient = clientDao.findById(client.getId());

        // Vérifier les nouvelles données
        assertNotNull(updatedClient, "Le client mis à jour devrait être trouvé.");
        assertEquals("samir", updatedClient.getPrenom(), "Le prénom devrait être mis à jour à samit.");
        assertEquals("lail", updatedClient.getNom(), "Le nom devrait être mis à jour à lail.");
        assertEquals("Lyon", updatedClient.getCommune(), "La commune devrait être mise à jour à Lyon.");
    }

    @Test
    @Order(3)
    public void testDelete() {
        // Récupérer le client
        Client client = clientDao.findAll().get(0);

        // Supprimer le client
        clientDao.delete(client.getId());

        // Vérifier que le client a été supprimé
        Client deletedClient = clientDao.findById(client.getId());
        assertNull(deletedClient, "Le client devrait être supprimé de la base de données.");
    }
}
