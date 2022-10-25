package integrador.modelo.conjuntoCarta;

import integrador.modelo.cartas.Carta;
import integrador.modelo.cartas.TipoCarta;

import java.util.ArrayList;
import java.util.List;

public class VerificarTrio extends VerificarJugada{

    public VerificarTrio() {
        this.forma = Formacion.TRIO;
        this.cantidadCartas = 3;
    }

    @Override
    public Jugada formarJugada(Mano mano) { //NO FUNCIONA, NO TIENE EN CUENTA JOKERS
        List<Carta> cartas = mano.getCartas();
        List<Carta> result = new ArrayList<>();
        boolean armado = false;
        int i = 0;
        while ((i < cartas.size()) && (!armado)) {
            TipoCarta tipoBuscado = cartas.get(i).getTipo();
            for (Carta cartaActual: cartas) {
                if ((cartaActual.getTipo() == tipoBuscado) && (result.size() < this.cantidadCartas)) {
                    result.add(cartaActual);
                }
            }
            if (result.size() == this.cantidadCartas) {
                armado = true;
            }
            else result = new ArrayList<>();
            i++;
        }

        if (armado) {
            return new Jugada(this.forma,result);
        }
        else return null;
    }
}
