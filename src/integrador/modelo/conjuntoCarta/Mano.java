package integrador.modelo.conjuntoCarta;
import integrador.vista.IMano;

import java.util.List;

public class Mano extends ConjuntoCartas implements IMano {
    private static int sizeMano = 12;

    public Mano(List<Carta> cartas) {
        super(cartas);
    }

    @Override
    public boolean agregarCarta(Carta carta) {
        List<Carta> cartas = getCartas();
        if (cartas.size() <= sizeMano) {
            return super.agregarCarta(carta);
        }
        else return false;
    }
    public static int getSizeMano(){return sizeMano;}
    public boolean tiraCarta(Carta carta) {
        List<Carta> cartas = getCartas();
        if ((cartas.size() > sizeMano) || (cartas.contains(carta))) {
            cartas.remove(carta);
            return true;
        }
        else return false;
    }
    @Override
    public String getStringCartas() {
        return super.mostrarCartas();
    }
}
