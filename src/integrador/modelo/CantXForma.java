package integrador.modelo;

import integrador.modelo.commons.Formacion;

public record CantXForma(Formacion forma,int cantidad) {

    public String getString() {
        return (cantidad + " " + forma.getLabel() + "/s");
    }
}
