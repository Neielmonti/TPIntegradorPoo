package programa.vista;
public enum ErrorVista {
    ACCION_NO_RECONOCIDA("No entiendo crack"),
    NOMBRE_TOMADO("Nombre ya tomado"),
    PARTIDA_LLENA("Partida llena :("),
    FUERA_DE_RANGO("Valor/es fuera de rango"),
    JUGADAS_INVALIDAS("Las jugadas no cumplen con las pedidas en la ronda"),
    JUGADA_RECHAZADA("Jugada invalida"),
    BAJADA_RECHAZADA("No se cumple con las jugadas pedidas en la ronda"),
    CANTIDAD_INCORRECTA_CARTAS("Cantidad incorrecta de cartas, solo puede tirar una y solo una"),
    DESCARGA_INVALIDA("La carta no pudo ser agregada a la jugada"),
    CONEXION("Error de conexion");
    private String df = "||ERROR||: ";
    private String label;
    ErrorVista(String label) {
        this.label = df + label;
    }
    public String getLabel() {
        return this.label;
    }
}
