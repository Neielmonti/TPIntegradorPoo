package integrador.modelo.conjuntoCarta;

import java.util.ArrayList;
import java.util.List;

public abstract class ConjuntoCartas {
    private List<Carta> cartas = new ArrayList<>();

    public ConjuntoCartas(List <Carta> cartas) {
        this.cartas = cartas;
    }

    public boolean agregarCarta(Carta carta) {
        this.cartas.add(carta);
        return true;
    }

    public boolean agregarCartaPrincipio(Carta carta) {
        List<Carta> c = new ArrayList<>();
        c.add(carta);
        c.addAll(this.cartas);
        this.cartas = c;
        return true;
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

    public String mostrarCartas() {
        String result = "";
        for (Carta carta : this.cartas) {
            result += carta.mostrarCarta();
        }
        return result;
    }


    public void quitarCartas(List<Carta> cartas) {
        for (Carta carta: cartas) {
            if (this.cartas.contains(carta)) {
                this.cartas.remove(carta);
            }
        }
    }
}
