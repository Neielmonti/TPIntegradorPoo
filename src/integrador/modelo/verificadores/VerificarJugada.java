package integrador.modelo.verificadores;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.conjuntoCarta.jugadas.Jugada;

import java.util.List;

public abstract class VerificarJugada {
    protected Formacion forma;
    protected int cantidadCartas;
    public abstract Jugada formarJugada(List<Carta> cartas);

}
