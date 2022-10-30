package integrador.modelo.conjuntoCarta.jugadas;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.conjuntoCarta.ConjuntoCartas;

import java.util.List;

public class Jugada extends ConjuntoCartas {
    private Formacion forma;

    public Jugada(Formacion forma, List<Carta> cartas) {
        super(cartas);
        this.forma = forma;
    }

    public Formacion getForma(){
        return this.forma;
    }

}
