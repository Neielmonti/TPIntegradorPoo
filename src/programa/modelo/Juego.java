package programa.modelo;

import programa.controlador.Evento;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.Mano;
import programa.modelo.conjuntoCarta.Mazo;
import programa.modelo.conjuntoCarta.Pozo;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import programa.modelo.verificadores.*;
import programa.utils.observer.IObservable;
import programa.utils.observer.IObservador;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Juego implements IObservable {
    private List<Jugador> jugadores = new ArrayList<>();
    private int maxJugadores = 2;
    private int minJugadores = 2;
    private List<Ronda> rondas;
    private Jugador jugadorActual;
    private Ronda rondaActual;
    private Mazo mazo = new Mazo();
    private Pozo pozo = new Pozo();
    private List<IObservador> observadores = new ArrayList<>();
    private List<VerificarJugada> verificadoresJugada = new ArrayList<>();

    public Juego(){
        generarRondas();
        this.rondaActual = this.rondas.get(0);
        generarVerificadores();
    }

    public void pasarSiguienteJugador() {
        int indiceSiguiente = (this.jugadores.indexOf(this.jugadorActual) + 1) % this.jugadores.size();
        this.jugadorActual = this.jugadores.get(indiceSiguiente);
        notificar(Evento.CAMBIO_DE_JUGADOR);
    }

    public void pasarSiguienteRonda() {
        int indiceSiguiente = (this.rondas.indexOf(this.rondaActual) + 1) % this.rondas.size();
        this.rondaActual = this.rondas.get(indiceSiguiente);
    }


    /**
    public boolean getPreparadoParaJugar() {
        if (this.jugadores.size() >= this.minJugadores) {
            return true;
        }
        else return false;
    }
    **/

    public Ronda getRondaActual() {
        return this.rondaActual;
    }

    public boolean nombreValido(String nombre) {
        boolean salida = true;
        for (Jugador jugador:jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                salida = false;
            }
        }
        return salida;
    }

    public boolean faltanJugadores() {
        if (this.jugadores.size() < maxJugadores) {
            return true;
        }
        else return false;
    }

    public void deshacerJugadas(Jugador jugador) {
        if (jugadores.contains(jugador)) {
            jugador.deshacerJugadas();
            notificar(Evento.MANO_ACTUALIZADA);
        }
    }

    public void armarJugada(List<Carta> cartas, Jugador jugador) {
        if (jugadores.contains(jugador)) {
            int i = 0;
            Jugada jugada = null;
            while ((i < verificadoresJugada.size()) && (jugada == null)) {
                jugada = verificadoresJugada.get(i).formarJugada(cartas,jugador);
                i++;
            }
            if (jugada != null) {
                jugador.getMano().quitarCartas(jugada);
                notificar(Evento.JUGADA_ARMADA);
            }
            else {notificar(Evento.JUGADA_RECHAZADA);}
        }
    }

    public boolean agregarJugador(Jugador j) {
        if ((jugadores.contains(j)) || (jugadores.size() == this.maxJugadores)) {
            return false;
        }
        else {
            for (Jugador jug:jugadores) {
                if (Objects.equals(jug.getNombre(), j.getNombre())) {
                    return false;
                }
            }
            if (jugadores.isEmpty()) {
                jugadorActual = j;
            }
            jugadores.add(j);
            if (jugadores.size() == maxJugadores) {
                repartirCartas();
                //notificar(Evento.LISTO_PARA_JUGAR);
                notificar(Evento.CAMBIO_DE_JUGADOR);
            }
            return true;
        }
    }
    private void generarVerificadores() {
        this.verificadoresJugada.add(new VerificarEscaleraReal());
        this.verificadoresJugada.add(new VerificarEscaleraSucia());
        this.verificadoresJugada.add(new VerificarEscala());
        this.verificadoresJugada.add(new VerificarTrio());
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

    //public String mostrarMazo() {return this.mazo.mostrarCartas();}

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

    public void tomarDelPozo(Jugador jugador) {
        jugador.getMano().agregarCarta(this.pozo.tomarCarta());
        if (this.pozo.isEmpty()) {
            this.pozo.agregarCarta(this.mazo.tomarCarta());
        }
        notificar(Evento.MANO_ACTUALIZADA);
        notificar(Evento.POZO_ACTUALIZADO);
    }

    public void tirarCartaPozo(Jugador jugador, Carta carta) {
        jugador.getMano().quitarCarta(carta);
        this.pozo.agregarCarta(carta);
        pasarSiguienteJugador();
        //notificar(Evento.MANO_ACTUALIZADA);
        //notificar(Evento.POZO_ACTUALIZADO);
    }

    public void tomarDelMazo(Jugador jugador) {
        jugador.getMano().agregarCarta(this.mazo.tomarCarta());
        if (this.mazo.isEmpty()) {
            pozo.pasarCartas(this.mazo);
            this.pozo.agregarCarta(this.mazo.tomarCarta());
        }
        notificar(Evento.MANO_ACTUALIZADA);
    }

    public Jugador getJugadorActual() {
        return this.jugadorActual;
    }

    public Mano getManoJugador(Jugador jugador){
        if (jugadores.contains(jugador)) {
            return jugador.getMano();
        }
        else return null;
    }
    public Carta cartaRandom() {
        return this.mazo.tomarCarta();
    }

    @Override
    public void notificar(Evento evento) {
        for (IObservador observador : this.observadores) {
            observador.actualizar(evento, this);
        }
    }

    @Override
    public void agregadorObservador(IObservador observador) {
        this.observadores.add(observador);
    }
}
