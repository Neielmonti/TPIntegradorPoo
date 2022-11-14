package integrador.modelo;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.conjuntoCarta.Mano;
import integrador.modelo.conjuntoCarta.Mazo;
import integrador.modelo.conjuntoCarta.Pozo;
import integrador.utils.observer.IObservable;
import integrador.utils.observer.IObservador;
import integrador.vista.IRonda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Juego implements IObservable {
    private List<Jugador> jugadores = new ArrayList<>();
    private int maxJugadores = 4;
    private int minJugadores = 2;
    private List<Ronda> rondas;
    private Jugador jugadorActual;
    private Ronda rondaActual;
    private Mazo mazo = new Mazo();
    private Pozo pozo = new Pozo();
    private List<IObservador> observadores = new ArrayList<>();


    public Juego(){
        generarRondas();
        this.rondaActual = this.rondas.get(0);
    }

    public void pasarSiguienteJugador() {
        int indiceSiguiente = (this.jugadores.indexOf(this.jugadorActual) + 1) % this.jugadores.size();
        this.jugadorActual = this.jugadores.get(indiceSiguiente);
    }

    public void pasarSiguienteRonda() {
        int indiceSiguiente = (this.rondas.indexOf(this.rondaActual) + 1) % this.rondas.size();
        this.rondaActual = this.rondas.get(indiceSiguiente);
    }

    public boolean getPreparadoParaJugar() {
        if (this.jugadores.size() >= this.minJugadores) {
            return true;
        }
        else return false;
    }

    public Ronda getRondaActual() {
        return this.rondaActual;
    }

    public boolean agregarJugador(Jugador j) {
        if ((jugadores.contains(j)) || (jugadores.size() == this.maxJugadores)) {
            return false;
        }
        else {
            if (jugadores.isEmpty()) {
                jugadorActual = j;
            }
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

    private void resetMazo(){
        for(Jugador jugador:this.jugadores) {
            Mano manoActual = jugador.getMano();
            if (manoActual != null) {
                manoActual.pasarCartas(this.mazo);
            }
        }
        pozo.pasarCartas(this.mazo);
    }
    public void repartirCartas(){
        this.resetMazo();
        for(Jugador jugador:this.jugadores) {
            jugador.setMano(this.mazo.formarMano());
        }
        this.pozo.pasarCartas(this.mazo);
        this.pozo.agregarCarta(this.mazo.tomarCarta());
    }

    public Pozo getPozo() {
        return this.pozo;
    }

    public Mano getManoJugadorActual(){
        return this.jugadorActual.getMano();
    }
    public Carta cartaRandom() {
        return this.mazo.tomarCarta();
    }

    @Override
    public void notificar(Object evento) {
        for (IObservador observador : this.observadores) {
            observador.actualizar(evento, this);
        }
    }

    @Override
    public void agregadorObservador(IObservador observador) {
        this.observadores.add(observador);
    }
}
