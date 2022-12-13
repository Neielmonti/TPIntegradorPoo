package programa.modelo.verificadores;
import programa.modelo.commons.Formacion;
import programa.modelo.commons.PaloCarta;
import programa.modelo.conjuntoCarta.Carta;
import java.util.List;
public abstract class VerificarAscendienteMismoPalo extends VerificarAscendiente{
    PaloCarta palo;
    public VerificarAscendienteMismoPalo(Formacion forma) {
        super(forma);
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
    public boolean verificarListaCartas(List<Carta> cartas) {
        this.palo = buscarPalo(cartas);
        return super.verificarListaCartas(cartas);
    }
    @Override
    protected boolean esSiguiente(Carta c1, Carta siguiente) {
        return super.esSiguiente(c1, siguiente) && (siguiente.getPalo() == this.palo);
    }
}