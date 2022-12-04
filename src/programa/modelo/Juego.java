package programa.modelo;
import programa.controlador.Evento;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.Mano;
import programa.modelo.conjuntoCarta.Mazo;
import programa.modelo.conjuntoCarta.Pozo;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import programa.modelo.verificadores.*;
import programa.utils.observer.IObservable;
import programa.utils.observer.IObservador;
import java.util.*;
public class Juego implements IObservable {
    private Queue<Jugador> jugadores = new LinkedList<>();
    private int maxJugadores = 4;
    private int minJugadores = 2;
    private Queue<Ronda> rondas = new LinkedList();
    private Mazo mazo = new Mazo();
    private Pozo pozo = new Pozo();
    private List<IObservador> observadores = new ArrayList<>();
    private List<VerificarJugada> verificadoresJugada = new ArrayList<>();
    public Juego(){
        generarRondas();
        generarVerificadores();
    }
    public void pasarSiguienteJugador() {
        Jugador aux = this.jugadores.remove();
        this.jugadores.add(aux);
        allJugadoresNotificar(Evento.CAMBIO_DE_JUGADOR);
    }
    public void pasarSiguienteRonda() {
        Ronda aux = this.rondas.remove();
        this.rondas.add(aux);
        actualizarPuntajes();
        resetearJugadores();
        allJugadoresNotificar(Evento.RONDA_GANADA);
    }
    public void agregarCartaAJugada(Jugada jugada, Carta carta, boolean alFinal){
        Mano mano = jugadores.peek().getMano();
        //Primero se verifica que la mano del jugador actual contenga la carta pasada
        if (mano.getCartas().contains(carta)) {
            if (jugada.agregarCarta(carta, alFinal)) {
                // Si se pudo agregar la carta a la jugada, se le quita la carta al jugador
                mano.quitarCarta(carta);
                // Si el jugador se quedo sin cartas, se pasa de ronda (el jugador gano)
                if (mano.isEmpty()) {
                    pasarSiguienteRonda();
                } else {
                    jugadores.peek().notificar(Evento.MANO_ACTUALIZADA);
                    allJugadoresNotificar(Evento.JUGADA_MODIFICADA);
                }
            } else jugadores.peek().notificar(Evento.DESCARGA_RECHAZADA);
        }
    }
    public Ronda getRondaActual() {
        return this.rondas.peek();
    }
    public boolean nombreValido(String nombre) {
        for (Jugador jugador: jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                return false;
            }
        }
        return true;
    }
    public boolean faltanJugadores() {
        if (this.jugadores.size() < maxJugadores) {
            return true;
        }
        else return false;
    }
    public void deshacerJugadas() {
        // Se deshacen todas las jugadas del jugador actual
        jugadores.peek().deshacerJugadas();
        jugadores.peek().notificar(Evento.MANO_ACTUALIZADA);
    }
    public void verificarJugadas() {
        if ((!jugadores.peek().yaBajo()) && (this.rondas.peek().verificarJugadasxRonda(jugadores.peek()))) {
            // Si el jugador actual aun no bajo, y sus jugadas coincides con las pedidas en la ronda, se lo baja y se notifica a todos
            jugadores.peek().bajar();
            allJugadoresNotificar(Evento.JUGADOR_BAJO);
        }
        else {
            // Caso contrario, se le notifica solo a este jugador que sus jugadas fueron rechazadas (no puede bajarse)
            jugadores.peek().notificar(Evento.BAJADA_RECHAZADA);
        }
    }
    public void allJugadoresNotificar(Evento evento) {
        // Se utiliza en ocaciones donde la notificacion debe llegar a todos los jugadores, pero deben comprobar si es su turno
        for (Jugador jugador: jugadores) {
            jugador.notificar(evento);
        }
    }
    public List<Jugada> getAllJugadas() {
        // Se devuelven todas las jugadas de todos los jugadores en forma de lista
        List<Jugada> jugadas = new ArrayList<>();
        for (Jugador jugador: jugadores) {
            jugadas.addAll(jugador.getJugadas());
        }
        return jugadas;
    }
    public void armarJugada(List<Carta> cartas) {
        Jugada jugada = armandoJugada(cartas);
        if (jugada != null) {
            // Si se pudo formar una jugada, se le quitan las cartas de la mano del jugador
            jugadores.peek().getMano().quitarCartas(jugada);
            if (jugadores.peek().getMano().isEmpty()) {
                // Si el jugador se quedo sin cartas, se pasa de ronda (el jugador gano)
                pasarSiguienteRonda();
            }
            // Caso contrario se le avisa al jugador que la jugada fue armada
            else jugadores.peek().notificar(Evento.JUGADA_ARMADA);
        }
        // Si no se pudo formar la jugada, se le avisa al jugador
        else {jugadores.peek().notificar(Evento.JUGADA_RECHAZADA);}
    }
    private Jugada armandoJugada(List<Carta> cartas) {
        int i = 0;
        Jugada jugada = null;
        while ((i < verificadoresJugada.size()) && (jugada == null)) {
            jugada = verificadoresJugada.get(i).formarJugada(cartas,jugadores.peek());
            i++;
        }
        return jugada;
    }
    public void agregarJugador(Jugador j) {
        if ((!jugadores.contains(j)) && (jugadores.size() < this.maxJugadores)) {
            // Si el jugador es nuevo en el juego, y hay espacio, se lo agrega
            jugadores.add(j);
            j.notificar(Evento.JUGADOR_AGREGADO);
        }
    }
    private void generarVerificadores() {
        if (verificadoresJugada.isEmpty()) {
            this.verificadoresJugada.add(new VerificarEscaleraReal());
            this.verificadoresJugada.add(new VerificarEscaleraSucia());
            this.verificadoresJugada.add(new VerificarEscala());
            this.verificadoresJugada.add(new VerificarTrio());
        }
    }
    private void generarRondas(){
        if (this.rondas.isEmpty()) {
            List<CantXFormacion> listaAux;
            // En el caso de rondas complejas (de varios tipos de jugadas) se utiliza una lista auxiliar para crearlas
            //this.rondas.add(new Ronda(Formacion.ESCALA,1));// ESTE ES SOLO PARA PRUEBAS
            this.rondas.add(new Ronda(Formacion.TRIO,2));
            listaAux = new ArrayList<>();
            listaAux.add(new CantXFormacion(Formacion.TRIO,1));
            listaAux.add(new CantXFormacion(Formacion.ESCALA,1));
            this.rondas.add(new Ronda(listaAux));
            this.rondas.add(new Ronda(Formacion.ESCALA,2));
            this.rondas.add(new Ronda(Formacion.TRIO,3));
            listaAux = new ArrayList<>();
            listaAux.add(new CantXFormacion(Formacion.TRIO,2));
            listaAux.add(new CantXFormacion(Formacion.ESCALA,1));
            this.rondas.add(new Ronda(listaAux));
            listaAux = new ArrayList<>();
            listaAux.add(new CantXFormacion(Formacion.TRIO,1));
            listaAux.add(new CantXFormacion(Formacion.ESCALA,2));
            this.rondas.add(new Ronda(listaAux));
            this.rondas.add(new Ronda(Formacion.ESCALA,3));
            this.rondas.add(new Ronda(Formacion.TRIO,4));
            this.rondas.add(new Ronda(Formacion.ESCALERA_SUCIA,1));
            this.rondas.add(new Ronda(Formacion.ESCALERA_REAL,1));
        }
    }
    private void resetMazo(){
        for(Jugador jugador:this.jugadores) {
            Mano manoActual = jugador.getMano();
            if (manoActual != null) {
                manoActual.pasarCartas(this.mazo);
            }
        }
        pozo.pasarCartas(this.mazo);
    }
    public void repartirCartas(){
        this.resetMazo();
        for(Jugador jugador:this.jugadores) {
            jugador.setMano(this.mazo.formarMano());
        }
        this.pozo.pasarCartas(this.mazo);
        this.pozo.agregarCarta(this.mazo.tomarCarta());
    }
    /**
     // PRUEBITA
    public void repartirCartas(){
        this.resetMazo();
        List<Carta> cartas = new ArrayList<>();
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.NUEVE));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.DIEZ));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.J));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.JOKER));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.K));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.Q));
        jugadores.peek().setMano(new Mano(cartas));

        Jugador aux = jugadores.remove();
        jugadores.add(aux);

        cartas = new ArrayList<>();
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.NUEVE));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.DIEZ));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.J));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.JOKER));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.K));
        cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.Q));
        jugadores.peek().setMano(new Mano(cartas));
        this.pozo.agregarCarta(this.mazo.tomarCarta());
    }
    **/
    public Pozo getPozo() {
        return this.pozo;
    }
    public void tomarDelPozo() {
        this.jugadores.peek().getMano().agregarCarta(this.pozo.tomarCarta());
        if (this.pozo.isEmpty()) {
            this.pozo.agregarCarta(this.mazo.tomarCarta());
        }
        notificar(Evento.POZO_ACTUALIZADO);
        jugadores.peek().notificar(Evento.MANO_ACTUALIZADA);
    }
    public void tirarCartaPozo(Carta carta) {
        if (jugadores.peek().getMano() != null) {
            jugadores.peek().getMano().quitarCarta(carta);
            this.pozo.agregarCarta(carta);
            if (jugadores.peek().getMano().isEmpty()) {
                pasarSiguienteRonda();
            }
            else pasarSiguienteJugador();
        }
    }
    public void tomarDelMazo() {
//        jugador.getMano().agregarCarta(this.mazo.tomarCarta());
        jugadores.peek().getMano().agregarCarta(this.mazo.tomarCarta());
        if (this.mazo.isEmpty()) {
            pozo.pasarCartas(this.mazo);
            this.pozo.agregarCarta(this.mazo.tomarCarta());
        }
        jugadores.peek().notificar(Evento.MANO_ACTUALIZADA);
    }
    public Jugador getJugadorActual() {
        return this.jugadores.peek();
    }
    public Mano getManoJugador(){
        return jugadores.peek().getMano();
    }
    public void actualizarPuntajes() {
        for(Jugador jugador: this.jugadores) {
            jugador.actualizarPuntaje();
        }
    }
    public void JugadorPreparado(String nombre) {
        for (Jugador jugador: jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                jugador.estaPreparado();
                verificarJugadoresEstanPreparados();
                break;
            }
        }
    }
    private void verificarJugadoresEstanPreparados() {
        boolean todosListos = !jugadores.isEmpty();
        for (Jugador jugador: jugadores) {
            if (!jugador.getPreparado()) {
                todosListos = false;
                break;
            }
        }
        if ((todosListos) && (jugadores.size() >= this.minJugadores)){
            repartirCartas();
            allJugadoresNotificar(Evento.CAMBIO_DE_JUGADOR);
            //notificar(Evento.CAMBIO_DE_JUGADOR);
        }
    }
    private void resetearJugadores() {
        for (Jugador jugador: jugadores) {
            jugador.resetearJugador();
        }
    }
    @Override
    public void notificar(Evento evento) {
        for (IObservador observador : this.observadores) {
            observador.actualizar(evento, this);
        }
    }
    @Override
    public void agregadorObservador(IObservador observador) {
        this.observadores.add(observador);
    }
}