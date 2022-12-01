package programa.vista;

public enum EstadoVista {
    ESPERANDO_JUGADORES("Esperando jugadores, sea paciente"),
    ESPERANDO_TURNO("Espere su turno"),
    JUGANDO(""),
    INICIALIZANDO("Ingrese su nombre (No puede ser vacio ni uno ya usado)"),
    TOMAR_CARTA("De donde quiere tomar la carta? Del [" + OpcionVista.MAZO.getLabel() + "] o del [" + OpcionVista.POZO.getLabel() + "], ingrese su opcion"),
    TIRAR_O_BAJAR("Quiere tirar una carta al [" + OpcionVista.TIRAR_CARTA.getLabel() + "]? o quiere [" + OpcionVista.BAJARSE.getLabel() + "]? Ingrese su opcion"),
    TIRAR_CARTA("Que carta quiene tirar? Ingrese el nro. de la carta, o escriba [" + OpcionVista.CANCELAR.getLabel() + "] para cancelar"),
    BAJAR("[" + OpcionVista.ARMAR_JUEGO.getLabel() + "], [" + OpcionVista.BAJARSE.getLabel() + "], o [" + OpcionVista.CANCELAR.getLabel() + "], ingrese su opcion"),
    BAJARSE(""),
    ARMANDO_JUEGO("Ingrese los nros. de las cartas separados por un guion, o escriba [" + OpcionVista.CANCELAR.getLabel() + "] para cancelar"),
    BAJADO_DESCARGAR_O_TIRAR("Quiere [" + OpcionVista.DESCARGAR.getLabel() + "] cartas en las jugadas o quiere [" + OpcionVista.TIRAR_CARTA.getLabel() + "]? Ingrese su opcion"),
    BAJADO_DESCARGAR("En que jugada quiere descargar? Y que carta? Ingrese el nro. de jugada y el nro. de carta a descargar separados por un guion, o escriba [" + OpcionVista.CANCELAR.getLabel() + "] para cancelar"),
    BAJADO_DESCARGAR_CARTA("Que carta quiere tirar? Ingrese el nro. de la carta");
    private String label;
    EstadoVista(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
