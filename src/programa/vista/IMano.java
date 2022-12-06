package programa.vista;

import programa.modelo.conjuntoCarta.Carta;

import java.util.List;

public interface IMano {
    String mostrarCartas();
    int getCantidadCartas();
    List<Carta> getCartas();
}