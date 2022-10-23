package integrador.modelo.conjuntoCarta;

import integrador.modelo.cartas.Carta;

public class Pozo extends ConjuntoCartas{

    public Carta tomarCarta() {
        if (cartas.size() > 0) {
            return this.cartas.get(this.cartas.size()-1);
        }
        else return null;
    }

}
