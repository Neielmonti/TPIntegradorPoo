package programa.modelo.verificadores;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.jugadas.JugadaEscaleraSucia;
import java.util.List;
public class VerificarEscaleraSucia extends VerificarAscendiente{
    public VerificarEscaleraSucia() {
        super(Formacion.ESCALERA_REAL);
    }
    @Override
    public JugadaEscaleraSucia formarJugada(List<Carta> cartas, Jugador jugador) {
        if (verificarListaCartas(cartas)) {
            return new JugadaEscaleraSucia(cartas,jugador);
        }
        else return null;
    }
}