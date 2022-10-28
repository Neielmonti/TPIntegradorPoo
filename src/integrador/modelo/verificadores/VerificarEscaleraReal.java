package integrador.modelo.verificadores;

import integrador.modelo.commons.Formacion;

public class VerificarEscaleraReal extends VerificarAscendiente{
    public VerificarEscaleraReal() {
        this.forma = Formacion.ESCALERA_REAL;
        this.cantidadCartas = 12;
    }
}
