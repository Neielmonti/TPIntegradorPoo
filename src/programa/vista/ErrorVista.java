package programa.vista;

public enum ErrorVista {
    ACCION_NO_RECONOCIDA("No entiendo crack"),
    NOMBRE_TOMADO("Nombre ya tomado"),
    NOMBRE_INVALIDO("Nombre invalido"),
    PARTIDA_LLENA("Partida llena :("),
    FUERA_DE_RANGO("Valor/es fuera de rango"),
    JUGADAS_INVALIDAS("Las jugadas no cumplen con las pedidas en la ronda");

    private String df = "[ERROR]: ";
    private String label;
    ErrorVista(String label) {
        this.label = df + label;
    }

    public String getLabel() {
        return this.label;
    }
}
