package integrador.modelo.verificadores;
import integrador.modelo.commons.TipoCarta;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Jugada;
import integrador.modelo.conjuntoCarta.Mano;

import java.util.ArrayList;
import java.util.List;

public class VerificarTrio extends VerificarJugada{

    public VerificarTrio() {
        this.forma = Formacion.TRIO;
        this.cantidadCartas = 3;
    }

    @Override
    public Jugada formarJugada(Mano mano) {

        List<Carta> cartas = mano.getCartas();
        List<Carta> result = new ArrayList<>();
        boolean armado = false;
        boolean jokerUsado = false;
        int i = 0;

        while ((i < cartas.size()) && (!armado)) {

            TipoCarta tipoBuscado = cartas.get(i).getTipo();

            for (Carta cartaActual: cartas) {

                if (result.size() < this.cantidadCartas) {

                    if (cartaActual.getTipo() == tipoBuscado) {
                        result.add(cartaActual);
                    }
                    else if ((cartaActual.getTipo() == TipoCarta.JOKER) && (!jokerUsado)) {
                        jokerUsado = true;
                        result.add(cartaActual);
                    }
                }

            }
            if (result.size() == this.cantidadCartas) {
                armado = true;
            }
            else result = new ArrayList<>();
            i++;
        }

        if (armado) {
            mano.quitarCartas(result);
            return new Jugada(this.forma,result);
        }
        else return null;
    }
}
