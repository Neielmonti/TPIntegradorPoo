package integrador.modelo.conjuntoCarta.jugadas;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Carta;

import java.util.List;

public class JugadaTrio extends Jugada{
    public JugadaTrio(List<Carta> cartas) {
        super(Formacion.TRIO, cartas);
    }

    public boolean agregarCarta(Carta carta) {
        boolean salida = false;
        List<Carta> cartas = this.getCartas();
        if (!cartas.isEmpty()) {
            if (cartas.get(cartas.size() - 1).getTipo() == carta.getTipo()) {
                this.agregarCarta(carta);
                salida = true;
            }
        }
        return salida;
    }
}
