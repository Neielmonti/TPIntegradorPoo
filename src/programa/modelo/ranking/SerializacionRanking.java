package programa.modelo.ranking;
import programa.modelo.jugador.Jugador;
import serializacion.services.Serializador;

import java.util.List;
public class SerializacionRanking {
    private static Serializador serializador = new Serializador("datos.dat");
    public void GuardarNuevosJugadores(List<Jugador> jugadoresNuevos) {
        Ranking rankingViejo = recuperarRanking();
        if (rankingViejo == null) {
            almacenarRanking(new Ranking(jugadoresNuevos));}
        else {insertarJugadoresAlTop(jugadoresNuevos);}
    }
    private void almacenarRanking(Ranking ranking) {
        serializador = new Serializador("datos.dat");
        serializador.writeOneObject(ranking);
    }
    private void insertarJugadoresAlTop(List<Jugador> jugadores) {
        Ranking rankingViejo = recuperarRanking();
        rankingViejo.insertarJugadoresAlTop(jugadores);
        almacenarRanking(rankingViejo);
    }
     public Ranking recuperarRanking() {
     Object object = serializador.readFirstObject();
     if (object == null) return null;
     else return (Ranking) object;
     }
}