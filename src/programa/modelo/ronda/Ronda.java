package programa.modelo.ronda;
import programa.modelo.jugador.Jugador;
import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.jugadas.Jugada;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ronda implements IRonda, Serializable{
    private List<CantXFormacion> formaciones = new ArrayList<>();
    // El constructor esta sobrecargado, para crear una ronda con un solo tipo de jugada (formacion), o con multiples
    public Ronda(Formacion formacion, int cantidad) {
        this.formaciones.add(new CantXFormacion(formacion,cantidad));
    }
    public Ronda(List<CantXFormacion> formaciones) {
        while (!formaciones.isEmpty()) {
            Formacion formaActual = formaciones.get(0).forma();
            this.formaciones.add(formaciones.get(0));
            formaciones.remove(0);
            // Se eliminan las formaciones repetidas para evitar el mal uso de las rondas (solo una precaucion)
            eliminarFormacionesRepetidas(formaActual,formaciones);
        }
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
        // Se revisa si el jugador tiene las jugadas (y la cantidad apropiada de ellas) correspondientes a
        // las formaciones
        for (CantXFormacion cf:this.formaciones) {
            Formacion formaActual = cf.forma();
            int cantActual = cf.cantidad();
            int contador = 0;
            for (Jugada jugada:jugadas) {
                if (jugada.getForma() == formaActual) {
                    contador++;
                }
            }
            // Si la cantidad de jugadas de la formacion buscada no cumple con la cantidad requerida, se devuelve false
            if (cantActual != contador) {
                salida = false;
                break;
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
}