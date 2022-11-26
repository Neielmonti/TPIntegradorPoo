package programa.modelo.conjuntoCarta.jugadas;

import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.ConjuntoCartas;

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
