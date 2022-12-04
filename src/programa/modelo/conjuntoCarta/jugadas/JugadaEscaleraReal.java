package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import java.util.List;
public class JugadaEscaleraReal extends JugadaAscendienteMismoPalo{
    public JugadaEscaleraReal(List<Carta> cartas, Jugador jugador) {
        super(Formacion.ESCALERA_REAL, cartas, jugador);
    }
}