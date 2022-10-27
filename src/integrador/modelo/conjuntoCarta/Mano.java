package integrador.modelo.conjuntoCarta;

import java.util.List;

public class Mano extends ConjuntoCartas{
    private int sizeMano = 12;

    public Mano(List<Carta> cartas) {
        super(cartas);
    }

    public boolean tomaCarta(Carta carta) {
        List<Carta> cartas = getCartas();
        if (cartas.size() <= sizeMano) {
            cartas.add(carta);
            return true;
        }
        else return false;
    }

    public boolean tiraCarta(Carta carta) {
        List<Carta> cartas = getCartas();
        if ((cartas.size() > sizeMano) || (cartas.contains(carta))) {
            cartas.remove(carta);
            return true;
        }
        else return false;
    }
}
