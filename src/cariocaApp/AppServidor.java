package cariocaApp;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import programa.modelo.Juego;
import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
public class AppServidor {
    public static void main(String[] args) {
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );
        Juego juego = new Juego();
        Servidor servidor = new Servidor(ip, Integer.parseInt(port));
        try {
            servidor.iniciar(juego);
        } catch (RemoteException | RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}