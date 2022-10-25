package integrador.modelo.conjuntoCarta;

import integrador.modelo.cartas.Carta;

import java.util.List;

public class Jugada extends ConjuntoCartas{
    private Formacion forma;

    public Jugada(Formacion forma, List<Carta> cartas) {
        super(cartas);
        this.forma = forma;
    }

    public Formacion getForma(){
        return this.forma;
    }
}
