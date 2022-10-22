package integrador.modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class ConjuntoCartas {
    protected List<Carta> cartas = new ArrayList<>();

    public void agregarCarta(Carta carta) {
        this.cartas.add(carta);
    }

    public List<Carta> pasarCartas() {
        List<Carta> ret = this.cartas;
        this.cartas.clear();
        return ret;
    }

    public void agregarCartas(List<Carta> cartas) {
        if (cartas.size() > 0) {
            this.cartas.addAll(cartas);
        }
    }

    public String MostrarCartas() {
        String result = "";
        for (Carta carta : this.cartas) {
            result += carta.mostrarCarta();
        }
        return result;
    }
}
