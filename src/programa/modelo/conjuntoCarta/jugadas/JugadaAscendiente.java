package programa.modelo.conjuntoCarta.jugadas;
import programa.modelo.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.commons.TipoCarta;
import programa.modelo.conjuntoCarta.Carta;
import java.util.List;
public abstract class JugadaAscendiente extends Jugada{
    public JugadaAscendiente(Formacion forma, List<Carta> cartas, Jugador jugador) {
        super(forma, cartas, jugador);
    }
    protected boolean esSiguiente(Carta c1, Carta siguiente) {
        return siguiente.getTipo() == c1.getTipo().getNext();
    }
    protected boolean esAnterior(Carta c1, Carta anterior) {
        return anterior.getTipo() == c1.getTipo().getPrevius();
    }
    @Override
    public boolean agregarCarta(Carta carta, boolean alFinal) {
        List<Carta> cartas = getCartas();
        if (alFinal) {
            Carta cartaFinal = cartas.get(cartas.size()-1);
            if (cartaFinal.getTipo() == TipoCarta.JOKER) {
                cartaFinal = generarCartaAux(cartas.get(cartas.size()-2), true);
                if (cartaFinal == null) return false;
            }
            if ((this.esSiguiente(cartaFinal,carta)) ||
                    ((carta.getTipo() == TipoCarta.JOKER) && ((cartas.get(cartas.size()-1).getTipo() != TipoCarta.JOKER)))
                            && (cartas.get(cartas.size()-1).getTipo() != TipoCarta.getMayorTipo())) {
                agregarCarta(carta);
                return true;
            }
        }
        else {
            Carta cartaPrincipio = cartas.get(0);
            if (cartaPrincipio.getTipo() == TipoCarta.JOKER) {
                cartaPrincipio = generarCartaAux(cartas.get(1), false);
                if (cartaPrincipio == null) return false;
            }
            if ((this.esAnterior(cartaPrincipio,carta)) ||
                    ((carta.getTipo() == TipoCarta.JOKER) && ((cartas.get(0).getTipo() != TipoCarta.JOKER)))
                            && (cartas.get(0).getTipo() != TipoCarta.getMenorTipo())){
                agregarCartaPrincipio(carta);
                return true;
            }
        }
        return false;
    }
    protected Carta generarCartaAux(Carta carta, boolean siguiente) {
        if (siguiente) {
            if (carta.getTipo().getNext() != null) {
                return new Carta(carta.getPalo(),carta.getTipo().getNext());
            }
            else return null;
        }
        else {
            if (carta.getTipo().getPrevius() != null) {
                return new Carta(carta.getPalo(),carta.getTipo().getPrevius());
            }
            else return null;
        }
    }
}