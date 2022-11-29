package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.commons.TipoCarta;
import programa.modelo.conjuntoCarta.Carta;
import java.util.List;
public abstract class JugadaAscendiente extends Jugada{
    public JugadaAscendiente(Formacion forma, List<Carta> cartas, Jugador jugador) {
        super(forma, cartas, jugador);
    }
    protected boolean esSiguiente(Carta c1, Carta siguiente) {
        if (siguiente.getTipo() == c1.getTipo().getNext()) {
            return true;
        }
        else return false;
    }
    protected boolean esAnterior(Carta c1, Carta anterior) {
        if (anterior.getTipo() == c1.getTipo().getPrevius()) {
            return true;
        }
        else return false;
    }
    @Override
    public boolean agregarCarta(Carta carta, boolean alFinal) {
        List<Carta> cartas = getCartas();

        if (alFinal) {
            if ((this.esSiguiente(cartas.get(cartas.size()-1),carta)) ||
                    ((carta.getTipo() == TipoCarta.JOKER) && (cartas.get(cartas.size()-1).getTipo() != TipoCarta.JOKER))) {
                agregarCarta(carta);
                return true;
            }
        }
        else {
            if ((this.esAnterior(cartas.get(0),carta)) ||
                    ((carta.getTipo() == TipoCarta.JOKER) && (cartas.get(0).getTipo() != TipoCarta.JOKER))) {
                agregarCartaPrincipio(carta);
                return true;
            }
        }
        return false;
    }
}
