package integrador.modelo.conjuntoCarta;

public abstract class VerificarJugada {
    protected Formacion forma;
    protected int cantidadCartas;
    public abstract Jugada formarJugada(Mano mano);
}
