/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.encriptador.logica;

import java.util.Map;
/**
 *
 * @author Diego Calix
 */
public class Encriptador {
    public static String encriptar(String texto, Map<Character, Character> mapa) {
        if (texto == null) return "";
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            resultado.append(mapa.getOrDefault(c, c));
        }
        return resultado.toString();
    }

    public static String desencriptar(String textoCifrado, Map<Character, Character> mapaInverso) {
        if (textoCifrado == null) return "";
        StringBuilder resultado = new StringBuilder();
        for (char c : textoCifrado.toCharArray()) {
            resultado.append(mapaInverso.getOrDefault(c, c));
        }
        return resultado.toString();
    }
}
