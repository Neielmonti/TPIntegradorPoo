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
    public boolean verificarListaCartas(List<Carta> cartas) {
        if (!super.verificarListaCartas(cartas)) {
            return false;
        }
        boolean armado = true;
        int i = 1;
        Carta cartaAnterior = cartas.get(0);
        if (cartaAnterior.getTipo() == TipoCarta.JOKER) {
            cartaAnterior = cartas.get(1);
            i ++;
            if (cartaAnterior.getTipo() == TipoCarta.getMenorTipo()) {armado = false;}
        }
        while ((i < cartas.size()) && (armado)) {
            if (cartas.get(i).getTipo() == TipoCarta.JOKER) {
                if (i == cartas.size() - 1) {
                    if (cartas.get(i - 1).getTipo() == TipoCarta.getMayorTipo()) {
                        armado = false;
                    }
                }
                else {cartaAnterior = new Carta(cartaAnterior.getPalo(),cartaAnterior.getTipo().getNext());}
            }
            else {
                if (!esSiguiente(cartaAnterior,cartas.get(i))) {
                    armado = false;
                }
                else cartaAnterior = cartas.get(i);
            }
            i++;
        }
        return armado;
    }
}