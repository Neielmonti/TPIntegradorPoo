package programa.modelo.commons;
import java.io.Serializable;
public enum PaloCarta implements Serializable {
    PICAS("Picas", "♠"),
    CORAZONES("Corazones","♥"),
    ROMBOS("Rombos","♦"),
    TREBOLES("Treboles", "♣"),
    JOKER("Joker","☺");
    private String label;
    private String simbol;
    PaloCarta(String label, String simbol) {
        this.label = label;
        this.simbol = simbol;
    }
    public String getLabel() {return this.label;}
    public String getSimbol(){return this.simbol;}
}