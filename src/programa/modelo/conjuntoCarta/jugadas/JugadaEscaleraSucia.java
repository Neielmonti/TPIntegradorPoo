package programa.modelo.conjuntoCarta.jugadas;

import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;

import java.util.List;

public class JugadaEscaleraSucia extends JugadaAscendiente{
    public JugadaEscaleraSucia(List<Carta> cartas) {
        super(Formacion.ESCALERA_SUCIA, cartas);
    }
}
