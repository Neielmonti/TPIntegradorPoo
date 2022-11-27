package programa.modelo;
import programa.modelo.commons.Formacion;
public record CantXForma(Formacion forma,int cantidad) {
    public String getString() {
        return (cantidad + " " + forma.getLabel() + "/s");
    }
}
