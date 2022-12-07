package cariocaApp;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import programa.modelo.Juego;
import java.rmi.RemoteException;
public class AppServidorPrueba {
    public static void main(String[] args) {
        Juego modelo = new Juego();
        Servidor servidor = new Servidor("127.0.0.1", 8888);
        try {
            servidor.iniciar(modelo);
        } catch (RemoteException e) {
            // error de conexión
        } catch (RMIMVCException e) {
            // error al crear el objeto de acceso remoto del modelo
        }
    }
}
