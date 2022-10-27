package integrador.modelo.verificadores;

import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Jugada;
import integrador.modelo.conjuntoCarta.Mano;

import java.util.ArrayList;
import java.util.List;

public class VerificarEscala extends VerificarJugada{

    public VerificarEscala() {
        this.forma = Formacion.ESCALA;
        this.cantidadCartas = 4;
    }

    @Override
    public Jugada formarJugada(Mano mano) { //NO FUNCIONA, NO TIENE EN CUENTA JOKERS
        List<Carta> cartas = mano.getCartas();
        boolean armado = false;
        List<Carta> result = new ArrayList<>();
        int i = 0;
        while ((i < cartas.size()) && (!armado)) {
            Carta cartaActual = cartas.get(i);
            int x = 0;
            result = new ArrayList<>();
            while ((x < cartas.size()) && (result.size() < 4) && !armado) {
                Carta cartaAux = cartas.get(x);

                if (((cartaAux.getTipo().ordinal()) == cartaActual.getTipo().ordinal() + 1)
                        && (cartaAux.getPalo() == cartaActual.getPalo())) {
                    result.add(cartaAux);
                    cartaActual = cartaAux;
                }

                if (result.size() == 4) {
                    armado = true;
                }

                x++;
            }
            i++;
        }
        if (armado) {
            return new Jugada(this.forma,result);
        }
        else return null;
    }



}//END.
