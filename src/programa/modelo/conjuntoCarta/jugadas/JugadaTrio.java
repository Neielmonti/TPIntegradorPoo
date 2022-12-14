package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.jugador.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.commons.TipoCarta;
import programa.modelo.conjuntoCarta.Carta;
import java.util.List;
public class JugadaTrio extends Jugada{
    private TipoCarta tipo;
    public JugadaTrio(List<Carta> cartas, Jugador jugador) {
        super(Formacion.TRIO, cartas, jugador);
        this.tipo = buscarTipo(cartas);
    }
    private TipoCarta buscarTipo(List<Carta> cartas) {
        int i = 0;
        TipoCarta result = null;
        while ((i < cartas.size()) && (result == null)) {
            if (cartas.get(i).getTipo() != TipoCarta.JOKER) {
                result = cartas.get(i).getTipo();
            }
            i ++;
        }
        return result;
    }
    @Override
    public boolean agregarCarta(Carta carta, boolean alFinal) {
        List<Carta> cartas = getCartas();
        if (alFinal) {
            if ((this.tipo == carta.getTipo()) ||
                    ((carta.getTipo() == TipoCarta.JOKER) && (cartas.get(cartas.size() - 1).getTipo() != TipoCarta.JOKER))) {
                agregarCarta(carta);
                return true;
            }
        }
        else {
            if ((this.tipo == carta.getTipo()) ||
                    ((carta.getTipo() == TipoCarta.JOKER) && (cartas.get(0).getTipo() != TipoCarta.JOKER))) {
                agregarCartaPrincipio(carta);
                return true;
            }
        }
        return false;
    }
}