/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.encriptador.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Diego Calix
 */
public class ConfiguracionManager {
    private Map<Character, Character> mapaEncriptar = new HashMap<>();
    private Map<Character, Character> mapaDesencriptar = new HashMap<>();

    public void cargarDesdeArchivo(String rutaArchivo) throws IOException, IllegalArgumentException {
        mapaEncriptar.clear();
        mapaDesencriptar.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numLinea = 0;
            while ((linea = br.readLine()) != null) {
                numLinea++;
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(",");
                if (partes.length != 2 || partes[0].length() != 1 || partes[1].length() != 1) {
                    throw new IllegalArgumentException("Error de formato en línea " + numLinea + ": '" + linea + "'. Debe ser x,y");
                }

                char original = partes[0].charAt(0);
                char cifrado = partes[1].charAt(0);

                if (mapaEncriptar.containsKey(original)) {
                    throw new IllegalArgumentException("Carácter original duplicado en línea " + numLinea + ": " + original);
                }

                mapaEncriptar.put(original, cifrado);
                mapaDesencriptar.put(cifrado, original);
            }
        }
    }

    public Map<Character, Character> getMapaEncriptar() {
        return mapaEncriptar;
    }

    public Map<Character, Character> getMapaDesencriptar() {
        return mapaDesencriptar;
    }
}
