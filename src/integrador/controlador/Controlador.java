package integrador.controlador;

import integrador.modelo.Juego;
import integrador.modelo.Jugador;
import integrador.utils.observer.IObservable;
import integrador.utils.observer.IObservador;
import integrador.vista.IMano;
import integrador.vista.IVista;
import integrador.vista.VistaConsola;
import integrador.vista.VistaConsolaSwing;

//import integrador.vista.IVista;
public class Controlador implements IObservador {
    private Juego juego;
    private Jugador jugador;
    private VistaConsolaSwing vista;

    public Controlador(Juego juego, VistaConsolaSwing vista) {
        this.juego = juego;
        this.vista = vista;
        this.vista.setControlador(this);
    }
    @Override
    public void actualizar(Object evento, IObservable observado) {
        if ((evento == Evento.CAMBIO_DE_JUGADOR) && (this.jugador == juego.getJugadorActual())) {
            vista.mostrarMano();
            //cosas de vista, mostrar cartas, pozo, mano, etc.
        }
        if (evento == Evento.MANO_ACTUALIZADA) {
            //al jugador actual mostrarle su mano otra vez
        }
    }


    public IMano getMano() {
        return this.juego.getManoJugador(this.jugador);
    }

    /**
    public void iniciarJuego() {
        if (newGame) {
            this.juego.repartirCartas();
            newGame = false;
        }
        this.vista.mostrarRonda(this.juego.getRondaActual());
        this.vista.mostrarMano(this.juego.getManoJugadorActual());
        this.vista.mostrarConjuntoCarta(this.juego.getPozo());

        if (this.vista.tomarDeMazoOPozo()) {
            this.juego.tomarDelMazo(this.juego.getJugadorActual());
        }
        else {
            this.juego.tomarDelPozo(this.juego.getJugadorActual());
        }

        this.vista.mostrarMano(this.juego.getManoJugadorActual());
        this.vista.mostrarConjuntoCarta(this.juego.getPozo());
    } **/


}
