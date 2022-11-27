package cariocaApp;

import programa.controlador.Controlador;
import programa.modelo.Juego;
import programa.modelo.Jugador;
import programa.modelo.commons.PaloCarta;
import programa.modelo.commons.TipoCarta;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.verificadores.VerificarEscala;
import programa.vista.VistaConsolaSwing;

import java.util.ArrayList;
import java.util.List;

public class CariocaApp {

    public static void main(String[] args) {
        Juego juego = new Juego();
        VistaConsolaSwing v1 = new VistaConsolaSwing();
        Controlador c1 = new Controlador(juego, v1);
        VistaConsolaSwing v2 = new VistaConsolaSwing();
        Controlador c2 = new Controlador(juego, v2);
        //VistaConsolaSwing v3 = new VistaConsolaSwing();
        //Controlador c3 = new Controlador(juego, v3);
        //VistaConsolaSwing v4 = new VistaConsolaSwing();
        //Controlador c4 = new Controlador(juego, v4);
        v1.inicioGrafico();
        v2.inicioGrafico();
        //v3.inicioGrafico();
        //v4.inicioGrafico();


        /**
        List<Carta> cartas = new ArrayList<>();
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.J));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.Q));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.K));
        cartas.add(new Carta(PaloCarta.JOKER, TipoCarta.JOKER));

        VerificarEscala ve = new VerificarEscala();
        //ve.formarJugada(cartas,new Jugador("hola"));
        if ((ve.verificarListaCartas(cartas))){
            System.out.println("Formada");
        }
        else System.out.println("No formada");
        **/
    }
}
