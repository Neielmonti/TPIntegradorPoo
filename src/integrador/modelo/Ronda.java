package integrador.modelo;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.jugadas.Jugada;

import java.util.ArrayList;
import java.util.List;

public class Ronda {
    private List<CantXForma> formaciones = new ArrayList<>();

    public Ronda(List<Formacion> formaciones){
        while (formaciones.isEmpty()) {
            Formacion formaActual = formaciones.get(0);
            int cantidad = getCantForm(formaciones,formaActual);
            while (formaciones.contains(formaActual)) {
                formaciones.remove(formaActual);
            }
            if (cantidad > 0) {
                this.formaciones.add(new CantXForma(formaActual,cantidad));
            }
        }
    }

    public boolean verificarJugadasxRonda(List<Jugada> jugadas) {
        boolean salida = true;
        for (CantXForma cf:this.formaciones) {
            Formacion formaActual = cf.forma();
            int cantActual = cf.cantidad();
            int contador = 0;
            for (Jugada jugada:jugadas) {
                if (jugada.getForma() == formaActual) {
                    contador++;
                }
            }
            if (cantActual != contador){
                salida = false;
            }
        }
        return salida;
    }

    private int getCantForm(List<Formacion> formaciones, Formacion forma) {
        int contador = 0;
        for (Formacion f:formaciones) {
            if (f == forma) {contador++;}
        }
        return contador;
    }
}
