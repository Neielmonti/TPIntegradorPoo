package programa.controlador;
import programa.modelo.Juego;
import programa.modelo.Jugador;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.Mano;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import programa.utils.observer.IObservable;
import programa.utils.observer.IObservador;
import programa.vista.*;

import java.util.ArrayList;
import java.util.List;

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
                vista.clearMemo();
                vista.mostrarRonda();
                vista.mostrarMano();
                if (this.jugador == this.juego.getJugadorActual()) {
                    if (this.jugador.yaBajo()) {
                        this.vista.mostrarAllJugadas();
                    }
                    vista.mostrarPozo();
                    this.vista.setEstado(EstadoVista.TOMAR_CARTA);
                }
                else {
                    vista.mostrarPozo();
                    this.vista.setEstado(EstadoVista.ESPERANDO_TURNO);
                }
            }

            case MANO_ACTUALIZADA -> {
                if (this.jugador == this.juego.getJugadorActual()) {
                    this.vista.clearMemo();
                    this.vista.mostrarRonda();
                    this.vista.mostrarMano();
                    if (this.jugador.yaBajo()) {
                        this.vista.mostrarAllJugadas();
                        this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                    }
                    else this.vista.setEstado(EstadoVista.TIRAR_O_BAJAR);
                }
            }

            case POZO_ACTUALIZADO -> {
                this.vista.mostrarPozo();
            }

            case LISTO_PARA_JUGAR -> {
                // POSIBLEMENTE INUTL
                //vista.mostrarMano();
                //vista.mostrarPozo();
                //MOSTRAR 'ESPERANDO A TU TURNO'
            }

            case JUGADA_ARMADA -> {
                if (this.jugador == juego.getJugadorActual()) {
                    this.vista.mostrarJugadasJugador();
                    this.vista.mostrarMano();
                    this.vista.setEstado(EstadoVista.BAJAR);
                }
            }

            case JUGADA_RECHAZADA -> {
                if (this.jugador == this.juego.getJugadorActual()) {this.vista.jugadaRechazada();}
            }

            case JUGADOR_BAJO -> {
                this.vista.mostrarAllJugadas();
                if (this.jugador == this.juego.getJugadorActual()) {
                    this.vista.mostrarMano();
                    this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                }
            }

            case BAJADA_RECHAZADA -> {
                if (this.jugador == this.juego.getJugadorActual()) {
                    this.vista.bajadaRechazada();
                    this.vista.setEstado(EstadoVista.TIRAR_O_BAJAR);
                    this.juego.deshacerJugadas(this.jugador);
                }
            }

            case JUGADA_MODIFICADA -> {
                if (this.jugador == this.juego.getJugadorActual()) {
                    vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                }
            }

            case DESCARGA_RECHAZADA -> {
                if (this.jugador == this.juego.getJugadorActual()) {
                    vista.mostrarMano();
                    vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                }
            }

            case RONDA_GANADA -> {
                vista.rondaGanada();
            }
        }
    }

    public void deshacerJugadas() {
        this.juego.deshacerJugadas(this.jugador);
    }

    public IMano getMano() {
        return this.juego.getManoJugador(this.jugador);
    }

    public boolean nombreValido(String nombre) {
        if ((nombre.trim().equals("")) || (!this.juego.nombreValido(nombre.trim()))) {
            return false;
        }
        else return true;
    }

    public IJugador getGanador() {
        return this.juego.getJugadorActual();
    }

    public void agregarCartaJuego(int indiceJugada, int indiceCarta, boolean alFinal) {
        Jugada jugada = this.juego.getAllJugadas().get(indiceJugada);
        Carta carta = this.jugador.getMano().tomarCarta(indiceCarta);
        if ((jugada != null) && (carta != null)) {
            this.juego.agregarCartaAJuego(jugada,carta,jugador,alFinal);
        }
        else this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
    }

    public List<IJugada> getJugadasJugador() {
        List<IJugada> jugadasOut = new ArrayList<>(jugador.getJugadas());
        return jugadasOut;
    }
    public List<IJugada> getAllJugadas(){
        List<IJugada> jugadasOut = new ArrayList<>(this.juego.getAllJugadas());
        return jugadasOut;
    }

    public boolean faltanJugadoes() {
        return this.juego.faltanJugadores();
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

    public void verificarJuegos() {
        this.juego.verificarJugadas(this.jugador);
    }

    public void agregarJugador(String nombre) {
        Jugador j = new Jugador(nombre);
        this.jugador = j;
        vista.listoParaJugar();
        juego.agregarJugador(j);
    }

    public void armarJugada(int[] indices) {
        List<Carta> cartasJugada = new ArrayList<>();
        List<Carta> cartasMano = this.jugador.getMano().getCartas();
        for (int index : indices) {
            cartasJugada.add(cartasMano.get(index));
        }
        this.juego.armarJugada(cartasJugada,this.jugador);
    }

    public IRonda getRonda(){
        return this.juego.getRondaActual();
    }

    public boolean yaBajado() {
        return this.jugador.yaBajo();
    }

    public IConjuntoCartas getPozo() {
        return this.juego.getPozo();
    }
}
