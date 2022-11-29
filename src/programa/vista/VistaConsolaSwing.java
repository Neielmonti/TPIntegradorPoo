package programa.vista;
import java.lang.Character;

import programa.controlador.Controlador;

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
                    else {printError(ErrorVista.PARTIDA_LLENA);}
                }
            }

            case TOMAR_CARTA -> {
                String in = textbox.getText().trim();
                clearTextbox();

                if (in.equals(OpcionVista.MAZO.getLabel())) {
                    this.controlador.tomarCartaMazo();
                }
                else if (in.equals(OpcionVista.POZO.getLabel())) {
                    this.controlador.tomarCartaPozo();
                }
                else printError(ErrorVista.ACCION_NO_RECONOCIDA);
            }

            case TIRAR_O_BAJAR -> {
                String in = textbox.getText().trim();
                clearTextbox();

                if (in.equals(OpcionVista.TIRAR_CARTA.getLabel())) {
                    setEstado(EstadoVista.TIRAR_CARTA);
                }
                else if (in.equals(OpcionVista.BAJARSE.getLabel())) {
                    setEstado(EstadoVista.BAJAR);
                }
                else printError(ErrorVista.ACCION_NO_RECONOCIDA);
            }

            case TIRAR_CARTA -> {
                String in = textbox.getText().trim();
                clearTextbox();
                this.controlador.tirarCartaAlPozo(Integer.parseInt(in)-1);
            }

            case BAJAR -> {
                String in = textbox.getText().trim();
                if (in.equals(OpcionVista.ARMAR_JUEGO.getLabel())) {
                    setEstado(EstadoVista.ARMANDO_JUEGO);
                }
                else if (in.equals(OpcionVista.BAJARSE.getLabel())) {
                    this.controlador.verificarJuegos();
                }
                else if (in.equals(OpcionVista.CANCELAR.getLabel())) {
                    setEstado(EstadoVista.TIRAR_O_BAJAR);
                    controlador.deshacerJugadas();
                }
                else printError(ErrorVista.ACCION_NO_RECONOCIDA);
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
                    else printError(ErrorVista.FUERA_DE_RANGO);
                }
            }

            case BAJADO_DESCARGAR_O_TIRAR -> {
                String in = textbox.getText().trim();

                if (in.equals(OpcionVista.DESCARGAR.getLabel())) {
                    setEstado(EstadoVista.BAJADO_DESCARGAR);

                } else if (in.equals(OpcionVista.TIRAR_CARTA.getLabel())) {
                    setEstado(EstadoVista.TIRAR_CARTA);

                }
                else printError(ErrorVista.ACCION_NO_RECONOCIDA);
            }

            case BAJADO_DESCARGAR -> {
                String in = textbox.getText().trim();
                String[] aux = in.split("-");
                int[] jugadaCarta = new int[aux.length];
                List<IJugada> jugadas = this.controlador.getAllJugadas();
                IMano mano = this.controlador.getMano();
                if (jugadaCarta.length != 2)  {
                    println("Por favor, ingrese los parametros necesarios");
                }
                else if ((jugadaCarta[0] > mano.getCantidadCartas()) || (jugadaCarta[0] < 1)) {
                    println("Carta fuera de rango");
                }
                else if ((jugadaCarta[1] < 1) || (jugadaCarta[1] > jugadas.size())) {
                    println("Jugada fuera de rango");
                }
                else this.controlador.agregarCartaJuego(jugadas.get(jugadaCarta[1]),jugadaCarta[0]);
            }

            case ESPERANDO_TURNO -> clearTextbox();
        }
    }

    public void inicioGrafico() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    setSize(450,700);
                    setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        println("Ingrese su nombre de jugador");
    }
    public void bajadaRechazada() {
        printError(ErrorVista.JUGADAS_INVALIDAS);
    }
    public void mostrarJugadasJugador() {
        List<IJugada> jugadas = this.controlador.getJugadasJugador();
        for (IJugada jugada: jugadas) {
            println("[Jugada " + (jugadas.indexOf(jugada) + 1) + "] -------- \n" + jugada.mostrarCartas() + "\n");
        }
    }
    public void mostrarAllJugadas() {
        List<IJugada> jugadas = this.controlador.getAllJugadas();
        String jugadorAnterior = null;
        for (IJugada jugada: jugadas) {
            if (jugada.getNombreJugador() != jugadorAnterior) {
                jugadorAnterior = jugada.getNombreJugador();
                println("---------Jugador " + jugada.getNombreJugador() + "---------");
            }
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
        boolean salida = true;
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
        println("---------[MANO]---------" + "\n" + mano.mostrarCartas() + "\n");
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

    private void printError(ErrorVista error) {
        println(error.getLabel());
    }
    private void println() {
        println("");
    }
}