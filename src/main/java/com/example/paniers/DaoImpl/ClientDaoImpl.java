package com.example.paniers.DaoImpl;

import com.example.paniers.Dao.ClientDao;
import com.example.paniers.Models.Client;
import com.example.paniers.Models.Reservation;
import com.example.paniers.Util.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDao {

    private static final Logger logger = LoggerFactory.getLogger(ClientDaoImpl.class);

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";

        try (Connection conn = Persistence.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setPrenom(rs.getString("prenom"));
                client.setNom(rs.getString("nom"));
                client.setCommune(rs.getString("commune"));


                List<Reservation> reservations = loadReservations(client.getId(), conn);
                client.setReservations(reservations);

                clients.add(client);
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de l'exécution de la requête 'findAll': ", e);
        }

        return clients;
    }

    @Override
    public Client findById(Long id) {
        Client client = null;
        String sql = "SELECT * FROM clients WHERE id = ?";

        try (Connection conn = Persistence.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    client = new Client();
                    client.setId(rs.getLong("id"));
                    client.setPrenom(rs.getString("prenom"));
                    client.setNom(rs.getString("nom"));
                    client.setCommune(rs.getString("commune"));

                    // Load Reservations for the client
                    List<Reservation> reservations = loadReservations(client.getId(), conn);
                    client.setReservations(reservations);
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de l'exécution de la requête 'findById' pour l'ID: {}", id, e);
        }

        return client;
    }

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO clients (prenom, nom, commune) VALUES (?, ?, ?)";

        try (Connection conn = Persistence.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, client.getPrenom());
            pstmt.setString(2, client.getNom());
            pstmt.setString(3, client.getCommune());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Erreur lors de l'exécution de la requête 'save' pour le client: {}", client, e);
        }
    }

    @Override
    public void update(Client client) {
        String sql = "UPDATE clients SET prenom = ?, nom = ?, commune = ? WHERE id = ?";

        try (Connection conn = Persistence.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, client.getPrenom());
            pstmt.setString(2, client.getNom());
            pstmt.setString(3, client.getCommune());
            pstmt.setLong(4, client.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Erreur lors de l'exécution de la requête 'update' pour le client: {}", client, e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM clients WHERE id = ?";

        try (Connection conn = Persistence.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Erreur lors de l'exécution de la requête 'delete' pour l'ID: {}", id, e);
        }
    }

    private List<Reservation> loadReservations(Long clientId, Connection conn) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE client_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(rs.getLong("id"));
                    reservation.setDateCommande(rs.getDate("date_commande").toLocalDate());
                    reservation.setQuantite(rs.getInt("quantite"));
                    reservation.setPrix(rs.getDouble("prix"));
                    reservation.setPrix(rs.getDouble("prix_unitaire"));
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur lors du chargement des réservations pour le client: {}", clientId, e);
        }
        return reservations;
    }
}
