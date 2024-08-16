package com.example.paniers.Dao;

import com.example.paniers.Util.Prix;

public class DaoFactory {

    private final ClientDao clientDao;
    private final ReservationDao reservationDao;
    private final String cheminFichierPrix;

    public DaoFactory(ClientDao clientDao, ReservationDao reservationDao, String cheminFichierPrix) {
        this.clientDao = clientDao;
        this.reservationDao = reservationDao;
        this.cheminFichierPrix = cheminFichierPrix;
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    public ReservationDao getReservationDao() {
        return reservationDao;
    }

    public double getPrixUnitaire() {
        Prix prix = Prix.lirePrixDuFichier(cheminFichierPrix);
        if (prix != null) {
            return prix.getPrixPanier();
        } else {
            throw new RuntimeException("Impossible de lire le prix depuis le fichier JSON.");
        }
    }
}
