package programa.modelo;

import programa.controlador.Evento;
import programa.modelo.commons.Formacion;
import programa.modelo.commons.PaloCarta;
import programa.modelo.commons.TipoCarta;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.Mano;
import programa.modelo.conjuntoCarta.Mazo;
import programa.modelo.conjuntoCarta.Pozo;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import programa.modelo.verificadores.*;
import programa.utils.observer.IObservable;
import programa.utils.observer.IObservador;

import java.util.*;

public class Juego implements IObservable {
    private Queue<Jugador> jugadores = new LinkedList<>();
    private int maxJugadores = 2;
    private int minJugadores = 2;
    private Queue<Ronda> rondas = new LinkedList();
    private Mazo mazo = new Mazo();
    private Pozo pozo = new Pozo();
    private List<IObservador> observadores = new ArrayList<>();
    private List<VerificarJugada> verificadoresJugada = new ArrayList<>();

    public Juego(){
        generarRondas();
        generarVerificadores();
    }

    public void pasarSiguienteJugador() {
        Jugador aux = this.jugadores.remove();
        this.jugadores.add(aux);
        notificar(Evento.CAMBIO_DE_JUGADOR);
    }

    public void pasarSiguienteRonda() {
        Ronda aux = this.rondas.remove();
        this.rondas.add(aux);
        notificar(Evento.RONDA_GANADA);
    }


    /**
    public boolean getPreparadoParaJugar() {
        if (this.jugadores.size() >= this.minJugadores) {
            return true;
        }
        else return false;
    }
    **/

    public void agregarCartaAJuego(Jugada jugada, Carta carta, Jugador jugador, boolean alFinal){
        if (jugada.agregarCarta(carta,alFinal)) {
            jugador.getMano().quitarCarta(carta);
            if (jugador.getMano().isEmpty()) {
                pasarSiguienteRonda();
            }
            else notificar(Evento.MANO_ACTUALIZADA);
            //notificar(Evento.JUGADA_MODIFICADA);
        }
        else notificar(Evento.DESCARGA_RECHAZADA);
    }

    public Ronda getRondaActual() {
        return this.rondas.peek();
    }

    public boolean nombreValido(String nombre) {
        boolean salida = true;

        for (Jugador jugador: jugadores) {
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

    public void verificarJugadas(Jugador jugador) {
        if ((jugador == jugadores.peek()) && (!jugador.yaBajo()) && (this.rondas.peek().verificarJugadasxRonda(jugador))) {
            jugador.bajar();
            notificar(Evento.JUGADOR_BAJO);
        }
        else {
            notificar(Evento.BAJADA_RECHAZADA);
        }
    }

    public List<Jugada> getAllJugadas() {
        List<Jugada> jugadas = new ArrayList<>();
        for (Jugador jugador: jugadores) {
            jugadas.addAll(jugador.getJugadas());
        }
        return jugadas;
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
                if (jugador.getMano().isEmpty()) {
                    pasarSiguienteRonda();
                }
                else notificar(Evento.JUGADA_ARMADA);
            }
            else {notificar(Evento.JUGADA_RECHAZADA);}
        }
    }

    public boolean agregarJugador(Jugador j) {
        if ((jugadores.contains(j)) || (jugadores.size() == this.maxJugadores)) {
            return false;
        }
        else {
            for (Jugador jug: jugadores) {
                if (Objects.equals(jug.getNombre(), j.getNombre())) {
                    return false;
                }
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
        if (verificadoresJugada.isEmpty()) {
            this.verificadoresJugada.add(new VerificarEscaleraReal());
            this.verificadoresJugada.add(new VerificarEscaleraSucia());
            this.verificadoresJugada.add(new VerificarEscala());
            this.verificadoresJugada.add(new VerificarTrio());
        }
    }
    private void generarRondas(){
        if (this.rondas.isEmpty()) {

            List<CantXFormacion> listaAux = new ArrayList<>();
            /**
            this.rondas.add(new Ronda(new CantXFormacion(Formacion.TRIO,2)));

             **/

            this.rondas.add(new Ronda(Formacion.ESCALA,1)); ////////

            listaAux.add(new CantXFormacion(Formacion.TRIO,1));
            listaAux.add(new CantXFormacion(Formacion.ESCALA,1));
            this.rondas.add(new Ronda(listaAux));

            this.rondas.add(new Ronda(Formacion.ESCALA,2));

            this.rondas.add(new Ronda(Formacion.TRIO,3));

            listaAux = new ArrayList<>();
            listaAux.add(new CantXFormacion(Formacion.TRIO,2));
            listaAux.add(new CantXFormacion(Formacion.ESCALA,1));
            this.rondas.add(new Ronda(listaAux));


            listaAux = new ArrayList<>();
            listaAux.add(new CantXFormacion(Formacion.TRIO,1));
            listaAux.add(new CantXFormacion(Formacion.ESCALA,2));
            this.rondas.add(new Ronda(listaAux));

            this.rondas.add(new Ronda(Formacion.ESCALA,3));

            this.rondas.add(new Ronda(Formacion.TRIO,4));

            this.rondas.add(new Ronda(Formacion.ESCALERA_SUCIA,1));

            this.rondas.add(new Ronda(Formacion.ESCALERA_REAL,1));
        }
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

    /**

    public void repartirCartas(){
        this.resetMazo();
        for(Jugador jugador:this.jugadores) {
            jugador.setMano(this.mazo.formarMano());
        }
        this.pozo.pasarCartas(this.mazo);
        this.pozo.agregarCarta(this.mazo.tomarCarta());
    }

    **/

    public void repartirCartas(){
        this.resetMazo();
        List<Carta> cartas = new ArrayList<>();
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.NUEVE));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.DIEZ));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.J));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.JOKER));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.K));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.Q));
        jugadores.peek().setMano(new Mano(cartas));
        Jugador aux = jugadores.remove();
        jugadores.add(aux);
        jugadores.peek().setMano(new Mano(cartas));
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
        if ((jugadores.peek() == jugador)) {
            assert jugador != null;
            if (jugador.getMano() != null) {
                jugador.getMano().quitarCarta(carta);
                this.pozo.agregarCarta(carta);
                if (jugador.getMano().isEmpty()) {
                    pasarSiguienteRonda();
                }
                else pasarSiguienteJugador();
            }
        }
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
        return this.jugadores.peek();
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
