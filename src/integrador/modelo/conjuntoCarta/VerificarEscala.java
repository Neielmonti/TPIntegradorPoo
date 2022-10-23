package integrador.modelo.conjuntoCarta;

public class VerificarEscala extends VerificarJugada{

    public VerificarEscala() {
        this.forma = Formacion.ESCALA;
        this.cantidadCartas = 4;
    }

    @Override
    public Jugada formarJugada(Mano mano) {
        return null;
    }
}
