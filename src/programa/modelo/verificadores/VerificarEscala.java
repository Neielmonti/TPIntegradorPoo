package programa.modelo.verificadores;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.jugadas.JugadaEscala;
import java.util.List;
public class VerificarEscala extends VerificarAscendiente{
    public VerificarEscala() {
        super(Formacion.ESCALA);
    }
    @Override
    public JugadaEscala formarJugada(List<Carta> cartas, Jugador jugador) {
        if (verificarListaCartas(cartas)) {
            return new JugadaEscala(cartas, jugador);
        }
        return null;
    }
    @Override
    protected boolean esSiguiente(Carta c1, Carta siguiente) {
        if (super.esSiguiente(c1,siguiente) && (siguiente.getPalo() == c1.getPalo())) {
            return true;
        }
        else return false;
    }
}