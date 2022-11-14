package integrador.modelo;

import integrador.modelo.conjuntoCarta.Mano;
import integrador.modelo.conjuntoCarta.jugadas.Jugada;
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

    public Mano getMano(){
        return this.mano;
    }

    public void setMano(Mano mano){
        this.mano = mano;
    }
    public List<Jugada> getJugadas(){
        return this.jugadas;
    }
}
