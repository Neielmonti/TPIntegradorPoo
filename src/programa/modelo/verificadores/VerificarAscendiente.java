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

    protected int buscarSiguiente(List<Carta> cartas, Carta carta) {
        Boolean found = false;
        int i = 0;
        int salida = -1;
        while ((i < cartas.size()) && (!found)) {
            Carta cartaActual = cartas.get(i);
            if ((cartaActual.getTipo().ordinal() == carta.getTipo().ordinal() + 1) && (cartaActual.getPalo() == carta.getPalo())) {
                found = true;
                salida = i;
            }
            i++;
        }
        return salida;
    }

    private int cantJokers(List<Carta> cartas) {
        int contador = 0;
        for (Carta carta:cartas) {
            if (carta.getTipo() == TipoCarta.JOKER) {
                contador ++;
            }
        }
        return contador;
    }

    private int buscarJoker(List<Carta> cartas) {
        Boolean found = false;
        int i = 0;
        int salida = -1;
        while ((i < cartas.size()) && (!found)) {
            Carta cartaActual = cartas.get(i);
            if (cartaActual.getTipo() == TipoCarta.JOKER) {
                found = true;
                salida = i;
            }
            i++;
        }
        return salida;
    }

    protected List<Carta> verificarListaCartas(List<Carta> cartas) {
        boolean armado = false;
        List<Carta> result = new ArrayList<>();
        int i = 0;

        if ((cartas.size() == this.getCantCartas()) && (cantJokers(cartas) < 2)) {
            while ((i < cartas.size()) && (!armado)) {

                Carta cartaActual = cartas.get(i);
                result = new ArrayList<>();
                result.add(cartaActual);
                boolean jokerUsado = false;

                int iSiguiente = buscarSiguiente(cartas, cartaActual);

                if ((iSiguiente == -1) && (cartaActual.getTipo().getNext() != null) && (!jokerUsado)) {
                    iSiguiente = buscarJoker(cartas);
                    if (iSiguiente != -1) {
                        result.add(cartas.get(iSiguiente));
                        cartaActual = new Carta(cartaActual.getPalo(), cartaActual.getTipo().getNext());
                        iSiguiente = buscarSiguiente(cartas, cartaActual);
                        jokerUsado = true;
                    }
                }

                while ((iSiguiente != -1) && (result.size() < this.getCantCartas())) {
                    cartaActual = cartas.get(iSiguiente);
                    result.add(cartaActual);
                    iSiguiente = buscarSiguiente(cartas, cartaActual);
                    if ((iSiguiente == -1) && (cartaActual.getTipo().getNext() != null) && (!jokerUsado)) {
                        iSiguiente = buscarJoker(cartas);
                        if (iSiguiente != -1) {
                            result.add(cartas.get(iSiguiente));
                            cartaActual = new Carta(cartaActual.getPalo(), cartaActual.getTipo().getNext());
                            iSiguiente = buscarSiguiente(cartas, cartaActual);
                            jokerUsado = true;
                        }
                    }
                }

                if (result.size() == this.getCantCartas()) {
                    armado = true;
                }
                i++;
            }
        }
        if (armado) {
            return result;
        }
        else return null;
    }



}//END.
