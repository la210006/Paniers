package com.example.paniers;

import com.example.paniers.Dao.ClientDao;
import com.example.paniers.DaoImpl.ClientDaoImpl;
import com.example.paniers.DaoImpl.ReservationDaoImpl;
import com.example.paniers.Models.Client;
import com.example.paniers.Models.Reservation;
import com.example.paniers.Util.Persistence;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationDaoImplTest {

    private static ReservationDaoImpl reservationDao;
    private static ClientDao clientDao;

    @BeforeAll
    public static void setup() {
        // Initialisation des DAO
        clientDao = new ClientDaoImpl();
        reservationDao = new ReservationDaoImpl();
        reservationDao.setClientDao(clientDao);

        // S'assurer que la base de données est vide avant de commencer
        try (Connection conn = Persistence.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM reservations");
            stmt.execute("DELETE FROM clients");
        } catch (SQLException e) {
            fail("Erreur lors de la préparation de la base de données pour les tests: " + e.getMessage());
        }

        // Ajouter un client pour les tests de réservation
        Client client = new Client();
        client.setPrenom("John");
        client.setNom("Doe");
        client.setCommune("Paris");
        clientDao.save(client);
    }

    @Test
    @Order(1)
    public void testSaveAndFindById() {
        // Récupérer le client de test
        Client client = clientDao.findAll().get(0);

        // Créer une nouvelle réservation
        Reservation reservation = new Reservation();
        reservation.setDateCommande(LocalDate.now());
        reservation.setQuantite(10);
        reservation.setPrix(100.0);
        reservation.setPrixUnitaire(10.0);
        reservation.setClient(client);

        // Sauvegarder la réservation
        reservationDao.save(reservation);

        // Récupérer la réservation par son ID
        Reservation savedReservation = reservationDao.findById(reservation.getId());

        // Vérifier les données
        assertNotNull(savedReservation, "La réservation devrait être trouvée dans la base de données.");
        assertEquals(10, savedReservation.getQuantite(), "La quantité devrait être 10.");
        assertEquals(100.0, savedReservation.getPrix(), "Le prix devrait être 100.0.");
        assertEquals(10.0, savedReservation.getPrixUnitaire(), "Le prix unitaire devrait être 10.0.");
        assertEquals(client.getId(), savedReservation.getClient().getId(), "L'ID du client devrait correspondre.");
    }

    @Test
    @Order(2)
    public void testUpdate() {
        // Récupérer la réservation
        Reservation reservation = reservationDao.findAll().get(0);

        // Mettre à jour la réservation
        reservation.setQuantite(20);
        reservation.setPrix(200.0);
        reservationDao.update(reservation);

        // Récupérer la réservation mise à jour
        Reservation updatedReservation = reservationDao.findById(reservation.getId());

        // Vérifier les nouvelles données
        assertNotNull(updatedReservation, "La réservation mise à jour devrait être trouvée.");
        assertEquals(20, updatedReservation.getQuantite(), "La quantité devrait être mise à jour à 20.");
        assertEquals(200.0, updatedReservation.getPrix(), "Le prix devrait être mis à jour à 200.0.");
    }

    @Test
    @Order(3)
    public void testDelete() {
        // Récupérer la réservation
        Reservation reservation = reservationDao.findAll().get(0);

        // Supprimer la réservation
        reservationDao.delete(reservation.getId());

        // Vérifier que la réservation a été supprimée
        Reservation deletedReservation = reservationDao.findById(reservation.getId());
        assertNull(deletedReservation, "La réservation devrait être supprimée de la base de données.");
    }

    @Test
    @Order(4)
    public void testFindAll() {
        // Ajouter plusieurs réservations
        Client client = clientDao.findAll().get(0);

        Reservation reservation1 = new Reservation();
        reservation1.setDateCommande(LocalDate.now());
        reservation1.setQuantite(5);
        reservation1.setPrix(50.0);
        reservation1.setPrixUnitaire(10.0);
        reservation1.setClient(client);

        Reservation reservation2 = new Reservation();
        reservation2.setDateCommande(LocalDate.now());
        reservation2.setQuantite(3);
        reservation2.setPrix(30.0);
        reservation2.setPrixUnitaire(10.0);
        reservation2.setClient(client);

        reservationDao.save(reservation1);
        reservationDao.save(reservation2);

        // Récupérer toutes les réservations
        List<Reservation> allReservations = reservationDao.findAll();
        assertEquals(2, allReservations.size(), "Il devrait y avoir deux réservations dans la base de données.");

        // Vérifier les données de la première réservation
        Reservation retrievedReservation1 = allReservations.get(0);
        assertEquals(5, retrievedReservation1.getQuantite(), "La quantité devrait être 5.");
        assertEquals(50.0, retrievedReservation1.getPrix(), "Le prix devrait être 50.0.");
        assertEquals(10.0, retrievedReservation1.getPrixUnitaire(), "Le prix unitaire devrait être 10.0.");

        // Vérifier les données de la deuxième réservation
        Reservation retrievedReservation2 = allReservations.get(1);
        assertEquals(3, retrievedReservation2.getQuantite(), "La quantité devrait être 3.");
        assertEquals(30.0, retrievedReservation2.getPrix(), "Le prix devrait être 30.0.");
        assertEquals(10.0, retrievedReservation2.getPrixUnitaire(), "Le prix unitaire devrait être 10.0.");
    }

    @Test
    @Order(5)
    public void testSaveReservationWithoutClientShouldThrowException() {
        Reservation reservation = new Reservation();
        reservation.setDateCommande(LocalDate.now());
        reservation.setQuantite(10);
        reservation.setPrix(100.0);
        reservation.setPrixUnitaire(10.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationDao.save(reservation);
        });

        String expectedMessage = "La réservation doit avoir un client avec un ID non nul.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Le message d'exception devrait être correct.");
    }
}
