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
            case ESPERANDO_USUARIO -> {
                this.controlador.estaPreparado();
            }

            case ESPERANDO_JUGADORES -> {
                clearMemo();
                println(estado.getLabel());
            }

            case INICIALIZANDO -> {
                String nombre = textbox.getText();
                if (!controlador.faltanJugadoes()) {printError(ErrorVista.PARTIDA_LLENA);}
                if (!controlador.nombreValido(nombre.trim())) {printError(ErrorVista.NOMBRE_TOMADO);}
                else {controlador.agregarJugador(nombre.trim());}
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
                if (in.equals(OpcionVista.CANCELAR.getLabel())) {
                    setEstado(EstadoVista.TIRAR_O_BAJAR);
                }
                else {
                    clearTextbox();
                    this.controlador.tirarCartaAlPozo(Integer.parseInt(in) - 1);
                }
            }

            case BAJAR -> {
                String in = textbox.getText().trim();
                if (in.equals(OpcionVista.ARMAR_JUEGO.getLabel())) {
                    setEstado(EstadoVista.ARMANDO_JUGADA);
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

            case ARMANDO_JUGADA -> {
                String in = textbox.getText().trim();
                if (!verificarCartasJugada(in)) {
                    println("Por favor, solo ingrese numeros y '-' ");
                }
                else if (in.equals(OpcionVista.CANCELAR.getLabel())) {
                    setEstado(EstadoVista.BAJAR);
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
                jugadaCarta[0] = Integer.parseInt(aux[0]);
                jugadaCarta[1] = Integer.parseInt(aux[1]);
                List<IJugada> jugadas = this.controlador.getAllJugadas();
                IMano mano = this.controlador.getMano();

                if (in.equals(OpcionVista.CANCELAR.getLabel())) {
                    setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                }

                else if (jugadaCarta.length != 3)  {
                    println("Por favor, ingrese los parametros necesarios");
                }

                else if ((jugadaCarta[1] > mano.getCantidadCartas()) || (jugadaCarta[1] < 1)) {
                    println("Carta fuera de rango, la mano tiene solamente: " + mano.getCantidadCartas());
                }

                else if ((jugadaCarta[0] < 1) || (jugadaCarta[0] > jugadas.size())) {
                    println("Jugada fuera de rango");
                }

                else {
                    if (aux[2].equals(OpcionVista.FINAL.getLabel())) {
                        this.controlador.agregarCartaJuego(jugadaCarta[0] - 1, jugadaCarta[1] - 1, true);
                    }
                    else if (aux[2].equals(OpcionVista.INICIO.getLabel())) {
                        this.controlador.agregarCartaJuego(jugadaCarta[0] - 1, jugadaCarta[1] - 1, false);
                    }
                }
            }

            case ESPERANDO_TURNO -> clearTextbox();
        }
    }

    public void inicioGrafico() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    setSize(500,700);
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
                println("------------Jugador " + jugada.getNombreJugador() + "------------");
            }
            println("{Jugada " + (jugadas.indexOf(jugada) + 1) + "} -------- \n" + jugada.mostrarCartas() + "\n");
        }
    }
    public void setEstado(EstadoVista estado) {
        this.estado = estado;
        println(estado.getLabel());
    }

    public void rondaGanada() {
        clearMemo();
        IJugador ganador = this.controlador.getGanador();
        IJugador jugador = this.controlador.getJugador();
        println("EL JUGADOR " + ganador.getNombre() + " HA GANADO! >:)");
        println("Tu puntaje es de: " + jugador.getPuntaje());
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
        printError(ErrorVista.JUGADA_RECHAZADA);
    }

    public void jugando() {
        estado = EstadoVista.JUGANDO;
    }

    public void mostrarMano() {
        IMano mano = this.controlador.getMano();
        println("---------MANO---------" + "\n" + mano.mostrarCartas() + "\n");
    }

    public void mostrarPozo() {
        println("---------POZO---------");
        println(this.controlador.getPozo().mostrarCartas() + "\n");
    }

    public void mostrarRonda() {
        println("---------RONDA---------");
        println("Jugadas a armar: " + this.controlador.getRonda().mostrarRonda() + "\n");
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