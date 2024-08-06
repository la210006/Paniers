package com.example.paniers.Util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Prix {
    private static final String PRIX_FILE = "src/main/resources/prix.json";
    private static Prix instance;
    private int prixPanier;

    private Prix() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Prix prix = mapper.readValue(new File(PRIX_FILE), Prix.class);
            this.prixPanier = prix.getPrixPanier();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Prix getInstance() {
        if (instance == null) {
            instance = new Prix();
        }
        return instance;
    }

    public int getPrixPanier() {
        return prixPanier;
    }

    public void setPrixPanier(int prixPanier) {
        this.prixPanier = prixPanier;
    }
}
