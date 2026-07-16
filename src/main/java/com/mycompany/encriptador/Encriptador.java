/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.encriptador;

import com.mycompany.encriptador.vista.VistaEncriptador;
import com.mycompany.encriptador.datos.ConexionBD;
import javax.swing.SwingUtilities;
/**
 *
 * @author Diego Calix
 */
public class Encriptador {
    public static void main(String[] args) {
        // 1. Inicializa la BD en segundo plano (crea tablas si no existen)
        ConexionBD.inicializarTablas();

        // 2. Ejecuta y visualiza la UI de manera segura en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new VistaEncriptador().setVisible(true);
        });
    }
}
