package programa.modelo.conjuntoCarta;
import java.util.List;

public interface IMano {
    String mostrarCartas();
    int getCantidadCartas();
    List<Carta> getCartas();
}