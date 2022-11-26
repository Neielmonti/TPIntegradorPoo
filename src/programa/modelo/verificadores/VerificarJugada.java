package programa.modelo.verificadores;

import programa.modelo.commons.Formacion;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.jugadas.Jugada;

import java.util.List;

public abstract class VerificarJugada {
    private Formacion forma;
    public abstract Jugada formarJugada(List<Carta> cartas);

    public VerificarJugada(Formacion forma){
        this.forma = forma;
    }

    public int getCantCartas(){
        return this.forma.getCantCartas();
    };

}
