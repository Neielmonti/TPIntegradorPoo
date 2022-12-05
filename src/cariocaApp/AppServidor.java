package cariocaApp;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import programa.modelo.Juego;
import java.rmi.RemoteException;
public class AppServidor {
    public static void main(String[] args) {
        Juego juego = new Juego(); // modelo
        Servidor servidor = new Servidor("127.0.0.1", 8888);
        try {
            servidor.iniciar(juego);
        } catch (RemoteException e) {
            // error de conexión
        } catch (RMIMVCException e) {
            // error al crear el objeto de acceso remoto del modelo
        }
    }
}