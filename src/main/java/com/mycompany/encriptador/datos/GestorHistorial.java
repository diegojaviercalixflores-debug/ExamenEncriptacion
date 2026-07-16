/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.encriptador.datos;
import com.mycompany.encriptador.datos.ConexionBD;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Diego Calix
 */
public class GestorHistorial {
    public static int guardarFraseYConfiguracion(String original, String encriptado, Map<Character, Character> mapaUsado) throws SQLException {
        String sqlFrase = "INSERT INTO Frases(texto_original, texto_encriptado) VALUES(?, ?)";
        String sqlConfig = "INSERT INTO ConfiguracionEncriptacion(frase_id, original, cifrado) VALUES(?, ?, ?)";
        
        int fraseId = -1;

        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false); // Transacción para garantizar integridad de datos

            try (PreparedStatement psFrase = conn.prepareStatement(sqlFrase, Statement.RETURN_GENERATED_KEYS)) {
                psFrase.setString(1, original);
                psFrase.setString(2, encriptado);
                psFrase.executeUpdate();

                try (ResultSet rs = psFrase.getGeneratedKeys()) {
                    if (rs.next()) {
                        fraseId = rs.getInt(1);
                    }
                }
            }

            try (PreparedStatement psConfig = conn.prepareStatement(sqlConfig)) {
                for (Map.Entry<Character, Character> entry : mapaUsado.entrySet()) {
                    psConfig.setInt(1, fraseId);
                    psConfig.setString(2, String.valueOf(entry.getKey()));
                    psConfig.setString(3, String.valueOf(entry.getValue()));
                    psConfig.addBatch();
                }
                psConfig.executeBatch();
            }

            conn.commit(); 
        } catch (SQLException e) {
            throw e;
        }
        return fraseId;
    }

    public static Map<Character, Character> obtenerMapaInversoPorFrase(int fraseId) throws SQLException {
        String sql = "SELECT original, cifrado FROM ConfiguracionEncriptacion WHERE frase_id = ?";
        Map<Character, Character> mapaInverso = new HashMap<>();

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fraseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    char original = rs.getString("original").charAt(0);
                    char cifrado = rs.getString("cifrado").charAt(0);
                    mapaInverso.put(cifrado, original);
                }
            }
        }
        return mapaInverso;
    }

    public static String obtenerTextoEncriptado(int fraseId) throws SQLException {
        String sql = "SELECT texto_encriptado FROM Frases WHERE id = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fraseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("texto_encriptado");
                }
            }
        }
        return null;
    }
    
    public static java.util.List<Object[]> obtenerHistorialCompleto() throws SQLException {
    java.util.List<Object[]> historial = new java.util.ArrayList<>();
    
    // Asegúrate de que los nombres de la tabla y columnas coincidan exactamente con tu BD
    // En este caso asumimos que tu tabla se llama "Frases" con columnas: id, texto_original, texto_encriptado
    String sql = "SELECT id, texto_original, texto_encriptado FROM Frases ORDER BY id ASC";
    
    try (java.sql.Connection conn = ConexionBD.conectar(); // Ajusta al método de conexión que uses
         java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
         java.sql.ResultSet rs = pstmt.executeQuery()) {
        
        while (rs.next()) {
            int id = rs.getInt("id");
            String original = rs.getString("texto_original");
            String encriptado = rs.getString("texto_encriptado");
            
            // Creamos una fila con los 3 datos correspondientes a las columnas del JTable
            historial.add(new Object[]{id, original, encriptado});
        }
    }
    return historial;
}
}
