package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.ConjuntoCartas;
import programa.vista.IJugada;

import java.util.List;
public abstract class Jugada extends ConjuntoCartas implements IJugada {
    private Formacion forma;
    private Jugador jugador;
    public Jugada(Formacion forma, List<Carta> cartas, Jugador jugador) {
        super(cartas);
        this.forma = forma;
        this.jugador = jugador;
        jugador.addJugada(this);
    }
    public abstract boolean agregarCarta(Carta carta, boolean alFinal);
    public Formacion getForma(){
        return this.forma;
    }
    public String getNombreJugador() {
        return this.jugador.getNombre();
    }
}
