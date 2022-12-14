package programa.modelo.ranking;
import programa.modelo.jugador.Jugador;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Ranking implements Serializable, IRanking{
    @Serial
    private static final long serialVersionUID = -8670989939852101449L;
    private List<Jugador> jugadores;
    private static final int tamanioRanking = 10;
    public Ranking(List<Jugador> jugadores) {
        this.jugadores = ordenarJugadoresNuevos(jugadores);
    }
    public void insertarJugadoresAlTop(List<Jugador> jugadoresNuevos) {
        List<Jugador> jugadoresGuardados = this.jugadores;
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
            if ((!agregado) && (jugadoresGuardados.size() < tamanioRanking)) {
                jugadoresGuardados.add(jugadorAgregar);
            }
        }
        if (jugadoresGuardados.size() > tamanioRanking) {
           this.jugadores = hacerListaMenordeDiez(jugadoresGuardados);
        }
        else this.jugadores = jugadoresGuardados;
    }
    private List<Jugador> ordenarJugadoresNuevos(List<Jugador> jugadores) {
        List<Jugador> retorno = new ArrayList<>();
        int cantidadJugadores = jugadores.size();
        for (int i = 0; i < cantidadJugadores; i++) {
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
        if (cantidadJugadores > tamanioRanking) return hacerListaMenordeDiez(retorno);
        else return retorno;
    }
    private List<Jugador> hacerListaMenordeDiez(List<Jugador> jugadores) {
        int max = jugadores.size();
        if (max > tamanioRanking) max = tamanioRanking;
        List<Jugador> retorno = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            retorno.add(jugadores.get(i));
        }
        return retorno;
    }
    @Override
    public String mostrarRanking() {
        String retorno = "---------RANKING PERDEDORES---------\n"
                + "|Jugador|       |Puntaje|\n";
        for (Jugador jugador: this.jugadores) {
            retorno += jugador.getNombre() + "   " + jugador.getPuntaje() + "\n";
        }
        return retorno;
    }
}