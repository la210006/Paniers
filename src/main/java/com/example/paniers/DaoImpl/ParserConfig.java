package com.example.paniers.DaoImpl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ParserConfig {

    private static final String PRIX_JSON_FILE = "src/main/resources/Prix.json";

    public static Path getPrixJsonPath() {
        return Paths.get(PRIX_JSON_FILE);
    }
}
