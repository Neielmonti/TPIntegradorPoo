package integrador.modelo.conjuntoCarta;

import integrador.modelo.cartas.Carta;

import java.util.List;

public class Mano extends ConjuntoCartas{
    private int sizeMano = 12;

    public Mano(List<Carta> cartas) {
        if (cartas.size() == sizeMano) {
            this.cartas = cartas;
        }
    }

    public boolean tomaCarta(Carta carta) {
        if (this.cartas.size() <= sizeMano) {
            cartas.add(carta);
            return true;
        }
        else return false;
    }

    public boolean tiraCarta(Carta carta) {
        if ((this.cartas.size() > sizeMano) || (this.cartas.contains(carta))) {
            this.cartas.remove(carta);
            return true;
        }
        else return false;
    }
}
