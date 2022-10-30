package integrador.modelo.verificadores;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.conjuntoCarta.jugadas.JugadaEscaleraSucia;

import java.util.List;

public class VerificarEscaleraSucia extends VerificarAscendiente{

    public VerificarEscaleraSucia() {
        super(Formacion.ESCALERA_REAL);
    }

    @Override
    public JugadaEscaleraSucia formarJugada(List<Carta> cartas) {
        List<Carta> result = this.verificarListaCartas(cartas);
        if (result != null) {
            return new JugadaEscaleraSucia(result);
        }
        else return null;
    }

    @Override
    protected int buscarSiguiente(List<Carta> cartas, Carta carta) {
        Boolean found = false;
        int i = 0;
        int salida = -1;
        while ((i < cartas.size()) && (!found)) {
            Carta cartaActual = cartas.get(i);
            if (cartaActual.getTipo().ordinal() == carta.getTipo().ordinal() + 1) {
                found = true;
                salida = i;
            }
            i++;
        }
        return salida;
    }


}
