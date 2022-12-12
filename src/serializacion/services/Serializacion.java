package serializacion.services;
import programa.modelo.Jugador;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Serializacion {
    private int tamanioTop;
    private static Serializador serializador = new Serializador("datos.dat");
    public Serializacion(int tamanioTop) {
        this.tamanioTop = tamanioTop;
    }
    public void GuardarNuevosJugadores(List<Jugador> jugadoresNuevos) {
        List<Jugador> jugadoresYaGuardados = recuperarTop();
        if (jugadoresYaGuardados == null) {almacenarJugadores(jugadoresNuevos);}
        //else if (!hayQueReescribirElArchivo(jugadoresNuevos)) {appendJugadoresAlTop(jugadoresNuevos);}
        else {insertarJugadoresAlTop(jugadoresNuevos);}
    }
    private boolean hayQueReescribirElArchivo(List<Jugador> jugadoresNuevos) {
        List<Jugador> jugadoresGuardados = recuperarTop();
        Jugador jugadorGuardadoMenorPuntaje = jugadoresGuardados.get(jugadoresGuardados.size()-1);
        for (Jugador jugadorActual:jugadoresNuevos) {
            if (jugadorActual.getPuntaje() > jugadorGuardadoMenorPuntaje.getPuntaje()) {
                return true;
            }
        }
        return false;
    }
    private void almacenarJugadores(List<Jugador> jugadores) {
        serializador = new Serializador("datos.dat");
        if (jugadores.size() >= 1) {
            serializador.writeOneObject(jugadores.get(0));
            for (int x = 1; x < jugadores.size(); x++) {
                serializador.addOneObject(jugadores.get(x));
            }
        }
    }
    private List<Jugador> ordenarJugadoresNuevos(List<Jugador> jugadores) {
        List<Jugador> retorno = new ArrayList<>();
        for (int i = 0; i < jugadores.size(); i++) {
            int mayorPuntaje = -1;
            Jugador jugadorMayorPuntaje = jugadores.get(0);
            for (Jugador jugador: jugadores) {
                if (jugador.getPuntaje() >= mayorPuntaje) {
                    mayorPuntaje = jugador.getPuntaje();
                    jugadorMayorPuntaje = jugador;
                }
            }
            retorno.add(jugadorMayorPuntaje);
            jugadores.remove(jugadorMayorPuntaje);
        }
        return retorno;
    }
    private void appendJugadoresAlTop(List<Jugador> jugadoresNuevos) {
        int cantidadJugadoresGuardados = recuperarTop().size();
        jugadoresNuevos = ordenarJugadoresNuevos(jugadoresNuevos);
        while (cantidadJugadoresGuardados < tamanioTop) {
            for (Jugador jugadorNuevo : jugadoresNuevos) {
                serializador.addOneObject(jugadorNuevo);
            }
        }
    }
    private void insertarJugadoresAlTop(List<Jugador> jugadoresNuevos) {
        List<Jugador> jugadoresGuardados = recuperarTop();
        for (Jugador jugadorAgregar : jugadoresNuevos) {
            boolean agregado = false;
            int i = 0;
            while ((i < jugadoresGuardados.size()) && (!agregado)) {
                if (jugadorAgregar.getPuntaje() > jugadoresGuardados.get(i).getPuntaje()) {
                    jugadoresGuardados.add(i, jugadorAgregar);
                    agregado = true;
                }
                i++;
            }
            if ((!agregado) && (jugadoresGuardados.size() < tamanioTop)) {
                jugadoresGuardados.add(jugadorAgregar);
            }
        }
        if (jugadoresGuardados.size() > tamanioTop) {
            jugadoresNuevos = jugadoresGuardados.subList(0, tamanioTop - 1);
        }
        else {jugadoresNuevos = jugadoresGuardados;}
        almacenarJugadores(jugadoresNuevos);
    }
    public List<Jugador> recuperarTop() {
        Object[] objects = serializador.readObjects();
        if (objects == null) return null;
        else {
            List<Jugador> retorno = new ArrayList<>();
            for (Object o: objects) {
                retorno.add((Jugador) o);
            }
            return retorno;
            //return new ArrayList<>(Arrays.asList((Jugador[]) objects));
        }
    }
}