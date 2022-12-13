package programa.modelo;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
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
    void agregarCartaAJugada(int indiceJugada, int indiceCarta, boolean alFinal) throws RemoteException;
    Ronda getRondaActual() throws RemoteException;
    boolean nombreValido(String nombre) throws RemoteException;
    boolean faltanJugadores() throws RemoteException;
    void deshacerJugadas() throws RemoteException;
    void verificarJugadas() throws RemoteException;
    List<Jugada> getAllJugadas() throws RemoteException;
    void armarJugada(int[] indices) throws RemoteException;
    Jugada armandoJugada(List<Carta> cartas) throws RemoteException;
    void agregarJugador(Jugador j) throws RemoteException;
    void quitarJugador(String nombre, IControladorRemoto controlador) throws RemoteException;
    Pozo getPozo() throws RemoteException;
    void tomarDelPozo() throws RemoteException;
    void tirarCartaPozo(int indice) throws RemoteException;
    void tomarDelMazo() throws RemoteException;
    Jugador getJugadorActual() throws RemoteException;
    Jugador getJugador(String nombre) throws RemoteException;
    void JugadorPreparado(String nombre) throws RemoteException;
    void resetearJugadores() throws RemoteException;
    List<Jugador> getTopLowscores() throws RemoteException;
    boolean getOnGame() throws RemoteException;
}
