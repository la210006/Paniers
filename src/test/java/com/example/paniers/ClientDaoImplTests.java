package com.example.paniers;

import com.example.paniers.DaoImpl.ClientDaoImpl;
import com.example.paniers.Models.Client;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
public class ClientDaoImplTests {

    private static ClientDaoImpl clientDao;

    @BeforeAll
    public static void setup() {
        // Initialiser le DAO
        clientDao = new ClientDaoImpl();

        // S'assurer que la base de données est vide avant de commencer
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/db/paniersTest.db");
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM reservations");
            stmt.execute("DELETE FROM clients");
        } catch (SQLException e) {
            fail("Erreur lors de la préparation de la base de données pour les tests: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    public void testSaveAndFindById() {
        // Créer un nouveau client
        Client client = new Client();
        client.setPrenom("Houssam");
        client.setNom("Lamhamdi");
        client.setCommune("Nador");

        // Sauvegarder le client
        clientDao.save(client);

        // Récupérer le client par son ID
        List<Client> allClients = clientDao.findAll();
        assertEquals(1, allClients.size(), "Il devrait y avoir un client dans la base de données.");
        Client savedClient = allClients.get(0);

        // Vérifier les données
        assertNotNull(savedClient.getId(), "L'ID du client ne devrait pas être nul après l'insertion.");
        assertEquals("Houssam", savedClient.getPrenom(), "Le prénom devrait être 'Jean'.");
        assertEquals("Lamhamdi", savedClient.getNom(), "Le nom devrait être 'Dupont'.");
        assertEquals("Nador", savedClient.getCommune(), "La commune devrait être 'Paris'.");
    }

    @Test
    @Order(2)
    public void testUpdate() {
        // Récupérer le client
        List<Client> allClients = clientDao.findAll();
        assertEquals(1, allClients.size(), "Il devrait y avoir un client dans la base de données.");
        Client client = allClients.get(0);

        // Mettre à jour le client
        client.setPrenom("Adam");
        client.setNom("Saint");
        client.setCommune("Marcinelle");
        clientDao.update(client);

        // Récupérer le client mis à jour
        Client updatedClient = clientDao.findById(client.getId());

        // Vérifier les nouvelles données
        assertEquals("Adam", updatedClient.getPrenom(), "Le prénom devrait être 'Pierre'.");
        assertEquals("Saint", updatedClient.getNom(), "Le nom devrait être 'Durand'.");
        assertEquals("Marcinelle", updatedClient.getCommune(), "La commune devrait être 'Lyon'.");
    }

    @Test
    @Order(3)
    public void testDelete() {
        // Récupérer le client
        List<Client> allClients = clientDao.findAll();
        assertEquals(1, allClients.size(), "Il devrait y avoir un client dans la base de données.");
        Client client = allClients.get(0);

        // Supprimer le client
        clientDao.delete(client.getId());

        // Vérifier que le client a été supprimé
        Client deletedClient = clientDao.findById(client.getId());
        assertNull(deletedClient, "Le client devrait être supprimé de la base de données.");
    }

    @Test
    @Order(4)
    public void testFindAll() {
        // Ajouter plusieurs clients
        Client client1 = new Client();
        client1.setPrenom("Houssam");
        client1.setNom("Lamhamdi");
        client1.setCommune("Nador");

        Client client2 = new Client();
        client2.setPrenom("Adam");
        client2.setNom("Saint");
        client2.setCommune("Marcinelle");

        clientDao.save(client1);
        clientDao.save(client2);

        // Récupérer tous les clients
        List<Client> allClients = clientDao.findAll();
        assertEquals(2, allClients.size(), "Il devrait y avoir deux clients dans la base de données.");

        // Vérifier les données du premier client
        Client retrievedClient1 = allClients.get(0);
        assertEquals("Houssam", retrievedClient1.getPrenom(), "Le prénom devrait être 'Alice'.");
        assertEquals("Lamhamdi", retrievedClient1.getNom(), "Le nom devrait être 'Martin'.");
        assertEquals("Nador", retrievedClient1.getCommune(), "La commune devrait être 'Marseille'.");

        // Vérifier les données du deuxième client
        Client retrievedClient2 = allClients.get(1);
        assertEquals("Adam", retrievedClient2.getPrenom(), "Le prénom devrait être 'Bob'.");
        assertEquals("Saint", retrievedClient2.getNom(), "Le nom devrait être 'Bernard'.");
        assertEquals("Marcinelle", retrievedClient2.getCommune(), "La commune devrait être 'Nice'.");
    }
}
