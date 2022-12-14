package programa.modelo.commons;

public enum TipoCarta {
    A(20,"A"),
    DOS(2,"2"),
    TRES(3,"3"),
    CUATRO(4,"4"),
    CINCO(5,"5"),
    SEIS(6,"6"),
    SIETE(7,"7"),
    OCHO(8,"8"),
    NUEVE(9,"9"),
    DIEZ(10,"10"),
    J(10,"J"),
    Q(10,"Q"),
    K(10,"K"),
    JOKER(50,"Joker");

    private int valor;
    private String label;

    TipoCarta(int valor, String label) {
        this.valor = valor;
        this.label = label;
    }

    public static TipoCarta getMenorTipo(){//POSIBLEMENTE INUTIL  <-------------
        TipoCarta[] vals = TipoCarta.values();
        if (vals.length > 0) {
            return vals[0];
        }
        else return null;
    }

    public static TipoCarta getMayorTipo(){//POSIBLEMENTE INUTIL  <-------------
        TipoCarta[] vals = TipoCarta.values();
        if (vals.length > 0) {
            return vals[vals.length - 2];
        }
        else return null;
    }

    public int getValor(){
        return this.valor;
    }

    public String getLabel(){
        return this.label;
    }

    public TipoCarta getNext() {

        TipoCarta[] vals = TipoCarta.values();
        if ((this.ordinal() < vals.length - 1) && (this.ordinal() != JOKER.ordinal() - 1)) {
            return vals[(this.ordinal() + 1) % vals.length];
        } else return null;
    }

    public TipoCarta getPrevius() {
        TipoCarta[] vals = TipoCarta.values();
        if (this.ordinal() > 0) {
            return vals[(this.ordinal() - 1) % vals.length];
        } else return null;
    }


}
