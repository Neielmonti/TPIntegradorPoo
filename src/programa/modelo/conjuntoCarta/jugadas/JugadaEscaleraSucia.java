package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import java.util.List;
public class JugadaEscaleraSucia extends JugadaAscendiente{
    public JugadaEscaleraSucia(List<Carta> cartas, Jugador jugador) {
        super(Formacion.ESCALERA_SUCIA, cartas, jugador);
    }
}