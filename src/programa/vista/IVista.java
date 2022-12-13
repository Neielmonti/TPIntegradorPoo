package programa.vista;
import programa.controlador.Controlador;
public interface IVista {
    void setControlador(Controlador controlador);
    void inicioGrafico();
    void setManoActual(IMano mano);
    void mostrarJugadasJugador();
    void mostrarAllJugadas();
    void setEstado(EstadoVista estado);
    void rondaGanada(IJugador jugador);
    void mostrarMano();
    void mostrarPozo();
    void mostrarRonda();
    void clearMemo();
    void clearTextbox();
    void println(String texto);
    void printError(ErrorVista error);
    void mostrarTopLowscores();
}