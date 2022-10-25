import integrador.modelo.Juego;
import integrador.modelo.cartas.Carta;
import integrador.modelo.cartas.PaloCarta;
import integrador.modelo.cartas.TipoCarta;
import integrador.modelo.conjuntoCarta.Jugada;
import integrador.modelo.conjuntoCarta.Mano;
import integrador.modelo.conjuntoCarta.VerificarEscala;
import integrador.modelo.conjuntoCarta.VerificarTrio;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Juego juego = new Juego();

        List <Carta> prueba = new ArrayList<>();

        Mano mano = new Mano(prueba);
        System.out.println(mano.MostrarCartas());

        VerificarEscala ve = new VerificarEscala();
        VerificarTrio vt = new VerificarTrio();
        Jugada jugada2 = ve.formarJugada(mano);
        Jugada jugada1 = vt.formarJugada(mano);

        if (jugada1 != null) {
            System.out.println("Hay trio >:)");
        }

        if (jugada2 != null) {
            System.out.println("Hay escala");
        }
    }
}
