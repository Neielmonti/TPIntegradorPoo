package integrador.modelo;

import java.util.Random;

public class Mazo extends ConjuntoCartas{
    public Carta tomarCarta() {
        if (cartas.size() > 0) {
            Random r = new Random();
            Carta carta = this.cartas.get(r.nextInt(this.cartas.size()));
            this.cartas.remove(carta);
            return carta;
        }
        else return null;
    }

}
