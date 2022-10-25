package integrador.modelo.conjuntoCarta;

import integrador.modelo.cartas.Carta;

import java.util.List;

public class Pozo extends ConjuntoCartas{

    public Pozo(List<Carta> cartas) {
        super(cartas);
    }

    public Carta tomarCarta() {
        List<Carta> cartas = getCartas();
        if (cartas.size() > 0) {
            return cartas.get(cartas.size()-1);
        }
        else return null;
    }

}
