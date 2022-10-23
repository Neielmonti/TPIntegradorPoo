package integrador.modelo.cartas;

public class ValorXTipo {
    private TipoCarta tipo;
    private int valor;

    public ValorXTipo(TipoCarta tipo, int valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public TipoCarta getTipo() {
        return this.tipo;
    }

    public int getValor() {
        return this.valor;
    }
}
