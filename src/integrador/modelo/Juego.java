package integrador.modelo;

import integrador.modelo.cartas.Carta;
import integrador.modelo.cartas.PaloCarta;
import integrador.modelo.cartas.TipoCarta;
import integrador.modelo.cartas.ValoresXTipo;
import integrador.modelo.conjuntoCarta.Mazo;

import java.util.ArrayList;
import java.util.List;

public class Juego {
    private List<Jugador> jugadores = new ArrayList<>();
    private ValoresXTipo valores = new ValoresXTipo();
    private Mazo mazo;

    private void crearMazo() {
        List<Carta> mazoList = new ArrayList<>();
        for (PaloCarta palo : PaloCarta.values()) {
            if (palo != PaloCarta.JOKER) {
                for (TipoCarta tipo : TipoCarta.values()) {
                    if (tipo != TipoCarta.JOKER) {
                        Carta c1 = new Carta(palo, tipo, this.valores.valorXTipo(tipo));
                        mazoList.add(c1);
                        mazoList.add(c1);
                    }
                }
            }
        }
        Carta c2 = new Carta(PaloCarta.JOKER,TipoCarta.JOKER,this.valores.valorXTipo(TipoCarta.JOKER));
        mazoList.add(c2);
        mazoList.add(c2);
        mazoList.add(c2);
        mazoList.add(c2);
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
