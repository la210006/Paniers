package com.example.paniers.Service;

import com.example.paniers.Dao.ReservationDao;
import com.example.paniers.Models.Reservation;
import com.example.paniers.Util.Prix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationDao reservationDao;

    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }

    public void addReservation(Reservation reservation) {
        // Lire le prix du fichier JSON
        Prix prix = Prix.lirePrixDuFichier("Prix.json");
        if (prix != null) {
            double prixUnitaire = prix.getPrixPanier();
            reservation.setPrixUnitaire(prixUnitaire);
            reservation.setPrix(prixUnitaire * reservation.getQuantite());
        }
        reservationDao.save(reservation);
    }

    public Reservation getReservationById(Long id) {
        return reservationDao.findById(id).orElse(null);
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
