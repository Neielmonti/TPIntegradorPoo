package programa.modelo.conjuntoCarta;
import programa.modelo.commons.PaloCarta;
import programa.modelo.commons.TipoCarta;

import java.io.Serializable;

public class Carta implements Serializable {
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
        return (this.tipo.getLabel() + " " + this.palo.getSimbol());
    }
}