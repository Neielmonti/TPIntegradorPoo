package programa.vista;

public enum OpcionVista {
    MAZO("MAZO"),
    POZO("POZO"),
    TIRAR_CARTA("TIRAR"),
    BAJARSE("BAJAR"),
    ARMAR_JUEGO("ARMAR"),
    CANCELAR("CANCELAR"),
    DESCARGAR("DESCARGAR");
    private String label;
    OpcionVista(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
