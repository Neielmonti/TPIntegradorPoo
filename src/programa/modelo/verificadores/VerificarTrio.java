package programa.modelo.verificadores;
import programa.modelo.Jugador;
import programa.modelo.commons.TipoCarta;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import programa.modelo.conjuntoCarta.jugadas.JugadaTrio;
import java.util.List;
public class VerificarTrio extends VerificarJugada{
    public VerificarTrio() {
        super(Formacion.TRIO);
    }
    @Override
    public Jugada formarJugada(List<Carta> cartas, Jugador jugador) {
        if (verificarListaCartas(cartas)) {
            return new JugadaTrio(cartas, jugador);
        }
        else return null;
    }
    @Override
    protected boolean verificarListaCartas(List<Carta> cartas) {
        if (!super.verificarListaCartas(cartas)) {
            return false;
        }
        int i = 1;
        boolean armado = true;
        TipoCarta primero = cartas.get(0).getTipo();
        if (primero == TipoCarta.JOKER) {primero = cartas.get(cartas.size()-1).getTipo();}

        while ((i < cartas.size()) && (armado)) {
            if ((cartas.get(i).getTipo() != primero) && (cartas.get(i).getTipo() != TipoCarta.JOKER)) {
                armado = false;
            }
            i++;
        }
        return armado;
    }
}
