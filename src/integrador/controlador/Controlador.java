package integrador.controlador;

import integrador.modelo.Juego;
import integrador.modelo.Jugador;
import integrador.utils.observer.IObservable;
import integrador.utils.observer.IObservador;
import integrador.vista.VistaConsola;

import java.util.TreeMap;

//import integrador.vista.IVista;
public class Controlador implements IObservador {
    private Juego juego;
    private boolean newGame = true;
    private VistaConsola vista;

    public Controlador(Juego juego, VistaConsola vista) {
        this.juego = juego;
        this.vista = vista;
        this.vista.setControlador(this);
    }
    @Override
    public void actualizar(Object evento, IObservable observado) {

    }

    public void agregarJugador(String nombre) {
        Jugador jugador = new Jugador(nombre);
        boolean respuesta = this.juego.agregarJugador(jugador);
        if (!respuesta) {
            this.vista.jugadorNoAgregado();
        }
    }

    public void reiniciar() {
        this.newGame = true;
    }

    public boolean getPreparadoParaJugar() {
        return this.juego.getPreparadoParaJugar();
    }

    public void iniciarJuego() {
        if (newGame) {
            this.juego.repartirCartas();
            newGame = false;
        }
        this.vista.mostrarRonda(this.juego.getRondaActual());
        this.vista.mostrarMano(this.juego.getManoJugadorActual());
        this.vista.mostrarConjuntoCarta(this.juego.getPozo());
    }
}
