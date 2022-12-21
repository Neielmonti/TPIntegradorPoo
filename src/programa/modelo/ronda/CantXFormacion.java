package programa.modelo.ronda;
import programa.modelo.commons.Formacion;
import java.io.Serializable;
public class CantXFormacion implements Serializable {
    public final Formacion forma;
    public final int cantidad;
    public CantXFormacion(Formacion forma, int cantidad) {
        this.forma = forma;
        this.cantidad = cantidad;
    }
    public String getString() {
        return (cantidad + " " + forma.getLabel() + "/s");
    }
}