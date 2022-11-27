package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import java.util.List;
public class JugadaEscaleraReal extends JugadaAscendiente{
    public JugadaEscaleraReal(List<Carta> cartas, Jugador jugador) {
        super(Formacion.ESCALERA_REAL, cartas, jugador);
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
