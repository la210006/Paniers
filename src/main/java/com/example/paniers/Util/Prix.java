package com.example.paniers.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Prix {
    private static final Logger logger = LoggerFactory.getLogger(Prix.class);
    private double prixPanier;

    public double getPrixPanier() {
        return prixPanier;
    }

    public void setPrixPanier(double prixPanier) {
        this.prixPanier = prixPanier;
    }

    public static Prix lirePrixDuFichier(String nomFichier) {
        ObjectMapper mapper = new ObjectMapper();
        Prix prix = null;
        try {
            // Charger le fichier depuis le classpath
            InputStream inputStream = Prix.class.getClassLoader().getResourceAsStream(nomFichier);
            if (inputStream != null) {
                prix = mapper.readValue(inputStream, Prix.class);
                logger.info("Prix lu: " + prix.getPrixPanier());
            } else {
                logger.error("Le fichier n'a pas été trouvé dans le classpath: " + nomFichier);
            }
        } catch (IOException e) {
            logger.error("Erreur lors de la lecture du fichier JSON", e);
        }
        return prix;
    }
}

