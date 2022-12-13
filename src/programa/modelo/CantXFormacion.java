package programa.modelo;
import programa.modelo.commons.Formacion;
import java.io.Serializable;
public record CantXFormacion(Formacion forma, int cantidad) implements Serializable {
    public String getString() {
        return (cantidad + " " + forma.getLabel() + "/s");
    }
}