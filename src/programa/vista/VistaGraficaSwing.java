package programa.vista;
import programa.controlador.Controlador;
import programa.modelo.conjuntoCarta.Carta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.util.*;
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
    private Queue<String> indiceCartasSeleccionadas = new LinkedList<>();
    public VistaGraficaSwing() {
        super("Carioca App");
        setContentPane(this.panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                controlador.quitarJugador();
            }
        });
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
//        Arrays.fill(indicesCartasSeleccionadas, false);
        c1ToggleButton.setFocusable(false);
        c1ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 1) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("0");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("0");
                        }
                    }
                }
            }
        });
        c2ToggleButton.setFocusable(false);
        c2ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 2) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("1");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("1");
                        }
                    }
                }
            }
        });
        c3ToggleButton.setFocusable(false);
        c3ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 3) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("2");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("2");
                        }
                    }
                }
            }
        });
        c4ToggleButton.setFocusable(false);
        c4ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 4) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("3");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("3");
                        }
                    }
                }
            }
        });
        c5ToggleButton.setFocusable(false);
        c5ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 5) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("4");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("4");
                        }
                    }
                }
            }
        });
        c6ToggleButton.setFocusable(false);
        c6ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 6) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("5");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("5");
                        }
                    }
                }
            }
        });
        c7ToggleButton.setFocusable(false);
        c7ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 7) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("6");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("6");
                        }
                    }
                }
            }
        });
        c8ToggleButton.setFocusable(false);
        c8ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 8) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("7");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("7");
                        }
                    }
                }
            }
        });
        c9ToggleButton.setFocusable(false);
        c9ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 9) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("8");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("8");
                        }
                    }
                }
            }
        });
        c10ToggleButton.setFocusable(false);
        c10ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 10) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("9");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("9");
                        }
                    }
                }
            }
        });
        c11ToggleButton.setFocusable(false);
        c11ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 11) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("10");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("10");
                        }
                    }
                }
            }
        });
        c12ToggleButton.setFocusable(false);
        c12ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 12) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("11");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("11");
                        }
                    }
                }
            }
        });
        c13ToggleButton.setFocusable(false);
        c13ToggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (mano.getCantidadCartas() >= 13) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        indiceCartasSeleccionadas.add("12");
                    } else {
                        if (ev.getStateChange() == ItemEvent.DESELECTED) {
                            indiceCartasSeleccionadas.remove("12");
                        }
                    }
                }
            }
        });
        armarJugadaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indicesCartas = sacarCartasNulas();
                resetBotonesCartas();
                controlador.armarJugada(indicesCartas);
            }
        });
        tirarCartaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indicesCartas = sacarCartasNulas();
                resetBotonesCartas();
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
                        if (nombre.equals("")) {printError(ErrorVista.NOMBRE_INVALIDO);}
                        else if (!controlador.faltanJugadores()) {printError(ErrorVista.PARTIDA_LLENA);}
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
                    setSize(940,300);
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
    private int[] sacarCartasNulas() {
        int[] retorno = new int[indiceCartasSeleccionadas.size()];
        System.out.println("CARTAS: \n");
        for (int i = 0; i < retorno.length; i++) {
            System.out.println(indiceCartasSeleccionadas.peek());
            retorno[i] = Integer.parseInt(indiceCartasSeleccionadas.remove());
        }
        System.out.println("\n");
        return retorno;
    }
    /**
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
    private int cantidadCartasSeleccionadas() {
        int cantidad = 0;
        for (boolean indiceCartaSeleccionada : indicesCartasSeleccionadas) {
            if (indiceCartaSeleccionada) {
                cantidad++;
            }
        }
        return cantidad;
    }
     **/
    private void agregarCartaJugada(boolean alFinal) {
        String aux = textbox.getText();
        clearTextbox();
        int indiceJugada = convertirANumero(aux);
        int[] indicesCartas = sacarCartasNulas();
        resetBotonesCartas();
        try {
            if ((indiceJugada <= 0) || (indiceJugada > controlador.getAllJugadas().size()) || (indicesCartas.length != 1)) {
                printError(ErrorVista.JUGADA_RECHAZADA);
            } else controlador.agregarCartaJugada(indiceJugada-1, indicesCartas[0], alFinal);
        } catch (RemoteException e) {
            printError(ErrorVista.CONEXION);
        }
    }
    private int convertirANumero(String texto) {
        try {
            return Integer.parseInt(texto);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }
    private void resetBotonesCartas() {
        for (JToggleButton boton: botonesCarta) {
            boton.setSelected(false);
        }
        //Arrays.fill(indicesCartasSeleccionadas, false);
        indiceCartasSeleccionadas = new LinkedList<>();
    }
    @Override
    public void mostrarJugadasJugador() {
        List<IJugada> jugadas = new ArrayList<>(this.controlador.getJugadasJugador());
        for (IJugada jugada: jugadas) {
            println("[Jugada " + (jugadas.indexOf(jugada) + 1) + "] -------- \n" + jugada.mostrarCartas() + "\n");
        }
    }
    @Override
    public void mostrarAllJugadas() {
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
    public void setEstado(EstadoVista estado) {
        switch (estado) {
            case ESPERANDO_TURNO -> {
                setEnabledBotonesCarta(false);
                pozoButton.setEnabled(false);
                mazoButton.setEnabled(false);
                alFinalButton.setEnabled(false);
                alInicioButton.setEnabled(false);
                tirarCartaButton.setEnabled(false);
                armarJugadaButton.setEnabled(false);
                bajarseButton.setEnabled(false);
                textbox.setEnabled(false);
                setNombreButton.setEnabled(false);
                this.estado = estado;
                println(estado.getLabel());
            }
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
                println("Ingrese su nombre de usuario");
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
                setNombreButton.setText("Estoy listo!");
                this.estado = estado;
                println(estado.getLabel());
            }
            case TOMAR_CARTA -> {
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
                resetBotonesCartas();
                setEnabledBotonesCarta(false);
                this.estado = estado;
            }
            case TIRAR_O_BAJAR -> {
                mazoButton.setEnabled(false);
                pozoButton.setEnabled(false);
                alFinalButton.setEnabled(false);
                alInicioButton.setEnabled(false);
                tirarCartaButton.setEnabled(true);
                armarJugadaButton.setEnabled(true);
                bajarseButton.setEnabled(true);
                textbox.setEnabled(false);
                setNombreButton.setEnabled(false);
                //setEnabledBotonesCarta(true);
                resetBotonesCartas();
                mostrarMano();
                setEnabledBotonesCarta(true);
                this.estado = estado;
                println("Tire una carta o bajese");
            }
            case BAJADO_DESCARGAR_O_TIRAR -> {
                mazoButton.setEnabled(false);
                pozoButton.setEnabled(false);
                alFinalButton.setEnabled(true);
                alInicioButton.setEnabled(true);
                tirarCartaButton.setEnabled(true);
                armarJugadaButton.setEnabled(false);
                bajarseButton.setEnabled(false);
                textbox.setEnabled(true);
                setNombreButton.setEnabled(false);
                resetBotonesCartas();
                setEnabledBotonesCarta(true);
                this.estado = estado;
                println("Ingrese el nro de la jugada donde va a descargar, la carta a descargar, y elija si al principio o al final de la jugada");
            }

        }
    }
    private void setEnabledBotonesCarta(boolean valor) {
        for (JToggleButton boton: botonesCarta) {
            boton.setEnabled((valor) && (!boton.getText().equals("")));
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
        printError(ErrorVista.JUGADA_RECHAZADA);
    }
    @Override
    public void mostrarMano() {
        if (mano == null) {
            println("MANO NULA MANO NULA MANO NULA");
            System.out.println("MANO NULA MANO NULA MANO NULA");
        }
        else {
            List<Carta> cartas = mano.getCartas();
            for (int i = 0; i < botonesCarta.size(); i++) {
                if (i < cartas.size()) {
                    Carta carta = cartas.get(i);
                    botonesCarta.get(i).setText(carta.mostrarCarta());
                } else {
                    botonesCarta.get(i).setText("");
                }
            }
            resetBotonesCartas();
        }
    }
    @Override
    public void mostrarPozo() {
        IConjuntoCartas pozo = this.controlador.getPozo();
        if (pozo != null) {
            pozoButton.setText(pozo.mostrarCartas());
        }
    }
    @Override
    public void mostrarRonda() {
        println("---------RONDA---------");
        IRonda ronda = this.controlador.getRonda();
        if (ronda != null) {
            println("Jugadas a armar: " + this.controlador.getRonda().mostrarRonda() + "\n");
        }
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