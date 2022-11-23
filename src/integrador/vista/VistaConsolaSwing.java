package integrador.vista;

import integrador.controlador.Controlador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaConsolaSwing extends JFrame{
    private JTextArea memoPrincipal;
    private JTextField textbox;
    private JButton buttEnter;
    private JPanel panelPrincipal;
    private EstadoVista estado = EstadoVista.ESPERANDO_JUGADORES;

    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public VistaConsolaSwing() {
        super("Carioca App");
        setContentPane(this.panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (estado == EstadoVista.ESPERANDO_JUGADORES) {
                    clearMemo();
                    clearTextbox();
                    println("Esperando Jugadores! Deben haber 4 para jugar!");
                }
            }
        });
    }

    public void mostrarMano() {
        IMano mano = this.controlador.getMano();
        println(mano.getStringCartas());
    }

    private void clearMemo() {
        memoPrincipal.setText("");
    }

    private void clearTextbox() {
        textbox.setText("");
    }

    private void println(String texto) {
        memoPrincipal.append(texto + "\n");
    }
    private void println() {
        println("");
    }
}
