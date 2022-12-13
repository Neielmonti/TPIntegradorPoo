package programa.vista;
public enum ErrorVista {
    ACCION_NO_RECONOCIDA("Accion no reconocida"),
    NOMBRE_TOMADO("Nombre ya tomado"),
    NOMBRE_INVALIDO("Nombre invalido"),
    PARTIDA_LLENA("Partida llena, debera esperara a que haya espacio"),
    FUERA_DE_RANGO("Valor/es fuera de rango"),
    JUGADA_RECHAZADA("Jugada invalida"),
    BAJADA_RECHAZADA("No se cumple con las jugadas pedidas en la ronda"),
    CANTIDAD_INCORRECTA_CARTAS("Cantidad incorrecta de cartas, solo puede tirar una y solo una"),
    DESCARGA_INVALIDA("La carta no pudo ser agregada a la jugada"),
    CONEXION("Error de conexion");
    private static String df = "||ERROR||: ";
    private String label;
    ErrorVista(String label) {
        this.label = label;
    }
    public String getText() {
        return (df + this.label);
    }
    public String getLabel() {
        return this.label;
    }
}
