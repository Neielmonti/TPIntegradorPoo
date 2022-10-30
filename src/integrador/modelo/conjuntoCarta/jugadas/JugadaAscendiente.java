package integrador.modelo.conjuntoCarta.jugadas;

import integrador.modelo.commons.Formacion;
import integrador.modelo.commons.TipoCarta;
import integrador.modelo.conjuntoCarta.Carta;

import java.util.List;

public abstract class JugadaAscendiente extends Jugada{

    public JugadaAscendiente(Formacion forma, List<Carta> cartas) {
        super(forma, cartas);
    }

    private Carta buscarMayor() {
        List <Carta> cartas = this.getCartas();
        if (cartas.size() > 0) {
            Carta mayorCarta = cartas.get(0);
            for (Carta carta : cartas) {
                if (carta.getTipo().ordinal() > mayorCarta.getTipo().ordinal()) {
                    mayorCarta = carta;
                }
            }
            return mayorCarta;
        }
        else return null;
    }

    private Carta buscarMenor() {
        List <Carta> cartas = this.getCartas();
        if (cartas.size() > 0) {
            Carta menorCarta = cartas.get(0);
            for (Carta carta : cartas) {
                if (carta.getTipo().ordinal() < menorCarta.getTipo().ordinal()) {
                    menorCarta = carta;
                }
            }
            return menorCarta;
        }
        else return null;
    }

    @Override
    public boolean agregarCarta(Carta carta) {
        Carta menorCarta = buscarMenor();
        Carta mayorCarta = buscarMayor();
        if (carta.getTipo() == TipoCarta.JOKER) {
            if ((menorCarta.getTipo() == TipoCarta.A.getMenorTipo()) &&
                    (mayorCarta.getTipo() == TipoCarta.A.getMayorTipo())) {
                return false;
            }
            else return super.agregarCarta(carta);
        }
        if ((carta.getTipo().ordinal() > mayorCarta.getTipo().ordinal()) ||
                (carta.getTipo().ordinal() < menorCarta.getTipo().ordinal())) {
            return super.agregarCarta(carta);
        }
        else return false;
    }
}
