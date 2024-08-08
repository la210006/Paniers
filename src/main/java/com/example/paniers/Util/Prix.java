package com.example.paniers.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Prix {
    private static final Logger logger = LoggerFactory.getLogger(Prix.class);
    private double prixPanier;

    public double getPrixPanier() {
        return prixPanier;
    }

    public void setPrixPanier(double prixPanier) {
        this.prixPanier = prixPanier;
    }

    public static Prix lirePrixDuFichier(String cheminFichier) {
        ObjectMapper mapper = new ObjectMapper();
        Prix prix = null;
        try {
            File fichier = new File(cheminFichier);
            logger.info("Chemin du fichier: " + fichier.getAbsolutePath());
            if (fichier.exists()) {
                logger.info("Le fichier existe: " + fichier.getAbsolutePath());
                prix = mapper.readValue(fichier, Prix.class);
                logger.info("Prix lu: " + prix.getPrixPanier());
            } else {
                logger.error("Le fichier n'existe pas: " + fichier.getAbsolutePath());
            }
        } catch (IOException e) {
            logger.error("Erreur lors de la lecture du fichier JSON", e);
        }
        return prix;
    }
}
