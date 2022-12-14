package programa.vista;
import programa.controlador.Controlador;
import programa.modelo.jugador.IJugador;
import programa.modelo.conjuntoCarta.IMano;

public interface IVista {
    void setControlador(Controlador controlador);
    void inicioGrafico();
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
    void mostrarRanking();
}