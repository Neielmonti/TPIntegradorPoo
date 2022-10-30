package integrador.modelo.conjuntoCarta.jugadas;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Carta;

import java.util.List;

public class JugadaEscaleraSucia extends JugadaAscendiente{
    public JugadaEscaleraSucia(List<Carta> cartas) {
        super(Formacion.ESCALERA_SUCIA, cartas);
    }
}
