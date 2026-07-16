/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.encriptador.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Diego Calix
 */
public class ConexionBD {
    private static final String URL = "jdbc:sqlite:encriptador.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void inicializarTablas() {
        String tablaFrases = "CREATE TABLE IF NOT EXISTS Frases (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "texto_original TEXT NOT NULL," +
                "texto_encriptado TEXT NOT NULL," +
                "fecha DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";

        String tablaConfig = "CREATE TABLE IF NOT EXISTS ConfiguracionEncriptacion (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "frase_id INTEGER," +
                "original CHAR(1) NOT NULL," +
                "cifrado CHAR(1) NOT NULL," +
                "FOREIGN KEY(frase_id) REFERENCES Frases(id) ON DELETE CASCADE" +
                ");";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(tablaFrases);
            stmt.execute(tablaConfig);
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }
}
