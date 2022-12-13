package programa.modelo;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
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
import serializacion.services.Serializacion;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
public class Juego extends ObservableRemoto implements IJuego, Serializable{
    private Queue<Jugador> jugadores = new LinkedList<>();
    private final int maxJugadores = 4;
    private final int minJugadores = 2;
    private Queue<Ronda> rondas = new LinkedList();
    private Mazo mazo = new Mazo();
    private Pozo pozo = new Pozo();
    private List<VerificarJugada> verificadoresJugada = new ArrayList<>();
    private Serializacion topLowscores = new Serializacion(10);
    private boolean onGame = false;
    public Juego(){
        //Se generan las rondas y los verificadores
        generarRondas();
        generarVerificadores();
    }
    private void pasarSiguienteJugador() throws RemoteException {
        //Se desencola y encola el jugador del frente
        Jugador aux = this.jugadores.remove();
        this.jugadores.add(aux);
        int i = 0;
        //Se sigue pasando de jugador hasta que el jugador del frente este preparado y tenga una mano (este jugando)
        // (El contador "i" es solo para evitar que recorra infinitamente la cola de jugadores de no encontrar un jugador jugando)
        while (((!jugadores.peek().getPreparado()) || (jugadores.peek().getMano() == null)) && (i < jugadores.size())) {
            aux = this.jugadores.remove();
            this.jugadores.add(aux);
        }
        //Una vez pasado el jugador, se notifica a los observadores
        notificarObservadores(Evento.CAMBIO_DE_JUGADOR);
    }
    private void pasarSiguienteRonda() throws RemoteException{
        //Cada vez que se pasa de ronda, significa que alguien gano, por lo que deben calcularse los puntajes,
        // reiniciar los jugadores, pasar las cartas al mazo, y notificar a los observadores
        Ronda aux = this.rondas.remove();
        this.rondas.add(aux);
        actualizarPuntajes();
        resetearJugadores();
        guardarJugadoresTop();
        resetMazo();
        onGame = false;
        notificarObservadores(Evento.RONDA_GANADA);
    }
    @Override
    public void agregarCartaAJugada(int indiceJugada,int indiceCarta, boolean alFinal) throws RemoteException {
        //Primero se verifica que la mano del jugador actual contenga la carta pasada
        Mano mano = jugadores.peek().getMano();
        Carta carta = mano.tomarCarta(indiceCarta);
        // Tambien se verifica que el indice de la jugada corresponda con alguna jugada existente
        if ((carta != null) && (indiceJugada >= 0) && (indiceJugada < getAllJugadas().size())) {
            Jugada jugada = getAllJugadas().get(indiceJugada);
            if (jugada.agregarCarta(carta, alFinal)) {
                // Si el jugador se quedo sin cartas, se pasa de ronda (el jugador gano)
                if (mano.isEmpty()) {
                    pasarSiguienteRonda();
                }
                // Caso contrario, se notifica de una mano actualizada, y de una jugada modificada
                else {
                    notificarObservadores(Evento.MANO_ACTUALIZADA);
                    notificarObservadores(Evento.JUGADA_MODIFICADA);
                }
            }
            // Si la descarga no pudo realizarse, se notifica que la descarga fue rechazada
            else {
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
        // Se verifica que el nombre pasado por parametro no corresponda a ningun jugador
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
        if (jugadores.peek() != null) {
            if ((!jugadores.peek().yaBajo()) && (this.rondas.peek().verificarJugadasxRonda(jugadores.peek()))) {
                // Si el jugador actual aun no bajo, y sus jugadas coincides con las pedidas en la ronda, se lo baja y se notifica a todos
                jugadores.peek().bajar();
                notificarObservadores(Evento.JUGADOR_BAJO);
            } else {
                // Caso contrario, se notifica que sus jugadas fueron rechazadas (no puede bajarse)
                notificarObservadores(Evento.BAJADA_RECHAZADA);
            }
        }
    }
    @Override
    public List<Jugada> getAllJugadas() throws RemoteException {
        // Se devuelven todas las jugadas (de todos los jugadores) en forma de lista
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
        // Se forma una lista tomando las cartas que corresponden con los indices pasados por parametro
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
        }
    }
    private void generarVerificadores() {
        // Solo se ejecuta si los verificadores no fueron agregados a la lista de verificadores
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
            //this.rondas.add(new Ronda(Formacion.ESCALA,1));// ESTE ES SOLO PARA PRUEBAS
            //this.rondas.add(new Ronda(Formacion.ESCALA,1));// ESTE ES SOLO PARA PRUEBAS
            //this.rondas.add(new Ronda(Formacion.ESCALA,1));// ESTE ES SOLO PARA PRUEBAS
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
        // Se pasan todas las cartas de las manos de todos los jugadores al mazo
        for(Jugador jugador:this.jugadores) {
            Mano manoActual = jugador.tomarMano();
            if (manoActual != null) {
                manoActual.pasarCartas(this.mazo);
            }
        }
        // tambien se pasan todas las cartas del pozo al mazo
        pozo.pasarCartas(this.mazo);
    }

    private void repartirCartas() {
        resetMazo();
        for(Jugador jugador:this.jugadores) {
            if (jugador.getPreparado()) {jugador.setMano(this.mazo.formarMano());}
        }
        this.pozo.pasarCartas(this.mazo);
        this.pozo.agregarCarta(this.mazo.tomarCarta());
    }
    /**
    // PRUEBITA
    public void repartirCartas(){
        this.resetMazo();
        List<Carta> cartas;
        for (Jugador jugador: jugadores) {
            cartas = new ArrayList<>();
            cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.NUEVE));
            cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.DIEZ));
            cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.J));
            cartas.add(new Carta(PaloCarta.JOKER, TipoCarta.JOKER));
            cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.K));
            cartas.add(new Carta(PaloCarta.CORAZONES, TipoCarta.Q));
            jugador.setMano(new Mano(cartas));
        }
        this.pozo.pasarCartas(this.mazo);
        this.pozo.agregarCarta(this.mazo.tomarCarta());
    }**/
    @Override
    public void quitarJugador(String nombre, IControladorRemoto controlador) throws RemoteException{
        if (!jugadores.isEmpty()) {
            // Primero se corrobora si el jugador a quitar es el que tiene actualmente el turno
            boolean eliminoJugadorActual = jugadores.peek().getNombre().equals(nombre);
            boolean eliminado = false;
            for (int i = 0; i < jugadores.size(); i++) {
                Jugador jugador = jugadores.remove();
                if (!jugador.getNombre().equals(nombre)) {
                    // Si el jugador quitado NO tiene el nombre del jugador a eliminar, se lo vuelve a agregar a la cola
                    jugadores.add(jugador);
                } else {
                    // Caso contrario, se le quitan todas las cartas, y NO se lo vuelve a agregar a la cola
                    eliminado = true;
                    jugador.resetearJugador();
                    if (jugador.getMano() != null) {
                        jugador.getMano().pasarCartas(this.mazo);
                    }
                    i++;
                }
            }
            if (eliminado) {
                // Si el jugador fue eliminado, se quita al observador (controlador) de la lista de observadores
                removerObservador(controlador);
                if (cantidadJugadoresJugando() == 1) {
                    // Si solo queda un jugador (jugando), este gana automaticamente
                    pasarSiguienteRonda();
                }
                else if ((eliminoJugadorActual) && (cantidadJugadoresJugando() > 1) && (onGame)) {
                    // Si se elimino al jugador que tenia el turno, hay jugadores jugando, y se esta jugando una ronda, se cambia de jugador
                    notificarObservadores(Evento.CAMBIO_DE_JUGADOR);
                }
                // De no cumplirse con nada de lo anterior, significa que no quedan jugadores jugando.
                // Si aun asi hay jugadores (los cuales no estan jugando), se verifica que todos esten preparados (de esta forma empezaria otra
                // ronda)
                else if (!jugadores.isEmpty()) {verificarJugadoresEstanPreparados();}
            }
        }
    }
    private int cantidadJugadoresJugando() {
        int contador = 0;
        for (Jugador jugador:jugadores) {
            // Si el jugador tiene una mano, significa que esta jugando
            if (jugador.getMano() != null) {
                contador++;
            }
        }
        return contador;
    }
    @Override
    public Pozo getPozo() {
        return this.pozo;
    }
    @Override
    public void tomarDelPozo() throws RemoteException {
        if (jugadores.peek() != null) {
            this.jugadores.peek().getMano().agregarCarta(this.pozo.tomarCarta());
            if (this.pozo.isEmpty()) {
                // Si el pozo se vacio, se le pasa una carta del mazo
                this.pozo.agregarCarta(this.mazo.tomarCarta());
            }
            notificarObservadores(Evento.POZO_ACTUALIZADO);
            notificarObservadores(Evento.MANO_ACTUALIZADA);
        }
    }
    @Override
    public void tirarCartaPozo(int indice) throws RemoteException {
        if (jugadores.peek() != null) {
            Carta carta = jugadores.peek().getMano().tomarCarta(indice);
            // Se le quita la carta correspondiente al indice de la mano del jugador
            if (carta != null) {
                // De encontrar la carta, se agrega al pozo
                this.pozo.agregarCarta(carta);
                // Si el jugador tiro una carta al pozo, tiene jugadas, y NO se bajo, las jugadas se deshacen
                if ((jugadores.peek().getJugadas().size() > 0) && (!jugadores.peek().yaBajo())) {
                    jugadores.peek().deshacerJugadas();
                }
                // Si el jugador se quedo sin cartas, se pasa a la siguiente ronda (gano)
                if (jugadores.peek().getMano().isEmpty()) {
                    pasarSiguienteRonda();
                }
                // Caso contrario, simplemente se pasa al siguiente jugador
                else pasarSiguienteJugador();
            }
        }
    }
    @Override
    public void tomarDelMazo() throws RemoteException {
        if (jugadores.peek() != null) {
            // Se le agrega una carta a la mano del jugador (la misma fue tomada del mazo)
            jugadores.peek().getMano().agregarCarta(this.mazo.tomarCarta());
            if (this.mazo.isEmpty()) {
                // Si el mazo esta vacio, se le pasan todas las cartas del pozo, y luego se tira una carta al pozo
                pozo.pasarCartas(this.mazo);
                this.pozo.agregarCarta(this.mazo.tomarCarta());
            }
            notificarObservadores(Evento.MANO_ACTUALIZADA);
        }
    }
    @Override
    public Jugador getJugadorActual() {
        return this.jugadores.peek();
    }
    @Override
    public Jugador getJugador(String nombre) {
        for (Jugador jugador: jugadores) {
            // Se devuelve el jugador que tenga el nombre pasado por parametro
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
        // Se verifica si hay algun jugador que no este preparado
        for (Jugador jugador: jugadores) {
            if (!jugador.getPreparado()) {
                todosListos = false;
                break;
            }
        }
        // Si estan todos listos, se cumple la cantidad minima de jugadores, y no se esta jugando actualmente,
        // se inicia el juego
        if ((todosListos) && (jugadores.size() >= this.minJugadores) && (!onGame)){
            repartirCartas();
            onGame = true;
            notificarObservadores(Evento.CAMBIO_DE_JUGADOR);
        }
    }
    @Override
    public void resetearJugadores() {
        for (Jugador jugador: jugadores) {
            jugador.resetearJugador();
        }
    }
    private void guardarJugadoresTop() {
        Jugador jugadorActual = jugadores.peek();
        List<Jugador> perdedores = new ArrayList<>();
        // Se guardan en "perdedores" a todos los jugadores que no sean el actual (es decir, el ganador),
        // y que ademas tengan una mano (es decir, estaban jugando)
        for (Jugador jugador: jugadores) {
            if ((jugador != jugadorActual) && (jugador.getMano() != null)) {
                perdedores.add(jugador);
            }
        }
        // Si hay perdedores, se los guarda en el top (hay casos donde no hay perdedores)
        if (!perdedores.isEmpty()) {topLowscores.GuardarNuevosJugadores(perdedores);}
    }
    @Override
    public List<Jugador> getTopLowscores() throws RemoteException {
        return this.topLowscores.recuperarTop();
    }
    @Override
    public boolean getOnGame(){
        return this.onGame;
    }
}