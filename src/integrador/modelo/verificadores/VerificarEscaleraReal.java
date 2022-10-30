package integrador.modelo.verificadores;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.conjuntoCarta.jugadas.Jugada;
import integrador.modelo.conjuntoCarta.jugadas.JugadaEscaleraReal;

import java.util.List;

public class VerificarEscaleraReal extends VerificarAscendiente{
    public VerificarEscaleraReal() {
        this.forma = Formacion.ESCALERA_REAL;
        this.cantidadCartas = 13;
    }

    @Override
    public Jugada formarJugada(List<Carta> cartas) {
        if (verificarListaCartas(cartas)) {
            return new JugadaEscaleraReal(cartas);
        }
        else return null;
    }
}
