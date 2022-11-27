package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.ConjuntoCartas;

import java.util.List;
public abstract class Jugada extends ConjuntoCartas {
    private Formacion forma;
    public Jugada(Formacion forma, List<Carta> cartas, Jugador jugador) {
        super(cartas);
        this.forma = forma;
        jugador.addJugada(this);
    }
    public abstract boolean agregarCarta(Carta carta, boolean alFinal);
    public Formacion getForma(){
        return this.forma;
    }
}
