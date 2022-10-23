package integrador.modelo.conjuntoCarta;

import integrador.modelo.cartas.Carta;

import java.util.List;

public class Jugada extends ConjuntoCartas{
    private Formacion forma;

    public Jugada(Formacion forma, List<Carta> cartas) {
        super();
        this.forma = forma;
        this.cartas = cartas;
    }

    public Formacion getForma(){
        return this.forma;
    }
}
