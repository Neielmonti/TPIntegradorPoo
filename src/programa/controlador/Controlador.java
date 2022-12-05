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
public class Controlador implements IControladorRemoto, Serializable {
    private IJuego juego;
    private String nombre;
    private VistaConsolaSwing vista;
    public Controlador(VistaConsolaSwing vista){
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
                    if (!(iObservableRemoto instanceof Jugador)) {
                        break;
                    }
                    Jugador j = (Jugador) (iObservableRemoto);
                    //this.nombre = j.getNombre();
                    this.vista.setEstado(EstadoVista.ESPERANDO_USUARIO);
                }
                case CAMBIO_DE_JUGADOR -> {
                    //vista.clearMemo();                 //<------------------------------------
                    System.out.println("Post clear");
                    vista.mostrarRonda();
                    System.out.println("Post mostrarRonda");
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
                    if (!(iObservableRemoto instanceof Jugador)) {
                        break;
                    }
                    Jugador j = (Jugador) (iObservableRemoto);
                    if (this.juego.getJugadorActual().getNombre().equals(j.getNombre())) {
                        this.vista.clearMemo();
                        this.vista.mostrarRonda();
                        this.vista.setManoActual(j.getMano());
                        this.vista.mostrarMano();
                        if (j.yaBajo()) {
                            this.vista.mostrarAllJugadas();
                            this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                        } else this.vista.setEstado(EstadoVista.TIRAR_O_BAJAR);
                    }
                }
                case POZO_ACTUALIZADO -> this.vista.mostrarPozo();
                case JUGADA_ARMADA -> {
                    if (!(iObservableRemoto instanceof Jugador)) {
                        break;
                    }
                    Jugador j = (Jugador) (iObservableRemoto);
                    if (this.juego.getJugadorActual().getNombre().equals(j.getNombre())) {
                        this.vista.clearMemo();
                        this.vista.mostrarRonda();
                        this.vista.mostrarMano();
                        this.vista.mostrarJugadasJugador(j);
                        this.vista.setEstado(EstadoVista.BAJAR);
                    }
                }
                case JUGADA_RECHAZADA -> {
                    if (!(iObservableRemoto instanceof Jugador)) {
                        break;
                    }
                    Jugador j = (Jugador) (iObservableRemoto);
                    if (j == this.juego.getJugadorActual()) {
                        this.vista.jugadaRechazada();
                    }
                }
                case JUGADOR_BAJO -> {
                    if (!(iObservableRemoto instanceof Jugador)) {
                        break;
                    }
                    Jugador j = (Jugador) (iObservableRemoto);
                    if (this.juego.getJugadorActual().getNombre().equals(j.getNombre())) {
                        this.vista.clearMemo();
                        this.vista.mostrarMano();
                        this.vista.mostrarAllJugadas();
                        this.vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                    } else this.vista.mostrarAllJugadas();
                }
                case BAJADA_RECHAZADA -> {
                    if (!(iObservableRemoto instanceof Jugador)) {
                        break;
                    }
                    Jugador j = (Jugador) (iObservableRemoto);
                    if (this.juego.getJugadorActual().getNombre().equals(j.getNombre())) {
                        this.vista.bajadaRechazada();
                        this.vista.setEstado(EstadoVista.TIRAR_O_BAJAR);
                        this.juego.deshacerJugadas();
                    }
                }
                case JUGADA_MODIFICADA -> {
                    if (!(iObservableRemoto instanceof Jugador)) {
                        break;
                    }
                    Jugador j = (Jugador) (iObservableRemoto);
                    if (!this.juego.getJugadorActual().getNombre().equals(j.getNombre())) {
                        vista.mostrarAllJugadas();
                    }
                }
                case DESCARGA_RECHAZADA -> {
                    if (!(iObservableRemoto instanceof Jugador)) {
                        break;
                    }
                    Jugador j = (Jugador) (iObservableRemoto);
                    if (this.juego.getJugadorActual().getNombre().equals(j.getNombre())) {
                        this.vista.setManoActual(j.getMano());
                        vista.mostrarMano();
                        vista.setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                    }
                }
                case RONDA_GANADA -> {
                    if (!(iObservableRemoto instanceof Jugador)) {
                        break;
                    }
                    Jugador j = (Jugador) (iObservableRemoto);
                    this.vista.rondaGanada(j);
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
    public void agregarCartaJuego(int indiceJugada, int indiceCarta, boolean alFinal) throws RemoteException {
        Jugada jugada = this.juego.getAllJugadas().get(indiceJugada);
        Carta carta = this.juego.getJugadorActual().getMano().tomarCarta(indiceCarta);
        if ((jugada != null) && (carta != null)) {
            this.juego.agregarCartaAJugada(jugada,carta,alFinal);
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
            this.juego.tirarCartaPozo(carta);
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
            this.juego.armarJugada(cartasJugada);
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