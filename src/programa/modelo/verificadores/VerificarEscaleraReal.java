package programa.modelo.verificadores;
import programa.modelo.jugador.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.jugadas.JugadaEscaleraReal;
import java.util.List;
public class VerificarEscaleraReal extends VerificarAscendienteMismoPalo{
    public VerificarEscaleraReal() {
        super(Formacion.ESCALERA_REAL);
    }
    @Override
    public JugadaEscaleraReal formarJugada(List<Carta> cartas, Jugador jugador) {
        if (verificarListaCartas(cartas)) {
            return new JugadaEscaleraReal(cartas, jugador);
        }
        else return null;
    }
}