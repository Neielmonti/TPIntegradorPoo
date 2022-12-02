package programa.modelo;

import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.jugadas.Jugada;
import programa.vista.IRonda;

import java.util.ArrayList;
import java.util.List;

public class Ronda implements IRonda {
    private List<CantXFormacion> formaciones = new ArrayList<>();
    /**
    public Ronda(List<Formacion> formaciones){
        while (!formaciones.isEmpty()) {
            Formacion formaActual = formaciones.get(0);
            int cantidad = getCantForm(formaciones,formaActual);
            while (formaciones.contains(formaActual)) {
                formaciones.remove(formaActual);
            }
            if (cantidad > 0) {
                this.formaciones.add(new CantXFormacion(formaActual,cantidad));
            }
        }
    }
    **/

    public Ronda(List<CantXFormacion> formaciones) {
        while (!formaciones.isEmpty()) {
            Formacion formaActual = formaciones.get(0).forma();
            this.formaciones.add(formaciones.get(0));
            formaciones.remove(0);
            eliminarFormacionesRepetidas(formaActual,formaciones);
        }
    }

    public Ronda(Formacion formacion, int cantidad) {
        this.formaciones.add(new CantXFormacion(formacion,cantidad));
    }

    private void eliminarFormacionesRepetidas(Formacion forma, List<CantXFormacion> formaciones) {
        int i = 0;
        while (i < formaciones.size()) {
            if (formaciones.get(i).forma() == forma) {
                formaciones.remove(i);
            }
            else i++;
        }
    }

    public boolean verificarJugadasxRonda(Jugador jugador) {
        List<Jugada> jugadas = jugador.getJugadas();
        boolean salida = true;
        for (CantXFormacion cf:this.formaciones) {
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

    @Override
    public String mostrarRonda() {
        String result = "";
        CantXFormacion ultimaFormacion = null;
        if (!formaciones.isEmpty()) {
            ultimaFormacion = formaciones.get(formaciones.size()-1);
        }
        for (CantXFormacion forma: formaciones) {
            result += forma.getString();
            if (forma != ultimaFormacion) {
                result += ", ";
            }
        }
        return result;
    }

    private int getCantForm(List<Formacion> formaciones, Formacion forma) {
        int contador = 0;
        for (Formacion f:formaciones) {
            if (f == forma) {contador++;}
        }
        return contador;
    }
}
