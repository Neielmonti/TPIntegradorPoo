package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import java.util.List;
public class JugadaEscala extends JugadaAscendienteMismoPalo{
    public JugadaEscala(List<Carta> cartas, Jugador jugador) {
        super(Formacion.ESCALA, cartas, jugador);
    }
}
