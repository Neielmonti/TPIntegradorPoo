package cariocaApp;
import programa.controlador.Controlador;
import programa.modelo.Juego;
import programa.vista.VistaConsolaSwing;
public class CariocaApp {
    public static void main(String[] args) {
        Juego juego = new Juego();
        VistaConsolaSwing v1 = new VistaConsolaSwing();
        Controlador c1 = new Controlador(juego, v1);
        VistaConsolaSwing v2 = new VistaConsolaSwing();
        Controlador c2 = new Controlador(juego, v2);

        //VistaConsolaSwing v3 = new VistaConsolaSwing();
        //Controlador c3 = new Controlador(juego, v3);
        //VistaConsolaSwing v4 = new VistaConsolaSwing();
        //Controlador c4 = new Controlador(juego, v4);

        v1.inicioGrafico();
        v2.inicioGrafico();

        //v3.inicioGrafico();
        //v4.inicioGrafico();
    }
}
