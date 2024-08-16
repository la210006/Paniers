package com.example.paniers.Util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

public class Prix {
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
            Path fichier = Path.of(cheminFichier);
            prix = mapper.readValue(fichier.toFile(), Prix.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prix;
    }

}
