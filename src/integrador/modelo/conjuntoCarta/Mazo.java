package integrador.modelo.conjuntoCarta;

import integrador.modelo.commons.PaloCarta;
import integrador.modelo.commons.TipoCarta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mazo extends ConjuntoCartas{
    public Mazo() {super(crearMazo());}
    private static List<Carta> crearMazo() {
        List<Carta> mazoList = new ArrayList<>();
        for (PaloCarta palo : PaloCarta.values()) {
            if (palo != PaloCarta.JOKER) {
                for (TipoCarta tipo : TipoCarta.values()) {
                    if (tipo != TipoCarta.JOKER) {
                        Carta c1 = new Carta(palo, tipo);
                        Carta c2 = new Carta(palo, tipo);
                        mazoList.add(c1);
                        mazoList.add(c2);
                    }
                }
            }
        }
        Carta c2;
        for (int i = 0; i < 4; i++) {
            c2 = new Carta(PaloCarta.JOKER, TipoCarta.JOKER);
            mazoList.add(c2);
        }
        return mazoList;
    }
    @Override
    public Carta tomarCarta() {
        List<Carta> cartas = getCartas();
        if (cartas.size() > 0) {
            Random r = new Random();
            Carta carta = cartas.get(r.nextInt(cartas.size()));
            return carta;
        }
        else return null;
    }
}
