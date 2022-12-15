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
        // Lo primero que se debe hacer es rescatar la primer carta de la lista
        if (cartaAnterior.getTipo() == TipoCarta.JOKER) {
            // en el caso de que esta sea un joker, se rescata la segunda
            cartaAnterior = cartas.get(1);
            i ++;
            // si la primer carta fue un joker, y la segunda es una carta con el menor tipo (en el caso de los naipes, "A")
            // se rechaza la lista inmediatamente
            if (cartaAnterior.getTipo() == TipoCarta.getMenorTipo()) {armado = false;}
        }
        while ((i < cartas.size()) && (armado)) {
            if (cartas.get(i).getTipo() == TipoCarta.JOKER) {
                if (i == cartas.size() - 1) {
                    // Si la actual es un joker, y es la ultima de la lista, se verifica que la anterior a esta no sea una
                    // carta con el mayor tipo (en el caso de los naipes, "K")
                    if (cartas.get(i - 1).getTipo() == TipoCarta.getMayorTipo()) {
                        armado = false;
                    }
                }
                // si no es la ultima carta, se crea una carta "falsa" para poder seguir con la verificacion
                // (esta carta toma el valor de la posicion del joker)
                else {cartaAnterior = new Carta(cartaAnterior.getPalo(),cartaAnterior.getTipo().getNext());}
            }
            else {
                // si la carta actual no es un joker, se verifica que sea siguiente de la carta anterior
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