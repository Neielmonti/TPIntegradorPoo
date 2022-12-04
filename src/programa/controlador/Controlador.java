package programa.controlador;
import programa.modelo.Juego;
import programa.modelo.Jugador;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import programa.utils.observer.IObservable;
import programa.utils.observer.IObservador;
import programa.vista.*;
import java.util.ArrayList;
import java.util.List;
public class Controlador implements IObservador {
    private Juego juego;
    private String nombre;
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
            case JUGADOR_AGREGADO -> {
                if (!(observado instanceof Jugador)) {
                    break;
                }
                Jugador j = (Jugador) (observado);
                this.nombre = j.getNombre();
                this.vista.setEstado(EstadoVista.ESPERANDO_USUARIO);
                this.juego.agregarJugador(j);
            }
            case CAMBIO_DE_JUGADOR -> {
                if (!(observado instanceof Jugador)) {
                    break;
                }
                Jugador j = (Jugador) (observado);
                vista.clearMemo();
                vista.mostrarRonda();
                this.vista.setManoActual(j.getMano());
                vista.mostrarMano();
                if (j == this.juego.getJugadorActual()) {
                    if (j.yaBajo()) {
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
                if (!(observado instanceof Jugador)) {
                    break;
                }
                Jugador j = (Jugador) (observado);
                if (j == this.juego.getJugadorActual()) {
                    this.vista.clearMemo();
                    this.vista.mostrarRonda();
                    this.vista.setManoActual(j.getMano());
                    this.vista.mostrarMano();
                    if (j.yaBajo()) {
                        this.vista.mostrarAllJugadas();
                        this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                    }
                    else this.vista.setEstado(EstadoVista.TIRAR_O_BAJAR);
                }
            }
            case POZO_ACTUALIZADO -> this.vista.mostrarPozo();
            case JUGADA_ARMADA -> {
                if (!(observado instanceof Jugador)) {
                    break;
                }
                Jugador j = (Jugador) (observado);
                if (j == juego.getJugadorActual()) {
                    this.vista.clearMemo();
                    //this.vista.setManoActual(j.getMano());//////////
                    this.vista.mostrarRonda();
                    this.vista.mostrarMano();
                    this.vista.mostrarJugadasJugador(j);
                    this.vista.setEstado(EstadoVista.BAJAR);
                }
            }
            case JUGADA_RECHAZADA -> {
                if (!(observado instanceof Jugador)) {
                    break;
                }
                Jugador j = (Jugador) (observado);
                if (j == this.juego.getJugadorActual()) {this.vista.jugadaRechazada();}
            }
            case JUGADOR_BAJO -> {
                if (!(observado instanceof Jugador)) {
                    break;
                }
                Jugador j = (Jugador) (observado);
                if (j == this.juego.getJugadorActual()) {
                    this.vista.clearMemo();
                    this.vista.mostrarMano();
                    this.vista.mostrarAllJugadas();
                    this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                }
                else this.vista.mostrarAllJugadas();
            }
            case BAJADA_RECHAZADA -> {
                if (!(observado instanceof Jugador)) {
                    break;
                }
                Jugador j = (Jugador) (observado);
                if (j == this.juego.getJugadorActual()) {
                    this.vista.bajadaRechazada();
                    this.vista.setEstado(EstadoVista.TIRAR_O_BAJAR);
                    this.juego.deshacerJugadas();
                }
            }
            case JUGADA_MODIFICADA -> {
                if (!(observado instanceof Jugador)) {
                    break;
                }
                Jugador j = (Jugador) (observado);
                if (j != this.juego.getJugadorActual()) {
                    vista.mostrarAllJugadas();
                }
            }
            case DESCARGA_RECHAZADA -> {
                if (!(observado instanceof Jugador)) {
                    break;
                }
                Jugador j = (Jugador) (observado);
                if (j == this.juego.getJugadorActual()) {
                    this.vista.setManoActual(j.getMano());
                    vista.mostrarMano();
                    vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                }
            }
            case RONDA_GANADA -> {
                if (!(observado instanceof Jugador)) {
                    break;
                }
                Jugador j = (Jugador) (observado);
                this.vista.rondaGanada(j);
                this.vista.setEstado(EstadoVista.ESPERANDO_USUARIO);
            }
        }
    }
    public void deshacerJugadas() {
        this.juego.deshacerJugadas();
    }
    public IMano getMano() {
        return this.juego.getManoJugador();
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
        Carta carta = this.juego.getJugadorActual().getMano().tomarCarta(indiceCarta);
        if ((jugada != null) && (carta != null)) {
            this.juego.agregarCartaAJugada(jugada,carta,alFinal);
        }
        else this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
    }
    public List<IJugada> getJugadasJugador() {
        List<IJugada> jugadasOut = new ArrayList<>(juego.getJugadorActual().getJugadas());
        return jugadasOut;
    }
    public List<IJugada> getAllJugadas(){
        List<IJugada> jugadasOut = new ArrayList<>(this.juego.getAllJugadas());
        return jugadasOut;
    }
    public void estaPreparado() {
        this.vista.setEstado(EstadoVista.ESPERANDO_JUGADORES);
        this.juego.JugadorPreparado(this.nombre);
    }
    public boolean faltanJugadoes() {
        return this.juego.faltanJugadores();
    }
    public void tomarCartaPozo() {
        this.juego.tomarDelPozo();//this.jugador
    }
    public void tirarCartaAlPozo(int indice) {
        Carta carta = this.juego.getJugadorActual().getMano().getCartas().get(indice);
        this.juego.tirarCartaPozo(carta);
    }
    public void tomarCartaMazo() {
        this.juego.tomarDelMazo();
    }
    public void verificarJugadas() {
        this.juego.verificarJugadas();
    }
    public void agregarJugador(String nombre) {
        Jugador j = new Jugador(nombre,this);
        this.juego.agregarJugador(j);
    }
    public void armarJugada(int[] indices) {
        List<Carta> cartasJugada = new ArrayList<>();
        List<Carta> cartasMano = this.juego.getJugadorActual().getMano().getCartas();
        for (int index : indices) {
            cartasJugada.add(cartasMano.get(index));
        }
        this.juego.armarJugada(cartasJugada);
    }
    public IRonda getRonda(){
        return this.juego.getRondaActual();
    }
    public IConjuntoCartas getPozo() {
        return this.juego.getPozo();
    }
}