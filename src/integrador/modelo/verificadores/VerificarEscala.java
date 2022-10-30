package integrador.modelo.verificadores;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.conjuntoCarta.Mano;
import integrador.modelo.conjuntoCarta.jugadas.Jugada;
import integrador.modelo.conjuntoCarta.jugadas.JugadaEscala;
import integrador.modelo.conjuntoCarta.jugadas.JugadaTrio;

import java.util.List;

public class VerificarEscala extends VerificarAscendiente{
    public VerificarEscala() {
        this.forma = Formacion.ESCALA;
        this.cantidadCartas = 4;
    }


    @Override
    public Jugada formarJugada(List<Carta> cartas) {
        if (this.verificarListaCartas(cartas)) {
            return new JugadaEscala(cartas);
        }
        return null;
    }
}
