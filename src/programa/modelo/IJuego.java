package programa.modelo;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import programa.controlador.Evento;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.Mano;
import programa.modelo.conjuntoCarta.Pozo;
import programa.modelo.conjuntoCarta.jugadas.Jugada;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
public interface IJuego extends Remote, IObservableRemoto{
    void agregarCartaAJugada(Jugada jugada, Carta carta, boolean alFinal) throws RemoteException;
    Ronda getRondaActual() throws RemoteException;
    boolean nombreValido(String nombre) throws RemoteException;
    boolean faltanJugadores() throws RemoteException;
    void deshacerJugadas() throws RemoteException;
    void verificarJugadas() throws RemoteException;
    void allJugadoresNotificar(Evento evento) throws RemoteException;
    List<Jugada> getAllJugadas() throws RemoteException;
    void armarJugada(List<Carta> cartas) throws RemoteException;
    Jugada armandoJugada(List<Carta> cartas) throws RemoteException;
    void agregarJugador(Jugador j) throws RemoteException;
    Pozo getPozo() throws RemoteException;
    void tomarDelPozo() throws RemoteException;
    void tirarCartaPozo(Carta carta) throws RemoteException;
    void tomarDelMazo() throws RemoteException;
    Jugador getJugadorActual() throws RemoteException;
    Jugador getJugador(String nombre) throws RemoteException;
    void JugadorPreparado(String nombre) throws RemoteException;
    void resetearJugadores() throws RemoteException;
}
