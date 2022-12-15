package programa.modelo.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Formacion implements Serializable {
    TRIO(3,"Trio"),
    ESCALA(4, "Escala"),
    ESCALERA_REAL(13, "Escalera Real"),
    ESCALERA_SUCIA(13,"Escalera Sucia", Arrays.asList(Formacion.ESCALERA_REAL));
    private List<Formacion> equivalentes;
    private int cantCartas;
    private String label;
    Formacion(int cantCartas, String label) {
        this(cantCartas,label,new ArrayList<>());
    }
    Formacion(int cantCartas, String label, List<Formacion> equivalentes) {
        this.cantCartas = cantCartas;
        this.label = label;
        this.equivalentes = equivalentes;
    }
    public String getLabel() {return this.label;}
    public boolean esEquivalente(Formacion formaEquivalente) {
        return this.equivalentes.contains(formaEquivalente);
    }
    public int getCantCartas() {
        return this.cantCartas;
    }
}