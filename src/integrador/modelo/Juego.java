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
    private Mazo mazo = new Mazo();

    private void crearMazo() {
        for (PaloCarta palo : PaloCarta.values()) {
            if (palo != PaloCarta.JOKER) {
                for (TipoCarta tipo : TipoCarta.values()) {
                    if (tipo != TipoCarta.JOKER) {
                        Carta c1 = new Carta(palo, tipo, this.valores.valorXTipo(tipo));
                        this.mazo.agregarCarta(c1);
                        this.mazo.agregarCarta(c1);
                    }
                }
            }
        }
        Carta c2 = new Carta(PaloCarta.JOKER,TipoCarta.JOKER,this.valores.valorXTipo(TipoCarta.JOKER));
        this.mazo.agregarCarta(c2);
        this.mazo.agregarCarta(c2);
        this.mazo.agregarCarta(c2);
        this.mazo.agregarCarta(c2);
    }

    public Juego() {
        this.crearMazo();
    }

    public String mostrarMazo() {
        return this.mazo.MostrarCartas();
    }

    public String cartaRandom() {
        return this.mazo.tomarCarta().mostrarCarta();
    }


}
