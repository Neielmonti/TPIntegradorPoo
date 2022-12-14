package programa.modelo.conjuntoCarta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public abstract class ConjuntoCartas implements IConjuntoCartas, Serializable {
    private List<Carta> cartas;
    public ConjuntoCartas(List <Carta> cartas) {
        this.cartas = cartas;
    }
    public boolean agregarCarta(Carta carta) {
        this.cartas.add(carta);
        return true;
    }
    public boolean isEmpty() {
        return this.cartas.isEmpty();
    }
    public boolean agregarCartaPrincipio(Carta carta) {
        List<Carta> c = new ArrayList<>();
        c.add(carta);
        c.addAll(this.cartas);
        this.cartas = c;
        return true;
    }
    public void pasarCartas(ConjuntoCartas conjuntoCartas) {
        while (!this.cartas.isEmpty()) {
            conjuntoCartas.agregarCarta(cartas.get(0));
            cartas.remove(0);
        }
    }
    public List<Carta> getCartas() {
        return this.cartas;
    }
    public void quitarCartas(ConjuntoCartas con) {
        List<Carta> resta = con.getCartas();
        for (Carta carta: resta) {
            cartas.remove(carta);
        }
    }
    public Carta tomarCarta() {
        if (!this.cartas.isEmpty()) {
            Carta carta = cartas.get(this.cartas.size()-1);
            this.cartas.remove(carta);
            return carta;
        }
        else return null;
    }
    @Override
    public String mostrarCartas() {
        String result = "";
        for (int i = 0; i < cartas.size(); i++) {
            result += (i+1) + " (" + cartas.get(i).mostrarCarta() + ")\n";
        }
        return result;
    }
    public boolean quitarCarta(Carta carta) {
        if (this.cartas.contains(carta)) {
            this.cartas.remove(carta);
            return true;
        }
        else return false;
    }
}