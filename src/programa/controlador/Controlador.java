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
                    Jugador j = this.juego.getJugador(this.nombre);
                    if (j == null) {
                        break;
                    }
                    if (j.getMano() == null) {
                        break;
                    }
                    vista.clearMemo();
                    vista.mostrarRonda();
                    if (this.juego.getJugador(this.nombre) != null) {
                        if (this.juego.getJugador(this.nombre).getMano() != null) {
                            this.vista.setManoActual(this.juego.getJugador(this.nombre).getMano());
                            vista.mostrarMano();
                        }
                    }
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
                        vista.clearMemo();
                        vista.mostrarRonda();
                        vista.mostrarMano();
                        vista.mostrarAllJugadas();
                        this.vista.printError(ErrorVista.JUGADA_RECHAZADA);
                        vista.setEstado(EstadoVista.BAJAR);
                    }
                }
                case JUGADOR_BAJO, JUGADA_MODIFICADA -> {
                    this.vista.clearMemo();
                    if (!this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        this.vista.mostrarRonda();
                    }
                    this.vista.mostrarMano();
                    this.vista.mostrarAllJugadas();
                    if (this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                    }
                }
                case BAJADA_RECHAZADA -> {
                    if (this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        this.vista.bajadaRechazada();
                        this.vista.printError(ErrorVista.BAJADA_RECHAZADA);
                        this.vista.setEstado(EstadoVista.TIRAR_O_BAJAR);
                        this.juego.deshacerJugadas();
                    }
                }
                case DESCARGA_RECHAZADA -> {
                    if (this.juego.getJugadorActual().getNombre().equals(this.nombre)) {
                        this.vista.clearMemo();
                        this.vista.setManoActual(this.juego.getJugador(this.nombre).getMano());
                        vista.mostrarMano();
                        vista.mostrarAllJugadas();
                        vista.printError(ErrorVista.DESCARGA_INVALIDA);
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
    public void quitarJugador() {
        try {
            if (this.juego != null) {
                this.juego.quitarJugador(this.nombre, this);
            }
        }
        catch (RemoteException e) {
            vista.printError(ErrorVista.CONEXION);
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
    public boolean jugadorYaBajo() throws RemoteException {
        return this.juego.getJugador(this.nombre).yaBajo();
    }
    public void agregarCartaJugada(int indiceJugada, int indiceCarta, boolean alFinal) throws RemoteException {
        int cantidadJugadas = this.juego.getAllJugadas().size();
        int cantidadCartas = this.juego.getJugadorActual().getMano().getCantidadCartas();
        if ((indiceCarta >= 0) && (indiceCarta < cantidadCartas) && (indiceJugada >= 0) && (indiceJugada < cantidadJugadas)) {
            this.juego.agregarCartaAJugada(indiceJugada,indiceCarta,alFinal);
        }
        else {
            this.vista.printError(ErrorVista.DESCARGA_INVALIDA);
            this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
        }
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
    public boolean faltanJugadores() {
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
            int cantidadCartasMano = this.juego.getJugadorActual().getMano().getCantidadCartas();
            if ((indice >= 0) && (indice < cantidadCartasMano)) {
                this.juego.tirarCartaPozo(indice);
            }
            else this.vista.printError(ErrorVista.FUERA_DE_RANGO);
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