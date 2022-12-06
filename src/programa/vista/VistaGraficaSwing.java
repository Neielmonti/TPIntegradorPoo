package programa.vista;
import programa.controlador.Controlador;
import programa.modelo.conjuntoCarta.Carta;
import programa.modelo.conjuntoCarta.Mano;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VistaGraficaSwing extends JFrame implements IVista{
    private JPanel panelPrincipal;
    private JToggleButton c1ToggleButton;
    private JToggleButton c3ToggleButton;
    private JToggleButton c7ToggleButton;
    private JToggleButton c9ToggleButton;
    private JToggleButton c11ToggleButton;
    private JToggleButton c13ToggleButton;
    private JToggleButton c2ToggleButton;
    private JToggleButton c12ToggleButton;
    private JToggleButton c10ToggleButton;
    private JToggleButton c8ToggleButton;
    private JToggleButton c5ToggleButton;
    private JToggleButton c6ToggleButton;
    private JToggleButton c4ToggleButton;
    private JTextArea memoTextArea;
    private JButton pozoButton;
    private JButton mazoButton;
    private JButton tirarCartaButton;
    private JButton armarJugadaButton;
    private JButton bajarseButton;
    private JTextField textbox;
    private JButton alFinalButton;
    private JButton alInicioButton;
    private JButton setNombreButton;
    private Controlador controlador;
    private EstadoVista estado = EstadoVista.INICIALIZANDO;
    private IMano mano;
    private List<JToggleButton> botonesCarta = new ArrayList<>();
    private boolean[] indicesCartasSeleccionadas = new boolean[Mano.getSizeMano() + 1];

    public VistaGraficaSwing() {
        super("Carioca App");
        setContentPane(this.panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        botonesCarta.add(c1ToggleButton);
        botonesCarta.add(c2ToggleButton);
        botonesCarta.add(c3ToggleButton);
        botonesCarta.add(c4ToggleButton);
        botonesCarta.add(c5ToggleButton);
        botonesCarta.add(c6ToggleButton);
        botonesCarta.add(c7ToggleButton);
        botonesCarta.add(c8ToggleButton);
        botonesCarta.add(c9ToggleButton);
        botonesCarta.add(c10ToggleButton);
        botonesCarta.add(c11ToggleButton);
        botonesCarta.add(c12ToggleButton);
        botonesCarta.add(c13ToggleButton);
        Arrays.fill(indicesCartasSeleccionadas, false);
        c1ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 1) {
                    indicesCartasSeleccionadas[0] = c1ToggleButton.isSelected();
                }
            }
        });
        c2ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 2) {
                    indicesCartasSeleccionadas[1] = c1ToggleButton.isSelected();
                }
            }
        });
        c3ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 3) {
                    indicesCartasSeleccionadas[2] = c1ToggleButton.isSelected();
                }
            }
        });
        c4ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 4) {
                    indicesCartasSeleccionadas[3] = c1ToggleButton.isSelected();
                }
            }
        });
        c5ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 5) {
                    indicesCartasSeleccionadas[4] = c1ToggleButton.isSelected();
                }
            }
        });
        c6ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 6) {
                    indicesCartasSeleccionadas[5] = c1ToggleButton.isSelected();
                }
            }
        });
        c7ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 7) {
                    indicesCartasSeleccionadas[6] = c1ToggleButton.isSelected();
                }
            }
        });
        c8ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 8) {
                    indicesCartasSeleccionadas[7] = c1ToggleButton.isSelected();
                }
            }
        });
        c9ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 9) {
                    indicesCartasSeleccionadas[8] = c1ToggleButton.isSelected();
                }
            }
        });
        c10ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 10) {
                    indicesCartasSeleccionadas[9] = c1ToggleButton.isSelected();
                }
            }
        });
        c11ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 11) {
                    indicesCartasSeleccionadas[10] = c1ToggleButton.isSelected();
                }
            }
        });
        c12ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 12) {
                    indicesCartasSeleccionadas[11] = c1ToggleButton.isSelected();
                }
            }
        });
        c13ToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mano.getCantidadCartas() >= 13) {
                    indicesCartasSeleccionadas[12] = c1ToggleButton.isSelected();
                }
            }
        });
        armarJugadaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indicesCartas = sacarCartasNulas();
                controlador.armarJugada(indicesCartas);
            }
        });
        tirarCartaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indicesCartas = sacarCartasNulas();
                if (indicesCartas.length != 1) {
                    printError(ErrorVista.CANTIDAD_INCORRECTA_CARTAS);
                }
                else controlador.tirarCartaAlPozo(indicesCartas[0]);
            }
        });
        mazoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.tomarCartaMazo();
            }
        });
        pozoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.tomarCartaPozo();
            }
        });
        bajarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.verificarJugadas();
            }
        });
        alFinalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCartaJugada(true);
            }
        });
        alInicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCartaJugada(false);
            }
        });
        setNombreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (estado == EstadoVista.ESPERANDO_USUARIO) {
                    controlador.estaPreparado();
                }
                else {
                    try {
                        String nombre = textbox.getText().trim();
                        if (!controlador.faltanJugadoes()) {printError(ErrorVista.PARTIDA_LLENA);}
                        else if (!controlador.nombreValido(nombre)) {printError(ErrorVista.NOMBRE_TOMADO);}
                        else controlador.agregarJugador(nombre);
                    }
                    catch (RemoteException r) {
                        printError(ErrorVista.CONEXION);
                    }
                }
            }
        });
        setEstado(EstadoVista.INICIALIZANDO);
    }
    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
    @Override
    public void inicioGrafico() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    setSize(1000,500);
                    setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void setManoActual(IMano mano) {
        this.mano = mano;
    }
    @Override
    public void bajadaRechazada() {
        printError(ErrorVista.CONEXION);
        setEstado(EstadoVista.TIRAR_O_BAJAR);
    }
    private int[] sacarCartasNulas(){
        int[] cartasSeleccionadas = new int[cantidadCartasSeleccionadas()];
        int i = 0;
        for (int x = 0; x < indicesCartasSeleccionadas.length; x ++) {
            if (indicesCartasSeleccionadas[x]) {
                cartasSeleccionadas[i] = x;
                i++;
            }
        }
        return cartasSeleccionadas;
    }
    private void agregarCartaJugada(boolean alFinal) {
        String aux = textbox.getText();
        int indiceJugada = convertirANumero(aux);
        int[] indicesCartas = sacarCartasNulas();
        try {
            if (((indiceJugada <= 0) || (indiceJugada >= controlador.getAllJugadas().size())) || (indicesCartas.length != 1)) {
                printError(ErrorVista.JUGADA_RECHAZADA);
            } else controlador.agregarCartaJugada(indiceJugada, indicesCartas[0], alFinal);
        } catch (RemoteException e) {
            printError(ErrorVista.CONEXION);
        }
    }
    private int cantidadCartasSeleccionadas() {
        int cantidad = 0;
        for (boolean indiceCartaSeleccionada : indicesCartasSeleccionadas) {
            if (indiceCartaSeleccionada) {
                cantidad++;
            }
        }
        return cantidad;
    }
    private int convertirANumero(String texto) {
        try {
            return Integer.parseInt(texto);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }
    @Override
    public void mostrarJugadasJugador() {
        try {
            List<IJugada> jugadas = this.controlador.getAllJugadas();
            String jugadorAnterior = null;
            for (IJugada jugada : jugadas) {
                if (jugada.getNombreJugador() != jugadorAnterior) {
                    jugadorAnterior = jugada.getNombreJugador();
                    println("------------Jugador " + jugada.getNombreJugador() + "------------");
                }
                println("{Jugada " + (jugadas.indexOf(jugada) + 1) + "} -------- \n" + jugada.mostrarCartas() + "\n");
            }
        }
        catch (RemoteException e) {
            printError(ErrorVista.CONEXION);
        }
    }
    @Override
    public void mostrarAllJugadas() {
    }
    @Override
    public void setEstado(EstadoVista estado) {
        switch (estado) {
            case INICIALIZANDO -> {
                setEnabledBotonesCarta(false);
                pozoButton.setEnabled(false);
                mazoButton.setEnabled(false);
                alFinalButton.setEnabled(false);
                alInicioButton.setEnabled(false);
                tirarCartaButton.setEnabled(false);
                armarJugadaButton.setEnabled(false);
                bajarseButton.setEnabled(false);
                textbox.setEnabled(true);
                setNombreButton.setEnabled(true);
                this.estado = estado;
            }
            case ESPERANDO_USUARIO -> {
                setEnabledBotonesCarta(false);
                pozoButton.setEnabled(false);
                mazoButton.setEnabled(false);
                alFinalButton.setEnabled(false);
                alInicioButton.setEnabled(false);
                tirarCartaButton.setEnabled(false);
                armarJugadaButton.setEnabled(false);
                bajarseButton.setEnabled(false);
                textbox.setEnabled(false);
                setNombreButton.setEnabled(true);
                this.estado = estado;
            }
            case TOMAR_CARTA -> {
                setEnabledBotonesCarta(false);
                mostrarMano();
                mostrarPozo();
                pozoButton.setEnabled(true);
                mazoButton.setEnabled(true);
                alFinalButton.setEnabled(false);
                alInicioButton.setEnabled(false);
                tirarCartaButton.setEnabled(false);
                armarJugadaButton.setEnabled(false);
                bajarseButton.setEnabled(false);
                textbox.setEnabled(false);
                setNombreButton.setEnabled(false);
                this.estado = estado;
            }
            case TIRAR_O_BAJAR -> {
                setEnabledBotonesCarta(true);
                mazoButton.setEnabled(false);
                pozoButton.setEnabled(false);
                alFinalButton.setEnabled(false);
                alInicioButton.setEnabled(false);
                tirarCartaButton.setEnabled(true);
                armarJugadaButton.setEnabled(true);
                bajarseButton.setEnabled(true);
                textbox.setEnabled(false);
                setNombreButton.setEnabled(false);
                this.estado = estado;
            }
            case BAJADO_DESCARGAR_O_TIRAR -> {
                setEnabledBotonesCarta(true);
                mazoButton.setEnabled(false);
                pozoButton.setEnabled(false);
                alFinalButton.setEnabled(true);
                alInicioButton.setEnabled(true);
                tirarCartaButton.setEnabled(true);
                armarJugadaButton.setEnabled(false);
                bajarseButton.setEnabled(false);
                textbox.setEnabled(true);
                setNombreButton.setEnabled(false);
                this.estado = estado;
            }

        }
    }
    private void setEnabledBotonesCarta(boolean valor) {
        for (JToggleButton boton: botonesCarta) {
            boton.setEnabled(valor);
        }
    }
    @Override
    public void rondaGanada(IJugador jugador) {
        try {
            clearMemo();
            IJugador ganador = this.controlador.getGanador();
            println("EL JUGADOR " + ganador.getNombre() + " HA GANADO! >:)");
            println("Tu puntaje es de: " + jugador.getPuntaje());
        }
        catch (RemoteException e) {
            printError(ErrorVista.CONEXION);
        }
    }
    @Override
    public void jugadaRechazada() {
    }
    @Override
    public void mostrarMano() {
        List<Carta> cartas = mano.getCartas();
        for (int i = 0; i < cartas.size(); i ++) {
            botonesCarta.get(i).setText(cartas.get(i).mostrarCarta());
        }
    }
    @Override
    public void mostrarPozo() {
        pozoButton.setText(this.controlador.getPozo().mostrarCartas());
    }
    @Override
    public void mostrarRonda() {
        println("---------RONDA---------");
        IRonda ronda = this.controlador.getRonda();
        if (ronda != null) {
            println("Jugadas a armar: " + this.controlador.getRonda().mostrarRonda() + "\n");
        }
        printError(ErrorVista.CONEXION);
    }
    @Override
    public void clearMemo() {
        memoTextArea.setText("");
    }
    @Override
    public void clearTextbox() {
        textbox.setText("");
    }
    @Override
    public void println(String texto) {
        clearTextbox();
        memoTextArea.append(texto + "\n");
    }
    @Override
    public void printError(ErrorVista error) {
        println(error.getLabel());
    }
}