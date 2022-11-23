package cariocaApp;

import integrador.controlador.Controlador;
import integrador.modelo.Juego;
import integrador.vista.VistaConsola;
import integrador.vista.VistaConsolaSwing;

import javax.swing.*;

public class CariocaApp {

    public static void main(String[] args) {
        Juego juego = new Juego();
        VistaConsolaSwing vista = new VistaConsolaSwing();
        Controlador controlador = new Controlador(juego, vista);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                vista.setSize(300,300);
                vista.setVisible(true);
            }
        });
    }
}
