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
        ConexionBD.inicializarTablas();
        SwingUtilities.invokeLater(() -> {
            new VistaEncriptador().setVisible(true);
        });
    }
}
