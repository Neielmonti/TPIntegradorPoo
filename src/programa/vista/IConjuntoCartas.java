package programa.vista;

import programa.modelo.conjuntoCarta.Carta;

import java.util.List;

public interface IConjuntoCartas {
    String mostrarCartas();
    List<Carta> getCartas();
}