package cariocaApp;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import programa.controlador.Controlador;
import programa.vista.IVista;
import programa.vista.VistaConsolaSwing;
import programa.vista.VistaGraficaSwing;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
public class AppCliente {
    public void inicioGrafico(){
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el cliente", "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la corre el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );
        String portServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );
        String[] buttons = {"Consola","Grafica"};
        int returnValue = JOptionPane.showOptionDialog(null, "Seleccione su vista", "Selector",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
        IVista vista;
        if (returnValue == 0) {
            vista = new VistaConsolaSwing();
        }
        else {
            vista = new VistaGraficaSwing();
        }
        Controlador controlador = new Controlador(vista);
        Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
        try {
            c.iniciar(controlador);
        } catch (RemoteException | RMIMVCException e) {
            e.printStackTrace();
        }
    }
}