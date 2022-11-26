package programa.modelo.verificadores;

import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.jugadas.JugadaEscaleraReal;

import java.util.List;

public class VerificarEscaleraReal extends VerificarAscendiente{
    public VerificarEscaleraReal() {
        super(Formacion.ESCALERA_REAL);
    }

    @Override
    public JugadaEscaleraReal formarJugada(List<Carta> cartas) {
        List<Carta> result = this.verificarListaCartas(cartas);
        if (result != null) {
            return new JugadaEscaleraReal(result);
        }
        else return null;
    }
}
