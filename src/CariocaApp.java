import integrador.modelo.Juego;
import integrador.modelo.commons.PaloCarta;
import integrador.modelo.commons.TipoCarta;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.conjuntoCarta.jugadas.Jugada;
import integrador.modelo.conjuntoCarta.Mano;
import integrador.modelo.verificadores.VerificarEscaleraReal;
import integrador.modelo.verificadores.VerificarJugada;

import java.util.ArrayList;
import java.util.List;

public class CariocaApp {

    public static void main(String[] args) {
        Juego juego = new Juego();

        List <Carta> prueba = new ArrayList<>();
        Carta c;
        c = new Carta(PaloCarta.PICAS, TipoCarta.A);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.SIETE);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.DOS);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.J);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.TRES);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.K);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.CINCO);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.SEIS);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.JOKER);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.NUEVE);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.DIEZ);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.CUATRO);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.Q);
        prueba.add(c);

        Mano mano = new Mano(prueba);
        System.out.println(mano.mostrarCartas());

        VerificarJugada ve = new VerificarEscaleraReal();
        Jugada jugada1 = ve.formarJugada(prueba);

        if (jugada1 != null) {
            System.out.println("Hay escalera >:)");
            System.out.println(jugada1.mostrarCartas() + "\n");
        }
        else System.out.println("No hay dos escalas :(");

    }
}
