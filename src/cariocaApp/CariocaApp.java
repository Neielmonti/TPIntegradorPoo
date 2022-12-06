package cariocaApp;
import programa.vista.VistaGraficaSwing;
import java.rmi.RemoteException;
public class CariocaApp {
    public static void main(String[] args) throws RemoteException {
        VistaGraficaSwing v = new VistaGraficaSwing();
        v.inicioGrafico();
    }
}