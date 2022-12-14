package programa.modelo.jugador;
import programa.modelo.conjuntoCarta.Mano;
import programa.modelo.conjuntoCarta.jugadas.Jugada;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Jugador implements IJugador, Serializable {
    @Serial
    private static final long serialVersionUID = -6610403937729533391L;
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
        // Si tiene una mano, se le suman los puntos de la mano al puntaje del jugador
        if (this.mano != null) {
            this.puntaje += this.mano.calcularPuntosCartas();
        }
    }
    public Mano tomarMano(){
        // Se le quita la mano al jugador
        Mano m = this.mano;
        this.mano = null;
        return m;
    }
    public void deshacerJugadas() {
        if (mano != null) {
            // Todas las jugadas del jugador le pasan sus cartas a la mano del jugador
            for (Jugada jugada: jugadas) {
                jugada.pasarCartas(mano);
            }
            this.jugadas = new ArrayList<>();
        }
    }
    public void resetearJugador() {
        // Se lo deja preparado para volver a jugar
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