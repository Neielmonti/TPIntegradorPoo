package integrador.modelo.commons;

public enum PaloCarta {
    PICAS("Picas"),
    CORAZONES("Corazones"),
    ROMBOS("Rombos"),
    TREBOLES("Treboles"),
    JOKER("Joker");
    private String label;
    PaloCarta(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
