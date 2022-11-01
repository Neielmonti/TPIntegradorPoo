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

    private boolean tieneJoker() {
        boolean result = false;
        List<Carta> cartas = this.getCartas();
        for (Carta carta:cartas) {
            if (carta.getTipo() == TipoCarta.JOKER) {
                result = true;
            }
        }
        return result;
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
        boolean result = false;
        List<Carta> cartas = this.getCartas();
        Carta menorCarta = cartas.get(0);
        Carta mayorCarta = cartas.get(cartas.size()-1);
        if (carta.getTipo() == TipoCarta.JOKER) {
            if (!this.tieneJoker()){
                if (mayorCarta.getTipo() != TipoCarta.getMayorTipo()) {
                    result = true;
                    super.agregarCarta(carta);
                }
                else if (menorCarta.getTipo() != TipoCarta.getMenorTipo()) {
                    result = true;
                    super.agregarCartaPrincipio(carta);
                }
            }
        }
        else {
            if (carta.getTipo() == mayorCarta.getTipo().getNext()) {
                result = true;
                super.agregarCarta(carta);
            }
            else if (carta.getTipo() == menorCarta.getTipo().getPrevius()) {
                result = true;
                super.agregarCartaPrincipio(carta);
            }
        }
        return result;
    }
}
