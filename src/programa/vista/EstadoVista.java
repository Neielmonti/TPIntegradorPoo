package programa.vista;
public enum EstadoVista {
    ESPERANDO_USUARIO("Cuando este listo, presione el boton"),
    ESPERANDO_JUGADORES("Esperando a que todos los jugadores esten preparados!"),
    ESPERANDO_TURNO("Espere su turno"),
    JUGANDO(""),
    INICIALIZANDO("Ingrese su nombre (No puede ser vacio ni uno ya usado)"),
    TOMAR_CARTA("De donde quiere tomar la carta? \n" +
            "Del [" + OpcionVista.MAZO.getLabel() + "] o del [" + OpcionVista.POZO.getLabel() + "], ingrese su opcion"),
    TIRAR_O_BAJAR("Quiere [" + OpcionVista.TIRAR_CARTA.getLabel() + "] una carta al pozo? \n" +
            "o quiere [" + OpcionVista.BAJARSE.getLabel() + "]? Ingrese su opcion"),
    TIRAR_CARTA("Que carta quiene tirar? \n" +
            "Ingrese el nro. de la carta, o escriba [" + OpcionVista.CANCELAR.getLabel() + "] para cancelar"),
    BAJAR("[" + OpcionVista.ARMAR_JUEGO.getLabel() + "], [" + OpcionVista.BAJARSE.getLabel() + "], o [" + OpcionVista.CANCELAR.getLabel() + "], ingrese su opcion"),
    ARMANDO_JUGADA("Ingrese los nros. de las cartas separados por un guion, \n" +
            "o escriba [" + OpcionVista.CANCELAR.getLabel() + "] para cancelar"),
    BAJADO_DESCARGAR_O_TIRAR("Quiere [" + OpcionVista.DESCARGAR.getLabel() + "] cartas en las jugadas o quiere [" + OpcionVista.TIRAR_CARTA.getLabel() + "]?\n" +
            "Ingrese su opcion"),
    BAJADO_DESCARGAR("Que carta quiere descargar? Y en que jugada? \n" +
            "Ingrese el nro. de carta, el nro. de jugada, e [" + OpcionVista.INICIO.getLabel() + "] o [" + OpcionVista.FINAL.getLabel()
            + "] separados por un guion,\n" +
            "o escriba [" + OpcionVista.CANCELAR.getLabel() + "] para cancelar");
    private String label;
    EstadoVista(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}