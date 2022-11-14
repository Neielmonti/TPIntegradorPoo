package integrador.modelo.conjuntoCarta;

import java.util.ArrayList;
import java.util.List;

public class Pozo extends ConjuntoCartas {

    public Pozo() {
        super(new ArrayList<>());
    }

    @Override
    public String mostrarCartas() {
        List<Carta> cartas = getCartas();
        String result = "";
        if (!cartas.isEmpty()) {
            result = cartas.get(cartas.size() - 1).mostrarCarta();
        }
        return result;
    }

}