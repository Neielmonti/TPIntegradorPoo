package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.commons.TipoCarta;
import programa.modelo.conjuntoCarta.Carta;
import java.util.List;
public class JugadaTrio extends Jugada{
    public JugadaTrio(List<Carta> cartas, Jugador jugador) {
        super(Formacion.TRIO, cartas, jugador);
    }
    @Override
    public boolean agregarCarta(Carta carta, boolean alFinal) {
        List<Carta> cartas = getCartas();
        if (alFinal) {
            if ((cartas.get(cartas.size() - 1).getTipo() == carta.getTipo()) ||
                    ((carta.getTipo() == TipoCarta.JOKER) && (cartas.get(cartas.size() - 1).getTipo() != TipoCarta.JOKER))) {
                agregarCarta(carta);
                return true;
            }
        }
        else {
            if ((cartas.get(0).getTipo() == carta.getTipo()) ||
                    ((carta.getTipo() == TipoCarta.JOKER) && (cartas.get(0).getTipo() != TipoCarta.JOKER))) {
                agregarCartaPrincipio(carta);
                return true;
            }
        }
        return false;
    }

}
