package programa.modelo.commons;

import java.io.Serializable;

public enum Formacion implements Serializable {
    TRIO(3,"Trio"),
    ESCALA(4, "Escala"),
    ESCALERA_SUCIA(13,"Escalera Sucia"),
    ESCALERA_REAL(13, "Escalera Real");
    private int cantCartas;
    private String label;
    Formacion(int cantCartas, String label) {
        this.cantCartas = cantCartas;
        this.label = label;
    }
    public String getLabel() {return this.label;}
    public int getCantCartas() {
        return this.cantCartas;
    }
}