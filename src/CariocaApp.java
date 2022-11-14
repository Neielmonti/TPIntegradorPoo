import integrador.controlador.Controlador;
import integrador.modelo.Juego;
import integrador.vista.VistaConsola;

public class CariocaApp {

    public static void main(String[] args) {
        Juego juego = new Juego();
        VistaConsola vista = new VistaConsola();
        Controlador controlador = new Controlador(juego, vista);
        vista.iniciar();
    }
}
