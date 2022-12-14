package programa.modelo.verificadores;
import programa.modelo.jugador.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.jugadas.JugadaEscala;
import java.util.List;
public class VerificarEscala extends VerificarAscendienteMismoPalo{
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
}