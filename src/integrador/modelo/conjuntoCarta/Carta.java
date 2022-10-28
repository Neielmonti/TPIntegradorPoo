package integrador.modelo.conjuntoCarta;

import integrador.modelo.commons.PaloCarta;
import integrador.modelo.commons.TipoCarta;

public class Carta {
    private PaloCarta palo;
    private TipoCarta tipo;

    public Carta(PaloCarta palo, TipoCarta tipo) {
        this.palo = palo;
        this.tipo = tipo;
    }

    public PaloCarta getPalo(){
        return this.palo;
    }

    public TipoCarta getTipo(){
        return this.tipo;
    }

    public String mostrarCarta() {
        return (this.tipo.toString() + " - " + this.palo.toString() + " - " + this.tipo.getValor() + "\n");
    }
}
