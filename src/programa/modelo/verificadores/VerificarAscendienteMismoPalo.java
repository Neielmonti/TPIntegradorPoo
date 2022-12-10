package programa.modelo.verificadores;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
public abstract class VerificarAscendienteMismoPalo extends VerificarAscendiente{
    public VerificarAscendienteMismoPalo(Formacion forma) {
        super(forma);
    }
    @Override
    protected boolean esSiguiente(Carta c1, Carta siguiente) {
        return super.esSiguiente(c1, siguiente) && (siguiente.getPalo() == c1.getPalo());
    }
}