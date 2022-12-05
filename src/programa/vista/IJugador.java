package programa.vista;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import java.util.List;
public interface IJugador {
    String getNombre();
    int getPuntaje();
    List<Jugada> getJugadas();
}