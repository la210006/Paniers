package com.example.paniers.DaoImpl;

import com.example.paniers.Dao.ClientDao;
import com.example.paniers.Dao.ReservationDao;
import com.example.paniers.Models.Client;
import com.example.paniers.Models.Reservation;
import com.example.paniers.Util.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDaoImpl implements ReservationDao {

    @Autowired
    private ClientDao clientDao;

    // Ajout de la méthode setClientDao
    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Reservation findById(Long id) {
        Reservation reservation = null;
        String query = "SELECT * FROM reservations WHERE id = ?";

        try (Connection connection = Persistence.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reservation = new Reservation();
                reservation.setId(resultSet.getLong("id"));
                reservation.setDateCommande(resultSet.getDate("date_commande").toLocalDate());
                reservation.setQuantite(resultSet.getInt("quantite"));
                reservation.setPrix(resultSet.getDouble("prix"));
                reservation.setPrixUnitaire(resultSet.getDouble("prix_unitaire"));

                Long clientId = resultSet.getLong("client_id");
                if (clientId != 0) {  // Vérification que l'ID client est non nul
                    Client client = clientDao.findById(clientId);
                    reservation.setClient(client);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";

        try (Connection connection = Persistence.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getLong("id"));
                reservation.setDateCommande(resultSet.getDate("date_commande").toLocalDate());
                reservation.setQuantite(resultSet.getInt("quantite"));
                reservation.setPrix(resultSet.getDouble("prix"));
                reservation.setPrixUnitaire(resultSet.getDouble("prix_unitaire"));

                Long clientId = resultSet.getLong("client_id");
                if (clientId != 0) {
                    Client client = clientDao.findById(clientId);
                    reservation.setClient(client); // Associer le client à la réservation
                }
                reservations.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    @Override
    public void save(Reservation reservation) {
        if (reservation.getClient() == null || reservation.getClient().getId() == null) {
            throw new IllegalArgumentException("La réservation doit avoir un client avec un ID non nul.");
        }

        String query = "INSERT INTO reservations (date_commande, quantite, prix, prix_unitaire, client_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = Persistence.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setDate(1, Date.valueOf(reservation.getDateCommande()));
            statement.setInt(2, reservation.getQuantite());
            statement.setDouble(3, reservation.getPrix());
            statement.setDouble(4, reservation.getPrixUnitaire());
            statement.setLong(5, reservation.getClient().getId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                reservation.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Reservation reservation) {
        String query = "UPDATE reservations SET date_commande = ?, quantite = ?, prix = ?, prix_unitaire = ?, client_id = ? WHERE id = ?";

        try (Connection connection = Persistence.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, Date.valueOf(reservation.getDateCommande()));
            statement.setInt(2, reservation.getQuantite());
            statement.setDouble(3, reservation.getPrix());
            statement.setDouble(4, reservation.getPrixUnitaire());
            statement.setLong(5, reservation.getClient().getId());
            statement.setLong(6, reservation.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM reservations WHERE id = ?";

        try (Connection connection = Persistence.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
