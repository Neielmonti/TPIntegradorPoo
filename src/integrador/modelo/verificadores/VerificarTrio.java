package integrador.modelo.verificadores;
import integrador.modelo.commons.TipoCarta;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.jugadas.Jugada;
import integrador.modelo.conjuntoCarta.jugadas.JugadaTrio;

import java.util.ArrayList;
import java.util.List;

public class VerificarTrio extends VerificarJugada{

    public VerificarTrio() {
        this.forma = Formacion.TRIO;
        this.cantidadCartas = 3;
    }

    @Override
    public Jugada formarJugada(List<Carta> cartas) {

        List<Carta> result = new ArrayList<>();
        boolean armado = false;
        boolean jokerUsado = false;
        int i = 0;
        if (cartas.size() != this.cantidadCartas) {
            return null;
        } else {
            while ((i < cartas.size()) && (!armado)) {

                TipoCarta tipoBuscado = cartas.get(i).getTipo();

                for (Carta cartaActual : cartas) {

                    if (result.size() < this.cantidadCartas) {

                        if (cartaActual.getTipo() == tipoBuscado) {
                            result.add(cartaActual);
                        } else if ((cartaActual.getTipo() == TipoCarta.JOKER) && (!jokerUsado)) {
                            jokerUsado = true;
                            result.add(cartaActual);
                        }
                    }

                }
                if (result.size() == this.cantidadCartas) {
                    armado = true;
                } else result = new ArrayList<>();
                i++;
            }

            if (armado) {
                return new JugadaTrio(result);
            } else return null;
        }
    }

}
