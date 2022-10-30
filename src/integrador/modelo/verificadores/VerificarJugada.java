package integrador.modelo.verificadores;

import integrador.modelo.commons.Formacion;
import integrador.modelo.conjuntoCarta.Carta;
import integrador.modelo.conjuntoCarta.jugadas.Jugada;

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
