package integrador.modelo.verificadores;

import integrador.modelo.commons.TipoCarta;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Jugada;
import integrador.modelo.conjuntoCarta.Mano;

import java.util.ArrayList;
import java.util.List;

public abstract class VerificarAscendiente extends VerificarJugada{

    private int buscarSiguiente(List<Carta> cartas, Carta carta) {
        Boolean found = false;
        int i = 0;
        int salida = -1;
        if (carta.getTipo().getNext() != TipoCarta.JOKER) {
            while ((i < cartas.size()) && (!found)) {
                Carta cartaActual = cartas.get(i);
                if ((cartaActual.getTipo().ordinal() == carta.getTipo().ordinal() + 1) && (cartaActual.getPalo() == carta.getPalo())) {
                    found = true;
                    salida = i;
                }
                i++;
            }
        }
        return salida;
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

    @Override
    public Jugada formarJugada(Mano mano) {
        List<Carta> cartas = mano.getCartas();
        boolean armado = false;
        List<Carta> result = new ArrayList<>();
        int i = 0;

        while ((i < cartas.size()) && (!armado)) {

            Carta cartaActual = cartas.get(i);
            result = new ArrayList<>();
            result.add(cartaActual);
            boolean jokerUsado = false;

            int iSiguiente = buscarSiguiente(cartas, cartaActual);

            if ((iSiguiente == -1) && (cartaActual.getTipo().getNext() != null) && (!jokerUsado)) {
                iSiguiente = buscarJoker(cartas);
                cartaActual = new Carta(cartaActual.getPalo(),cartaActual.getTipo().getNext());
                jokerUsado = true;
            }

            while ((iSiguiente != -1) && (result.size() < this.cantidadCartas)) {
                cartaActual = cartas.get(iSiguiente);
                result.add(cartaActual);
                iSiguiente = buscarSiguiente(cartas, cartaActual);
                if ((iSiguiente == -1) && (cartaActual.getTipo().getNext() != null) && (!jokerUsado)) {
                    iSiguiente = buscarJoker(cartas);
                    cartaActual = new Carta(cartaActual.getPalo(),cartaActual.getTipo().getNext());
                    jokerUsado = true;
                }
            }

            if (result.size() == this.cantidadCartas) {
                armado = true;
            }
            i++;
        }
        if (armado) {
            mano.quitarCartas(result);
            return new Jugada(this.forma,result);
        }
        else return null;
    }



}//END.
