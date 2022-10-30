package integrador.modelo.conjuntoCarta.jugadas;

import integrador.modelo.commons.Formacion;
import integrador.modelo.commons.TipoCarta;
import integrador.modelo.conjuntoCarta.Carta;

import java.util.List;

public class JugadaEscala extends JugadaAscendiente{
    public JugadaEscala(List<Carta> cartas) {
        super(Formacion.ESCALA, cartas);
    }

    @Override
    public boolean agregarCarta(Carta carta) {
        List<Carta> cartas = this.getCartas();
        if (cartas.size() == 0) {
            return false;
        }
        else {
            if (cartas.get(0).getTipo() == carta.getTipo()) {
                return super.agregarCarta(carta);
            }
            else return false;
        }
    }

}
