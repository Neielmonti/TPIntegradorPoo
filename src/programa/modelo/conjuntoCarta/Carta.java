package programa.modelo.conjuntoCarta;

import programa.modelo.commons.PaloCarta;
import programa.modelo.commons.TipoCarta;

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
        if (this.tipo == TipoCarta.JOKER) {
            return ("(" + this.tipo.getLabel() + ")");
        }
        else return ("(" + this.palo.getLabel() + " - " + this.tipo.getLabel() + ")");
    }
}
