package programa.modelo.verificadores;
import programa.modelo.Jugador;
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
        if ((cartas.size() != this.forma.getCantCartas()) || (cartas.size() == 0) || (cantJokers(cartas) > 1)) {
            return false;
        }
        else return true;
    }
}
