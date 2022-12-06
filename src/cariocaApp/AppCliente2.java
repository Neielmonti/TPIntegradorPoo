package cariocaApp;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import programa.controlador.Controlador;
import programa.vista.ErrorVista;
import programa.vista.VistaConsolaSwing;

import java.rmi.RemoteException;
public class AppCliente2 {
    public static void main(String[] args) {
        VistaConsolaSwing vista = new VistaConsolaSwing();
       IControladorRemoto controlador = new Controlador(vista);
        Cliente cliente = new Cliente("127.0.0.1",9998,"127.0.0.1",8888);
        try {
            cliente.iniciar(controlador);
        } catch (RemoteException e) {
            //error de conexion
            vista.printError(ErrorVista.CONEXION);
        } catch (RMIMVCException e) {
            vista.printError(ErrorVista.CONEXION);
            // error al crear el objeto de acceso remoto del modelo o del controlador
        }
    }
}