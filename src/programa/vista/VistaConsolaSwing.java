package programa.vista;

import programa.controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaConsolaSwing extends JFrame{
    private JTextArea memoPrincipal;
    private JTextField textbox;
    private JButton buttEnter;
    private JPanel panelPrincipal;
    private EstadoVista estado = EstadoVista.INICIALIZANDO;
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
                switchButton();
            }
        });
    }

    public void switchButton() {
        switch (estado) {

            case ESPERANDO_JUGADORES -> {
                clearMemo();
                println("Esperando Jugadores! Deben haber 4 para jugar!");
            }

            case INICIALIZANDO -> {
                String nombre = textbox.getText();
                if (!controlador.nombreValido(nombre.trim())) {
                    println("Por favor, ingrese un nombre valido");
                }
                else controlador.agregarJugador(nombre.trim());
            }

            case TOMAR_CARTA -> {
                String in = textbox.getText().trim();
                clearTextbox();
                if (in.equals("1")) {
                    this.controlador.tomarCartaMazo();
                }
                else if (in.equals("2")) {
                    this.controlador.tomarCartaPozo();
                }
                else println("No entiendo crack");
            }

            case TIRAR_O_BAJAR -> {
                String in = textbox.getText().trim();
                clearTextbox();

                if (in.equals("1")) {
                    setEstado(EstadoVista.TIRAR_CARTA);
                }
                else if (in.equals("2")) {
                    setEstado(EstadoVista.BAJAR);
                }
                else println("No entiendo crack");
            }

            case TIRAR_CARTA -> {
                String in = textbox.getText().trim();
                clearTextbox();
                this.controlador.tirarCartaAlPozo(Integer.parseInt(in)-1);
            }

            case ESPERANDO_TURNO -> println("Pera tu turno capo");
        }
    }

    public void inicioGrafico() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    setSize(300,300);
                    setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        println("Ingrese su nombre de jugador");
    }

    public void setEstado(EstadoVista estado) {
        this.estado = estado;
        println(estado.getLabel());
    }
    public void listoParaJugar() {
        estado = EstadoVista.ESPERANDO_JUGADORES;
        println("Bienvenido! Ahora debe esperar a que se conecte el resto!");
    }

    public void jugando() {
        estado = EstadoVista.JUGANDO;
    }

    public void mostrarMano() {
        IMano mano = this.controlador.getMano();
        println(mano.getStringCartas());
    }

    public void mostrarPozo() {
        println(this.controlador.getPozo().mostrarCartas());
    }

    private void clearMemo() {
        memoPrincipal.setText("");
    }

    private void clearTextbox() {
        textbox.setText("");
    }

    private void println(String texto) {
        clearTextbox();
        memoPrincipal.append(texto + "\n");
    }
    private void println() {
        println("");
    }
}