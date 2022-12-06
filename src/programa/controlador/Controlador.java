package programa.controlador;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import programa.modelo.IJuego;
import programa.modelo.Jugador;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import programa.vista.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
public class Controlador implements IControladorRemoto, Serializable{
    private IJuego juego;
    private String nombre;
    private IVista vista;
    public Controlador(IVista vista){
        super();
        this.vista = vista;
        this.vista.setControlador(this);
        this.vista.inicioGrafico();
    }
    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {
        if ((o instanceof Evento)) {
            Evento aux = (Evento) o;
            switch (aux) {
                case JUGADOR_AGREGADO -> {
                    this.vista.setEstado(EstadoVista.ESPERANDO_USUARIO);
                }
                case CAMBIO_DE_JUGADOR -> {
                    vista.clearMemo();
                    vista.mostrarRonda();
                    this.vista.setManoActual(this.juego.getJugador(this.nombre).getMano());
                    vista.mostrarMano();
                    if (this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        if (this.juego.getJugadorActual().yaBajo()) {
                            this.vista.mostrarAllJugadas();
                        }
                        vista.mostrarPozo();
                        this.vista.setEstado(EstadoVista.TOMAR_CARTA);
                    } else {
                        vista.mostrarPozo();
                        this.vista.setEstado(EstadoVista.ESPERANDO_TURNO);
                    }
                }
                case MANO_ACTUALIZADA -> {
                    if (this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        this.vista.clearMemo();
                        this.vista.mostrarRonda();
                        this.vista.setManoActual(this.juego.getJugador(this.nombre).getMano());
                        this.vista.mostrarMano();
                        if (this.juego.getJugador(this.nombre).yaBajo()) {
                            this.vista.mostrarAllJugadas();
                            this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                        } else this.vista.setEstado(EstadoVista.TIRAR_O_BAJAR);
                    }
                }
                case POZO_ACTUALIZADO -> {
                    if (!this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        vista.clearMemo();
                        vista.mostrarRonda();
                        vista.mostrarMano();
                    }
                    this.vista.mostrarPozo();
                }
                case JUGADA_ARMADA -> {
                    if (this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        this.vista.clearMemo();
                        this.vista.mostrarRonda();
                        this.vista.setManoActual(this.juego.getJugador(this.nombre).getMano());
                        this.vista.mostrarMano();
                        this.vista.mostrarJugadasJugador();
                        this.vista.setEstado(EstadoVista.BAJAR);
                    }
                }
                case JUGADA_RECHAZADA -> {
                    if (this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        this.vista.jugadaRechazada();
                    }
                }
                case JUGADOR_BAJO -> {
                    if (this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        this.vista.clearMemo();
                        this.vista.mostrarMano();
                        this.vista.mostrarAllJugadas();
                        this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                    } else this.vista.mostrarAllJugadas();
                }
                case BAJADA_RECHAZADA -> {
                    if (this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        this.vista.bajadaRechazada();
                        this.vista.setEstado(EstadoVista.TIRAR_O_BAJAR);
                        this.vista.printError(ErrorVista.BAJADA_RECHAZADA);
                        this.juego.deshacerJugadas();
                    }
                }
                case JUGADA_MODIFICADA -> {
                    if (!this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        vista.mostrarAllJugadas();
                    }
                }
                case DESCARGA_RECHAZADA -> {
                    if (this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        this.vista.setManoActual(this.juego.getJugador(this.nombre).getMano());
                        vista.mostrarMano();
                        vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                    }
                }
                case RONDA_GANADA -> {
                    this.vista.rondaGanada(this.juego.getJugador(this.nombre));
                    this.vista.setEstado(EstadoVista.ESPERANDO_USUARIO);
                }
            }
        }
    }
    public void deshacerJugadas() throws RemoteException{
        this.juego.deshacerJugadas();
    }
    public boolean nombreValido(String nombre) throws RemoteException {
        return (!nombre.trim().equals("")) && (this.juego.nombreValido(nombre.trim()));
    }
    public IJugador getGanador() throws RemoteException {
        return this.juego.getJugadorActual();
    }
    public List<Jugada> getJugadasJugador(){
        try {
            return this.juego.getJugador(nombre).getJugadas();
        }
        catch (RemoteException e) {
            this.vista.printError(ErrorVista.CONEXION);
        }
        return null;
    }
    public void agregarCartaJuego(int indiceJugada, int indiceCarta, boolean alFinal) throws RemoteException {
        Jugada jugada = this.juego.getAllJugadas().get(indiceJugada);
        Carta carta = this.juego.getJugadorActual().getMano().tomarCarta(indiceCarta);
        if ((jugada != null) && (carta != null)) {
            this.juego.agregarCartaAJugada(indiceJugada,indiceCarta,alFinal);
        }
        else this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
    }
    public List<IJugada> getAllJugadas() throws RemoteException {
        List<IJugada> jugadasOut = new ArrayList<>(this.juego.getAllJugadas());
        return jugadasOut;
    }
    public void estaPreparado() {
        try {
            this.vista.setEstado(EstadoVista.ESPERANDO_JUGADORES);
            this.juego.JugadorPreparado(this.nombre);
        }
        catch (RemoteException e) {
            this.vista.println("Sip, exactamente, tambien hay error cuando pones estoy listo");
            this.vista.printError(ErrorVista.CONEXION);
        }
    }
    public boolean faltanJugadoes() {
        try {
            return this.juego.faltanJugadores();
        }
        catch (RemoteException e) {
            this.vista.printError(ErrorVista.CONEXION);
        }
        return false;
    }
    public void tomarCartaPozo() {
        try {
            this.juego.tomarDelPozo();
        }
        catch (RemoteException e) {
            this.vista.printError(ErrorVista.CONEXION);
        }
    }
    public void tirarCartaAlPozo(int indice) {
        try {
            Carta carta = this.juego.getJugadorActual().getMano().getCartas().get(indice);
            this.juego.tirarCartaPozo(indice);
        }
        catch (RemoteException e) {
            this.vista.printError(ErrorVista.CONEXION);
        }
    }
    public void tomarCartaMazo() {
        try {
            this.juego.tomarDelMazo();
        }
        catch (RemoteException e) {
            this.vista.printError(ErrorVista.CONEXION);
        }
    }
    public void verificarJugadas() {
        try {
            this.juego.verificarJugadas();
        }
        catch (RemoteException e) {
            this.vista.printError(ErrorVista.CONEXION);
        }
    }
    public void agregarJugador(String nombre){
        try {
            Jugador j = new Jugador(nombre);
            this.nombre = nombre;
            this.vista.setEstado(EstadoVista.ESPERANDO_USUARIO);
            this.juego.agregarJugador(j);
        }
        catch (RemoteException e) {
            this.vista.printError(ErrorVista.CONEXION);
        }
    }
    public void armarJugada(int[] indices) {
        try {
            List<Carta> cartasJugada = new ArrayList<>();
            List<Carta> cartasMano = this.juego.getJugadorActual().getMano().getCartas();
            for (int index : indices) {
                cartasJugada.add(cartasMano.get(index));
            }
            this.juego.armarJugada(indices);
        }
        catch (RemoteException e) {
            this.vista.printError(ErrorVista.CONEXION);
        }
    }
    public IRonda getRonda(){
        try {
            return this.juego.getRondaActual();
        } catch (RemoteException e) {
            this.vista.printError(ErrorVista.CONEXION);
        }
        return null;
    }
    public IConjuntoCartas getPozo() {
        try {
            return this.juego.getPozo();
        }
        catch (RemoteException e) {
            this.vista.printError(ErrorVista.CONEXION);
        }
        return null;
    }
    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
        this.juego = (IJuego) modeloRemoto;
    }
}