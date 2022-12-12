package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.commons.PaloCarta;
import programa.modelo.conjuntoCarta.Carta;
import java.util.List;
public abstract class JugadaAscendienteMismoPalo extends JugadaAscendiente{
    private PaloCarta palo;
    public JugadaAscendienteMismoPalo(Formacion forma,List<Carta> cartas, Jugador jugador) {
        super(forma, cartas, jugador);
        this.palo = buscarPalo(cartas);
    }
    private PaloCarta buscarPalo(List<Carta> cartas) {
        int i = 0;
        PaloCarta result = null;
        while (i < cartas.size()) {
            if (cartas.get(i).getPalo() != PaloCarta.JOKER) {
                result = cartas.get(i).getPalo();
                break;
            }
            i ++;
        }
        return result;
    }
    @Override
    protected boolean esSiguiente(Carta c1, Carta siguiente) {
        return ((super.esSiguiente(c1, siguiente)) && (this.palo == siguiente.getPalo()));
    }
    @Override
    protected boolean esAnterior(Carta c1, Carta anterior) {
        return ((super.esAnterior(c1, anterior)) && (this.palo == anterior.getPalo()));
    }
}