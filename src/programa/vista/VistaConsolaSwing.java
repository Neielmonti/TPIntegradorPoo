package programa.vista;
import java.lang.Character;

import programa.controlador.Controlador;
import programa.modelo.conjuntoCarta.jugadas.Jugada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
                    println("[ERROR]: Nombre invalido o ya en uso");
                }
                else {
                    if (controlador.faltanJugadoes()) {controlador.agregarJugador(nombre.trim());}
                    else {println("[ERROR]: Partida llena :(");}
                }
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

            case BAJAR -> {
                String in = textbox.getText().trim();
                if (in.equals("1")) {
                    setEstado(EstadoVista.ARMANDO_JUEGO);
                }
                else if (in.equals("2")) {
                    setEstado(EstadoVista.BAJARSE);
                }
                else if (in.equals("3")) {
                    setEstado(EstadoVista.TIRAR_O_BAJAR);
                    controlador.deshacerJugadas();
                }
            }

            case ARMANDO_JUEGO -> {
                String in = textbox.getText().trim();
                if (!verificarCartasJugada(in)) {
                    println("Por favor, solo ingrese numeros y '-' ");
                }
                else {
                    String[] aux = in.split("-");
                    int[] indices = new int[aux.length];
                    boolean result = true;
                    int cantCartas = this.controlador.getMano().getCantidadCartas();
                    for (int i = 0; i < aux.length; i++) {
                        int a = Integer.parseInt(aux[i]);
                        if ((a > cantCartas) || (a < 1)) {
                            result = false;
                        }
                        else indices[i] = (a - 1);
                    }
                    if (result) {
                        this.controlador.armarJugada(indices);
                    }
                    else println("EH NO, que haces? fuera de rango crack");
                }
            }

            case ESPERANDO_TURNO -> println("Pera tu turno capo");
        }
    }

    public void inicioGrafico() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    setSize(400,400);
                    setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        println("Ingrese su nombre de jugador");
    }

    public void mostrarJugadasJugador() {
        List<IConjuntoCartas> jugadas = this.controlador.getJugadasJugador();
        for (IConjuntoCartas jugada: jugadas) {
            println("[Jugada " + (jugadas.indexOf(jugada) + 1) + "] -------- \n" + jugada.mostrarCartas() + "\n");
        }
    }

    public void setEstado(EstadoVista estado) {
        this.estado = estado;
        println(estado.getLabel());
    }
    public void listoParaJugar() {
        estado = EstadoVista.ESPERANDO_JUGADORES;
        println("Bienvenido! Ahora debe esperar a que se conecte el resto!");
    }

    public boolean verificarCartasJugada(String text) {
        Boolean salida = true;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!((Character.isDigit(c)) || (c == "-".charAt(0)))) {
                salida = false;
            }
        }
        return salida;
    }

    public void jugadaRechazada() {
        println("[ERROR]: Jugada rechazada");
    }

    public void jugando() {
        estado = EstadoVista.JUGANDO;
    }

    public void mostrarMano() {
        IMano mano = this.controlador.getMano();
        println("---------[MANO]---------" + "\n" + mano.getStringCartas() + "\n");
    }

    public void mostrarPozo() {
        println(this.controlador.getPozo().mostrarCartas());
    }

    public void mostrarRonda() {
        println(this.controlador.getRonda().mostrarRonda());
    }

    public void clearMemo() {
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