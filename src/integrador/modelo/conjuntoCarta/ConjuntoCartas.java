package integrador.modelo.conjuntoCarta;

import integrador.modelo.cartas.Carta;

import java.util.ArrayList;
import java.util.List;

public abstract class ConjuntoCartas {
    private List<Carta> cartas = new ArrayList<>();

    public ConjuntoCartas(List <Carta> cartas) {
        this.cartas = cartas;
    }

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

    public List<Carta> getCartas() {
        return this.cartas;
    }

    public void quitarCartas(ConjuntoCartas con) {
        List<Carta> resta = con.getCartas();
        for (Carta carta: resta) {
            if (cartas.contains(carta)) {
                cartas.remove(carta);
            }
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
