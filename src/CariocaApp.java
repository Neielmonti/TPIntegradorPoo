import integrador.modelo.Juego;
import integrador.modelo.commons.PaloCarta;
import integrador.modelo.commons.TipoCarta;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.conjuntoCarta.jugadas.Jugada;
import integrador.modelo.conjuntoCarta.Mano;
import integrador.modelo.conjuntoCarta.jugadas.JugadaEscala;
import integrador.modelo.conjuntoCarta.jugadas.JugadaEscaleraReal;
import integrador.modelo.verificadores.VerificarEscala;
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
        c = new Carta(PaloCarta.PICAS, TipoCarta.DOS);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.TRES);
        prueba.add(c);
        c = new Carta(PaloCarta.PICAS, TipoCarta.CUATRO);
        prueba.add(c);


        Mano mano = new Mano(prueba);
        System.out.println(mano.mostrarCartas());

        VerificarEscala ve = new VerificarEscala();
        JugadaEscala jugada1 = ve.formarJugada(prueba);

        if (jugada1 != null) {
            System.out.println("Hay escala >:)");
            System.out.println(jugada1.mostrarCartas() + "\n");

            if (jugada1.agregarCarta(new Carta(PaloCarta.PICAS,TipoCarta.SEIS))) {
                System.out.println("Carta agregada :) \n");
                System.out.println(jugada1.mostrarCartas() + "\n");
            }
            else System.out.println("No se pudo agregar :(");
        }
        else System.out.println("No hay escala :(");

    }
}
