package programa.modelo;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.Mano;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import programa.vista.IJugador;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
public class Jugador implements IJugador, Serializable {
    private final String nombre;
    private int puntaje = 0;
    private boolean bajo = false;
    private boolean preparado = false;
    private List<Jugada> jugadas = new ArrayList<>();
    private Mano mano;
    public Jugador(String nombre){
        this.nombre = nombre;
    }
    public int getPuntaje() {
        return this.puntaje;
    }
    public boolean yaBajo() {
        return this.bajo;
    }
    public void bajar() {
        this.bajo = true;
    }
    public void actualizarPuntaje() {
        this.puntaje += this.mano.calcularPuntosCartas();
    }
    public Mano tomarMano(){
        Mano m = this.mano;
        this.mano = null;
        return m;
    }
    public void deshacerJugadas() {
        if (mano != null) {
            for (Jugada jugada: jugadas) {
                List<Carta> cartas = jugada.getCartas();
                jugada.pasarCartas(mano);
            }
            this.jugadas = new ArrayList<>();
        }
    }
    public void resetearJugador() {
        this.bajo = false;
        deshacerJugadas();
        this.preparado = false;
    }
    public Mano getMano(){
        return this.mano;
    }
    public String getNombre(){
        return this.nombre;
    }
    public void setMano(Mano mano){
        this.mano = mano;
    }
    public List<Jugada> getJugadas(){
        return this.jugadas;
    }
    public void addJugada(Jugada jugada) {this.jugadas.add(jugada);}
    public void estaPreparado() {this.preparado = true;}
    public boolean getPreparado() {return this.preparado;}
}