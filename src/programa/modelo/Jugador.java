package programa.modelo;

import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.Mano;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private int puntaje = 0;
    private boolean bajo = false;
    private List<Jugada> jugadas = new ArrayList<>();
    private Mano mano;

    public Jugador(String nombre) {
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

    public void agregarPuntos(int puntos) {
        if (puntos > 0) {
            this.puntaje += puntos;
        }
    }
    public Mano tomarMazo(){
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
}
