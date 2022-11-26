package programa.vista;

public enum EstadoVista {
    ESPERANDO_JUGADORES("Esperando jugadores, sea paciente"),
    ESPERANDO_TURNO("Espere su turno"),
    JUGANDO(""),
    INICIALIZANDO("Ingrese su nombre (No puede ser vacio ni uno ya usado)"),
    TOMAR_CARTA("De donde quiere tomar la carta? Del mazo o del pozo?"),
    TIRAR_O_BAJAR("Quiere tirar una carta al pozo? o quiere bajarse?"),
    TIRAR_CARTA("Que carta quiene tirar?"),
    BAJAR("Arme sus juegos");
    private String label;
    EstadoVista(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
