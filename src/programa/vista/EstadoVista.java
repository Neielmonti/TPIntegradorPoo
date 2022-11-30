package programa.vista;

public enum EstadoVista {
    ESPERANDO_JUGADORES("Esperando jugadores, sea paciente"),
    ESPERANDO_TURNO("Espere su turno"),
    JUGANDO(""),
    INICIALIZANDO("Ingrese su nombre (No puede ser vacio ni uno ya usado)"),
    TOMAR_CARTA("De donde quiere tomar la carta? Del [" + OpcionVista.MAZO.getLabel() + "] o del [" + OpcionVista.POZO.getLabel() + "]"),
    TIRAR_O_BAJAR("Quiere tirar una carta al [" + OpcionVista.TIRAR_CARTA.getLabel() + "]? o quiere [" + OpcionVista.BAJARSE.getLabel() + "]?"),
    TIRAR_CARTA("Que carta quiene tirar?"),
    BAJAR("[" + OpcionVista.ARMAR_JUEGO.getLabel() + "], [" + OpcionVista.BAJARSE.getLabel() + "], o [" + OpcionVista.CANCELAR.getLabel() + "]"),
    BAJARSE(""),
    ARMANDO_JUEGO("Ingrese las cartas del juego a armar"),
    BAJADO_DESCARGAR_O_TIRAR("Quiere [" + OpcionVista.DESCARGAR.getLabel() + "] cartas en las jugadas o quiere [" + OpcionVista.TIRAR_CARTA.getLabel() + "]?"),
    BAJADO_DESCARGAR("En que jugada quiere descargar? Y que carta?"),
    BAJADO_DESCARGAR_CARTA("Que carta quiere tirar?");
    private String label;
    EstadoVista(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
