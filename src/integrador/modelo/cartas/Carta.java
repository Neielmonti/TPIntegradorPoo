package integrador.modelo.cartas;

public class Carta {
    private PaloCarta palo;
    private TipoCarta tipo;
    private int valor;

    public Carta(PaloCarta palo, TipoCarta tipo, int valor) {
        this.palo = palo;
        this.tipo = tipo;
        this.valor = valor;
    }

    public PaloCarta getPalo(){
        return this.palo;
    }

    public TipoCarta getTipo(){
        return this.tipo;
    }

    public int getValor() {
        return this.valor;
    }

    public String mostrarCarta() {
        return (this.tipo.toString() + " - " + this.palo.toString() + " - " + this.valor + "\n");
    }
}
