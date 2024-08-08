package com.example.paniers.Dao;

import com.example.paniers.Util.Prix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DaoFactory {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private ResourceLoader resourceLoader;

    private static final String CHEMIN_FICHIER_PRIX = "classpath:prix.json";

    public ClientDao getClientDao() {
        return clientDao;
    }

    public ReservationDao getReservationDao() {
        return reservationDao;
    }

    public double getPrixUnitaire() {
        Resource resource = resourceLoader.getResource(CHEMIN_FICHIER_PRIX);
        try {
            Prix prix = Prix.lirePrixDuFichier(resource.getFile().getAbsolutePath());
            if (prix != null) {
                return prix.getPrixPanier();
            } else {
                throw new RuntimeException("Impossible de lire le prix depuis le fichier JSON.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'accès au fichier JSON.", e);
        }
    }
}
