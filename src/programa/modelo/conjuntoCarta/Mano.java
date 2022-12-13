package programa.modelo.conjuntoCarta;
import programa.vista.IMano;
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
    @Override
    public int getCantidadCartas() {
        return getCartas().size();
    }
    public Carta tomarCarta(int indice) {
        List<Carta> c = this.getCartas();
        if ((indice >= 0) && (indice < c.size())) {
            Carta carta = c.get(indice);
            quitarCarta(carta);
            return carta;
        }
        else return null;
    }
    public int calcularPuntosCartas() {
        // Se suman los valores (en puntos) que tienen todas las cartas de la mano
        List<Carta> cartas = this.getCartas();
        int resultado = 0;
        for (Carta carta: cartas) {
            resultado += carta.getTipo().getValor();
        }
        return resultado;
    }
}