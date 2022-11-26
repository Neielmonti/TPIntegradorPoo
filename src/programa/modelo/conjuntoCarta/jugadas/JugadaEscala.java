package programa.modelo.conjuntoCarta.jugadas;

import programa.modelo.commons.Formacion;
import programa.modelo.commons.TipoCarta;
import programa.modelo.conjuntoCarta.Carta;

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
            if ((cartas.get(0).getPalo() == carta.getPalo()) || (carta.getTipo() == TipoCarta.JOKER)) {
                return super.agregarCarta(carta);
            }
            else return false;
        }
    }

}
