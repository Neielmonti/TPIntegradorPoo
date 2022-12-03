package programa.modelo;
import programa.controlador.Controlador;
import programa.controlador.Evento;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.Mano;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import programa.utils.observer.IObservable;
import programa.utils.observer.IObservador;
import programa.vista.IJugador;
import java.util.ArrayList;
import java.util.List;
public class Jugador implements IJugador, IObservable {
    private String nombre;
    private int puntaje = 0;
    private Controlador controlador;
    private boolean bajo = false;
    private boolean preparado = false;
    private List<Jugada> jugadas = new ArrayList<>();
    private List<IObservador> observadores = new ArrayList<>();
    private Mano mano;
    public Jugador(String nombre, Controlador controlador) {
        this.nombre = nombre;
        this.controlador = controlador;
        agregadorObservador(controlador);
    }
    public int getPuntaje() {
        return this.puntaje;
    }
    public boolean yaBajo() {
        return this.bajo;
    }
    public void bajar() {
        this.bajo = true;
    }
    public void actualizarPuntaje() {
        this.puntaje += this.mano.calcularPuntosCartas();
    }
    public Mano tomarMano(){
        Mano m = this.mano;
        this.mano = null;
        return m;
    }
    public void deshacerJugadas() {
        if (mano != null) {
            for (Jugada jugada: jugadas) {
                List<Carta> cartas = jugada.getCartas();
                jugada.pasarCartas(mano);
            }
            this.jugadas = new ArrayList<>();
        }
    }
    public void resetearJugador() {
        this.bajo = false;
        deshacerJugadas();
        this.preparado = false;
    }
    public Mano getMano(){
        return this.mano;
    }
    public String getNombre(){
        return this.nombre;
    }
    public void setMano(Mano mano){
        this.mano = mano;
    }
    public List<Jugada> getJugadas(){
        return this.jugadas;
    }
    public void addJugada(Jugada jugada) {this.jugadas.add(jugada);}
    public void estaPreparado() {this.preparado = true;}
    public boolean getPreparado() {return this.preparado;}
    @Override
    public void notificar(Evento evento) {
        for (IObservador observador: observadores) {
            observador.actualizar(evento,this);
        }
    }
    @Override
    public void agregadorObservador(IObservador observador) {
        this.observadores.add(observador);
    }
}
