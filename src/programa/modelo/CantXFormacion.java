package programa.modelo;
import programa.modelo.commons.Formacion;
public record CantXFormacion(Formacion forma, int cantidad) {
    public String getString() {
        return (cantidad + " " + forma.getLabel() + "/s");
    }
}