package programa.modelo.verificadores;
import programa.modelo.commons.Formacion;
import programa.modelo.commons.TipoCarta;
import programa.modelo.conjuntoCarta.Carta;

import java.util.ArrayList;
import java.util.List;
public abstract class VerificarAscendiente extends VerificarJugada{
    public VerificarAscendiente(Formacion forma) {
        super(forma);
    }

    protected boolean esSiguiente(Carta c1, Carta siguiente) {
        if (siguiente.getTipo() == c1.getTipo().getNext()) {
            return true;
        }
        else return false;
    }

    protected boolean esSiguienteDoble(Carta c1, Carta siguiente) {
        if (siguiente.getTipo() == c1.getTipo().getNext().getNext()) {
            return true;
        }
        else return false;
    }
    protected boolean verificarListaCartas(List<Carta> cartas) {
        if (!super.verificarListaCartas(cartas)) {
            return false;
        }
        boolean armado = true;
        int i = 0;
        Carta ultimaCarta = cartas.get(0);
        while ((i < cartas.size()) && (armado)) {
            if (cartas.get(i + 1).getTipo() == TipoCarta.JOKER) {
                ultimaCarta = cartas.get(i);
            }

            if ((cartas.get(i) != ultimaCarta)){
                // INCOMPLETO <----------------------------------------------------
            }

            if (!esSiguiente(cartas.get(i), cartas.get(i + 1)) ) {
                armado = false;
            }
            i++;
        }
        return armado;
    }
}//END.
