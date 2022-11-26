package programa.controlador;

import programa.modelo.Juego;
import programa.modelo.Jugador;
import programa.modelo.conjuntoCarta.Carta;
import programa.utils.observer.IObservable;
import programa.utils.observer.IObservador;
import programa.vista.*;

//import integrador.vista.IVista;
public class Controlador implements IObservador {
    private Juego juego;
    private Jugador jugador;
    private VistaConsolaSwing vista;

    public Controlador(Juego juego, VistaConsolaSwing vista) {
        this.juego = juego;
        this.juego.agregadorObservador(this);
        this.vista = vista;
        this.vista.setControlador(this);
    }
    @Override
    public void actualizar(Evento evento, IObservable observado) {
        switch (evento) {

            case CAMBIO_DE_JUGADOR -> {
                vista.mostrarMano();
                vista.mostrarPozo();
                if (this.jugador == this.juego.getJugadorActual()) {
                    this.vista.setEstado(EstadoVista.TOMAR_CARTA);
                }
                else this.vista.setEstado(EstadoVista.ESPERANDO_TURNO);
            }

            case MANO_ACTUALIZADA -> {
                if (this.jugador == this.juego.getJugadorActual()) {
                    this.vista.mostrarMano();
                    this.vista.setEstado(EstadoVista.TIRAR_O_BAJAR);
                }
            }

            case POZO_ACTUALIZADO -> {
                this.vista.mostrarPozo();
            }

            case LISTO_PARA_JUGAR -> {
                vista.mostrarMano();
                vista.mostrarPozo();
                //MOSTRAR 'ESPERANDO A TU TURNO'
            }
        }
    }

    public IMano getMano() {
        return this.juego.getManoJugador(this.jugador);
    }

    public boolean nombreValido(String nombre) {
        if ((nombre.equals("")) || (!this.juego.nombreValido(nombre.trim()))) {
            return false;
        }
        else return true;
    }

    public void tomarCartaPozo() {
        this.juego.tomarDelPozo(this.jugador);
    }

    public void tirarCartaAlPozo(int indice) {
        Carta carta = this.jugador.getMano().getCartas().get(indice);
        this.juego.tirarCartaPozo(this.jugador,carta);
    }

    public void tomarCartaMazo() {
        this.juego.tomarDelMazo(this.jugador);
    }

    public void agregarJugador(String nombre) {
        Jugador j = new Jugador(nombre);
        this.jugador = j;
        vista.listoParaJugar();
        juego.agregarJugador(j);
    }

    public IConjuntoCarta getPozo() {
        return this.juego.getPozo();
    }
}
