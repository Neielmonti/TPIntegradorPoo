package programa.modelo;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import programa.controlador.Evento;
import programa.modelo.commons.Formacion;
import programa.modelo.commons.PaloCarta;
import programa.modelo.commons.TipoCarta;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.Mano;
import programa.modelo.conjuntoCarta.Mazo;
import programa.modelo.conjuntoCarta.Pozo;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import programa.modelo.verificadores.*;
import programa.utils.observer.IObservador;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
public class Juego extends ObservableRemoto implements IJuego, Serializable{
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
    private void pasarSiguienteJugador() throws RemoteException {
        Jugador aux = this.jugadores.remove();
        this.jugadores.add(aux);
        notificarObservadores(Evento.CAMBIO_DE_JUGADOR);
    }
    private void pasarSiguienteRonda() throws RemoteException{
        Ronda aux = this.rondas.remove();
        this.rondas.add(aux);
        actualizarPuntajes();
        resetearJugadores();
        notificarObservadores(Evento.RONDA_GANADA);
    }
    @Override
    public void agregarCartaAJugada(int indiceJugada,int indiceCarta, boolean alFinal) throws RemoteException {

        Mano mano = jugadores.peek().getMano();
        //Primero se verifica que la mano del jugador actual contenga la carta pasada
        Carta carta = mano.tomarCarta(indiceCarta);
        Jugada jugada = getAllJugadas().get(indiceJugada);

        System.out.println("Mano: \n" + mano.mostrarCartas() + "\n" + "Jugada: \n" + jugada.mostrarCartas() + "\nCarta:\n" + carta.mostrarCarta());/// <-----------------------

        if (carta != null) {
            if (jugada.agregarCarta(carta, alFinal)) {
                System.out.println("La jugada fue aceptada, con alFinal:" + alFinal);
                // Si se pudo agregar la carta a la jugada, se le quita la carta al jugador
                //mano.quitarCarta(carta);
                // Si el jugador se quedo sin cartas, se pasa de ronda (el jugador gano)
                if (mano.isEmpty()) {
                    pasarSiguienteRonda();
                } else {
                    notificarObservadores(Evento.MANO_ACTUALIZADA);
                    notificarObservadores(Evento.JUGADA_MODIFICADA);
                }
            } else {
                mano.agregarCarta(carta);
                notificarObservadores(Evento.DESCARGA_RECHAZADA);
            }
        }
    }
    @Override
    public Ronda getRondaActual() throws RemoteException {
        return this.rondas.peek();
    }
    @Override
    public boolean nombreValido(String nombre) throws RemoteException {
        for (Jugador jugador: jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean faltanJugadores() throws RemoteException {
        return this.jugadores.size() < maxJugadores;
    }
    @Override
    public void deshacerJugadas() throws RemoteException {
        // Se deshacen todas las jugadas del jugador actual
        jugadores.peek().deshacerJugadas();
        notificarObservadores(Evento.MANO_ACTUALIZADA);
    }
    @Override
    public void verificarJugadas() throws RemoteException {
        if ((!jugadores.peek().yaBajo()) && (this.rondas.peek().verificarJugadasxRonda(jugadores.peek()))) {
            // Si el jugador actual aun no bajo, y sus jugadas coincides con las pedidas en la ronda, se lo baja y se notifica a todos
            jugadores.peek().bajar();
            notificarObservadores(Evento.JUGADOR_BAJO);
        }
        else {
            // Caso contrario, se le notifica solo a este jugador que sus jugadas fueron rechazadas (no puede bajarse)
            notificarObservadores(Evento.BAJADA_RECHAZADA);
        }
    }
    @Override
    public List<Jugada> getAllJugadas() throws RemoteException {
        // Se devuelven todas las jugadas de todos los jugadores en forma de lista
        List<Jugada> jugadas = new ArrayList<>();
        for (Jugador jugador: jugadores) {
            jugadas.addAll(jugador.getJugadas());
        }
        return jugadas;
    }
    @Override
    public void armarJugada(int[] indices) throws RemoteException {
        List<Carta> cartasMano = jugadores.peek().getMano().getCartas();
        List<Carta> cartasJugada = new ArrayList<>();
        for (int index : indices) {
            cartasJugada.add(cartasMano.get(index));
        }
        Jugada jugada = armandoJugada(cartasJugada);
        if (jugada != null) {
            // Si se pudo formar una jugada, se le quitan las cartas de la mano del jugador
            jugadores.peek().getMano().quitarCartas(jugada);
            if (jugadores.peek().getMano().isEmpty()) {
                // Si el jugador se quedo sin cartas, se pasa de ronda (el jugador gano)
                pasarSiguienteRonda();
            }
            // Caso contrario se le avisa al jugador que la jugada fue armada
            else notificarObservadores(Evento.JUGADA_ARMADA);
        }
        // Si no se pudo formar la jugada, se le avisa al jugador
        else notificarObservadores(Evento.JUGADA_RECHAZADA);
    }
    @Override
    public Jugada armandoJugada(List<Carta> cartas) throws RemoteException {
        int i = 0;
        Jugada jugada = null;
        while ((i < verificadoresJugada.size()) && (jugada == null)) {
            jugada = verificadoresJugada.get(i).formarJugada(cartas,jugadores.peek());
            i++;
        }
        return jugada;
    }
    @Override
    public void agregarJugador(Jugador j) throws RemoteException {
        if ((!jugadores.contains(j)) && (jugadores.size() < this.maxJugadores)) {
            // Si el jugador es nuevo en el juego, y hay espacio, se lo agrega
            jugadores.add(j);
            //j.notificarObservadores(Evento.JUGADOR_AGREGADO);
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
    private void generarRondas() {
        if (this.rondas.isEmpty()) {
            List<CantXFormacion> listaAux;
            // En el caso de rondas complejas (de varios tipos de jugadas) se utiliza una lista auxiliar para crearlas
            this.rondas.add(new Ronda(Formacion.ESCALA,1));// ESTE ES SOLO PARA PRUEBAS
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
    /**
    private void repartirCartas() {
        resetMazo();
        for(Jugador jugador:this.jugadores) {
            jugador.setMano(this.mazo.formarMano());
        }
        this.pozo.pasarCartas(this.mazo);
        this.pozo.agregarCarta(this.mazo.tomarCarta());
    }
     **/

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
    @Override
    public Pozo getPozo() {
        return this.pozo;
    }
    @Override
    public void tomarDelPozo() throws RemoteException {
        this.jugadores.peek().getMano().agregarCarta(this.pozo.tomarCarta());
        if (this.pozo.isEmpty()) {
            this.pozo.agregarCarta(this.mazo.tomarCarta());
        }
        notificarObservadores(Evento.POZO_ACTUALIZADO);
        notificarObservadores(Evento.MANO_ACTUALIZADA);
    }
    @Override
    public void tirarCartaPozo(int indice) throws RemoteException {
        if (jugadores.peek().getMano() != null) {
            Carta carta = jugadores.peek().getMano().tomarCarta(indice);
            jugadores.peek().getMano().quitarCarta(carta);
            this.pozo.agregarCarta(carta);
            if (jugadores.peek().getMano().isEmpty()) {
                pasarSiguienteRonda();
            }
            else pasarSiguienteJugador();
        }
    }
    @Override
    public void tomarDelMazo() throws RemoteException{
//        jugador.getMano().agregarCarta(this.mazo.tomarCarta());
        jugadores.peek().getMano().agregarCarta(this.mazo.tomarCarta());
        if (this.mazo.isEmpty()) {
            pozo.pasarCartas(this.mazo);
            this.pozo.agregarCarta(this.mazo.tomarCarta());
        }
        notificarObservadores(Evento.MANO_ACTUALIZADA);
    }
    @Override
    public Jugador getJugadorActual() {
        return this.jugadores.peek();
    }
    @Override
    public Jugador getJugador(String nombre) {
        for (Jugador jugador: jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                return jugador;
            }
        }
        return null;
    }
    private void actualizarPuntajes() {
        for(Jugador jugador: this.jugadores) {
            jugador.actualizarPuntaje();
        }
    }
    @Override
    public void JugadorPreparado(String nombre) throws RemoteException {
        for (Jugador jugador: jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                jugador.estaPreparado();
                verificarJugadoresEstanPreparados();
                break;
            }
        }
    }
    private void verificarJugadoresEstanPreparados() throws RemoteException{
        boolean todosListos = !jugadores.isEmpty();
        for (Jugador jugador: jugadores) {
            if (!jugador.getPreparado()) {
                todosListos = false;
                break;
            }
        }
        if ((todosListos) && (jugadores.size() >= this.minJugadores)){
            repartirCartas();
            notificarObservadores(Evento.CAMBIO_DE_JUGADOR);
        }
    }
    @Override
    public void resetearJugadores() {
        for (Jugador jugador: jugadores) {
            jugador.resetearJugador();
        }
    }
}