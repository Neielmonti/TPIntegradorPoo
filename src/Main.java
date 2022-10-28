import integrador.modelo.Juego;
import integrador.modelo.commons.PaloCarta;
import integrador.modelo.commons.TipoCarta;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.conjuntoCarta.Jugada;
import integrador.modelo.conjuntoCarta.Mano;
import integrador.modelo.verificadores.VerificarAscendiente;
import integrador.modelo.verificadores.VerificarEscala;
import integrador.modelo.verificadores.VerificarEscaleraReal;

import java.util.ArrayList;
import java.util.List;

public class Main {

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

        VerificarAscendiente ve = new VerificarEscala();
        Jugada jugada1 = ve.formarJugada(mano);

        if (jugada1 != null) {
            System.out.println("Hay escala >:)");
        }
        else System.out.println("No hay escala :(");

    }
}
