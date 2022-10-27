package integrador.modelo;

import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.commons.PaloCarta;
import integrador.modelo.commons.TipoCarta;
import integrador.modelo.conjuntoCarta.Mazo;

import java.util.ArrayList;
import java.util.List;

public class Juego {
    private List<Jugador> jugadores = new ArrayList<>();
    private Mazo mazo;

    private void crearMazo() {
        List<Carta> mazoList = new ArrayList<>();
        for (PaloCarta palo : PaloCarta.values()) {
            if (palo != PaloCarta.JOKER) {
                for (TipoCarta tipo : TipoCarta.values()) {
                    if (tipo != TipoCarta.JOKER) {
                        Carta c1 = new Carta(palo, tipo, tipo.getValor());
                        Carta c2 = new Carta(palo, tipo, tipo.getValor());
                        mazoList.add(c1);
                        mazoList.add(c2);
                    }
                }
            }
        }
        Carta c2;
        for (int i = 0; i < 4; i++) {
            c2 = new Carta(PaloCarta.JOKER, TipoCarta.JOKER, TipoCarta.JOKER.getValor());
            mazoList.add(c2);
        }
        this.mazo = new Mazo(mazoList);
    }

    public Juego() {
        this.crearMazo();
    }

    public String mostrarMazo() {
        return this.mazo.MostrarCartas();
    }

    public Carta cartaRandom() {
        return this.mazo.tomarCarta();
    }


}
