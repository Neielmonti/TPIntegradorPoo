package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import java.util.List;
public class JugadaEscala extends JugadaAscendiente{
    public JugadaEscala(List<Carta> cartas, Jugador jugador) {
        super(Formacion.ESCALA, cartas, jugador);
    }
    @Override
    protected boolean esSiguiente(Carta c1, Carta siguiente) {
        if (super.esSiguiente(c1, siguiente) && c1.getPalo() == siguiente.getPalo()) {
            return true;
        }
        else return false;
    }
    @Override
    protected boolean esAnterior(Carta c1, Carta anterior) {
        if (super.esAnterior(c1, anterior) && c1.getPalo() == anterior.getPalo()) {
            return true;
        }
        else return false;
    }
}
