package programa.modelo.verificadores;
import programa.modelo.jugador.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.commons.TipoCarta;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import java.util.List;
public abstract class VerificarJugada {
    private Formacion forma;
    public abstract Jugada formarJugada(List<Carta> cartas, Jugador jugador);
    public VerificarJugada(Formacion forma){
        this.forma = forma;
    }
    private int cantJokers(List<Carta> cartas) {
        int contador = 0;
        for (Carta carta:cartas) {
            if (carta.getTipo() == TipoCarta.JOKER) {
                contador ++;
            }
        }
        return contador;
    }
    protected boolean verificarListaCartas(List<Carta> cartas) {
        // Solo se devuelve verdadero si la cantidad de cartas es la misma que la cantidad de la formacion, y si tiene 1 joker o menos
        return (cartas.size() == this.forma.getCantCartas()) && (cartas.size() != 0) && (cantJokers(cartas) <= 1);
    }
}