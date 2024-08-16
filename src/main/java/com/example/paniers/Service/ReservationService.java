package com.example.paniers.Service;

import com.example.paniers.Dao.ClientDao;
import com.example.paniers.Dao.ReservationDao;
import com.example.paniers.Models.Client;
import com.example.paniers.Models.Reservation;
import com.example.paniers.Util.Prix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ClientDao clientDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao, ClientDao clientDao) {
        this.reservationDao = reservationDao;
        this.clientDao = clientDao;
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        for (Reservation reservation : reservations) {
            // Récupérez et assignez le client à chaque réservation
            if (reservation.getClient() != null && reservation.getClient().getId() != null) {
                Client client = clientDao.findById(reservation.getClient().getId());
                reservation.setClient(client);
            }
        }
        return reservations;
    }


    public void addReservation(Reservation reservation) {
        if (reservation.getClient() == null || reservation.getClient().getId() == null) {
            throw new IllegalArgumentException("La réservation doit avoir un client valide avec un ID non nul.");
        }

        Client client = clientDao.findById(reservation.getClient().getId());
        if (client == null) {
            throw new RuntimeException("Client non trouvé pour l'ID: " + reservation.getClient().getId());
        }

        // Lire le prix du fichier JSON
        Prix prix = Prix.lirePrixDuFichier("src/main/resources/Prix.json");
        if (prix != null) {
            double prixUnitaire = prix.getPrixPanier();
            reservation.setPrix(prixUnitaire);
            reservation.setPrix(prixUnitaire * reservation.getQuantite());
        }

        reservation.setClient(client);
        reservationDao.save(reservation);
    }

    public Reservation getReservationById(Long id) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation != null && reservation.getClient() != null) {
            Client client = clientDao.findById(reservation.getClient().getId());
            reservation.setClient(client);
        }
        return reservation;
    }

    public void deleteReservation(Long id) {
        reservationDao.delete(id);
    }
}
