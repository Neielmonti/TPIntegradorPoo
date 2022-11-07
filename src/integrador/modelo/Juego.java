package integrador.modelo;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.conjuntoCarta.Mazo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Juego {
    private List<Jugador> jugadores = new ArrayList<>();
    private List<Ronda> rondas = new ArrayList<>();

    private Jugador JugadorActual;
    private Mazo mazo = new Mazo();

    public boolean agregarJugador(Jugador j) {
        if (jugadores.contains(j)){
            return false;
        }
        else {
            jugadores.add(j);
            return true;
        }
    }


    private void generarRondas(){
        List<Formacion> f;
        List<Ronda> rondas1 = new ArrayList<>();
        f = new ArrayList<>(Arrays.asList(Formacion.TRIO,Formacion.TRIO));
        rondas1.add(new Ronda(f));
        f = new ArrayList<>(Arrays.asList(Formacion.ESCALA,Formacion.TRIO));
        rondas1.add(new Ronda(f));
        f = new ArrayList<>(Arrays.asList(Formacion.ESCALA,Formacion.ESCALA));
        rondas1.add(new Ronda(f));
        f = new ArrayList<>(Arrays.asList(Formacion.TRIO,Formacion.TRIO,Formacion.TRIO));
        rondas1.add(new Ronda(f));
        f = new ArrayList<>(Arrays.asList(Formacion.ESCALA,Formacion.TRIO,Formacion.TRIO));
        rondas1.add(new Ronda(f));
        f = new ArrayList<>(Arrays.asList(Formacion.ESCALA,Formacion.ESCALA,Formacion.TRIO));
        rondas1.add(new Ronda(f));
        f = new ArrayList<>(Arrays.asList(Formacion.ESCALA,Formacion.ESCALA,Formacion.ESCALA));
        rondas1.add(new Ronda(f));
        f = new ArrayList<>(Arrays.asList(Formacion.TRIO,Formacion.TRIO,Formacion.TRIO,Formacion.TRIO));
        rondas1.add(new Ronda(f));
        f = new ArrayList<>(Arrays.asList(Formacion.ESCALERA_SUCIA));
        rondas1.add(new Ronda(f));
        f = new ArrayList<>(Arrays.asList(Formacion.ESCALERA_REAL));
        rondas1.add(new Ronda(f));
        this.rondas = rondas1;
        }


    public String mostrarMazo() {
        return this.mazo.mostrarCartas();
    }
    public Carta cartaRandom() {
        return this.mazo.tomarCarta();
    }


}
