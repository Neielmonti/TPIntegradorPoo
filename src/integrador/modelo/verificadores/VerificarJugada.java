package integrador.modelo.verificadores;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Jugada;
import integrador.modelo.conjuntoCarta.Mano;

public abstract class VerificarJugada {
    protected Formacion forma;
    protected int cantidadCartas;
    public abstract Jugada formarJugada(Mano mano);
}
