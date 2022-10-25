package integrador.modelo.conjuntoCarta;

import integrador.modelo.cartas.Carta;

import java.util.List;
import java.util.Random;

public class Mazo extends ConjuntoCartas{
    public Mazo(List<Carta> cartas) {
        super(cartas);
    }

    public Carta tomarCarta() {
        List<Carta> cartas = getCartas();
        if (cartas.size() > 0) {
            Random r = new Random();
            Carta carta = cartas.get(r.nextInt(cartas.size()));
            cartas.remove(carta);
            return carta;
        }
        else return null;
    }

}
