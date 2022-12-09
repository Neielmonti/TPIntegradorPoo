package programa.vista;
import java.lang.Character;
import programa.controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
public class VistaConsolaSwing extends JFrame implements IVista {
    private JTextArea memoPrincipal;
    private JTextField textbox;
    private JButton buttEnter;
    private JPanel panelPrincipal;
    private IMano manoActual;
    private EstadoVista estado = EstadoVista.INICIALIZANDO;
    private Controlador controlador;
    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
    public VistaConsolaSwing() {
        super("Carioca App");
        setContentPane(this.panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                controlador.quitarJugador();
            }
        });
        buttEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    switchButton();
                }
                catch (RemoteException a) {
                    printError(ErrorVista.CONEXION);
                }
            }
        });
    }
    @Override
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
        println("Ingrese su nombre de jugador \n");
    }
    public void switchButton() throws RemoteException {
        switch (estado) {
            case ESPERANDO_TURNO -> clearTextbox();
            case ESPERANDO_USUARIO -> this.controlador.estaPreparado();
            case ESPERANDO_JUGADORES -> {
                clearMemo();
                println(this.estado.getLabel());
            }
            case INICIALIZANDO -> {
                String nombre = textbox.getText().trim();
                if (!controlador.faltanJugadoes()) {printError(ErrorVista.PARTIDA_LLENA);}
                else if (!controlador.nombreValido(nombre.trim())) {printError(ErrorVista.NOMBRE_TOMADO);}
                else {controlador.agregarJugador(nombre.trim());}
                clearTextbox();
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
                    if (this.controlador.jugadorYaBajo()) {
                        setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                    }
                    else setEstado(EstadoVista.TIRAR_O_BAJAR);
                } else {
                    try {
                        int indiceCarta = Integer.parseInt(in);
                        if ((indiceCarta <= 0) || (indiceCarta > this.manoActual.getCantidadCartas())) {
                            println("Carta fuera de rango");
                        }
                        else this.controlador.tirarCartaAlPozo(indiceCarta - 1);
                    }
                    catch (NumberFormatException e) {
                        println("Por favor, ingrese el indice de la carta a tirar");
                    }
                }
            }
            case BAJAR -> {
                String in = textbox.getText().trim();
                if (in.equals(OpcionVista.ARMAR_JUEGO.getLabel())) {
                    setEstado(EstadoVista.ARMANDO_JUGADA);
                }
                else if (in.equals(OpcionVista.BAJARSE.getLabel())) {
                    this.controlador.verificarJugadas();
                }
                else if (in.equals(OpcionVista.CANCELAR.getLabel())) {
                    setEstado(EstadoVista.TIRAR_O_BAJAR);
                    controlador.deshacerJugadas();
                }
                else printError(ErrorVista.ACCION_NO_RECONOCIDA);
            }
            case ARMANDO_JUGADA -> {
                String in = textbox.getText().trim();
                if (in.equals(OpcionVista.CANCELAR.getLabel())) {
                    setEstado(EstadoVista.BAJAR);
                }
                else if (!verificarCartasJugada(in)) {
                    println("Por favor, solo ingrese numeros y '-' ");
                }
                else {
                    String[] aux = in.split("-");
                    try {
                        int[] indices = convertirAIndices(aux);
                        if (indices != null) {
                            this.controlador.armarJugada(indices);
                        } else printError(ErrorVista.FUERA_DE_RANGO);
                    }
                    catch (NumberFormatException e) {
                        printError(ErrorVista.ACCION_NO_RECONOCIDA);
                    }
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
                if (in.equals(OpcionVista.CANCELAR.getLabel())) {
                    setEstado(EstadoVista.BAJADO_DESCARGAR_O_TIRAR);
                    break;
                }
                else if (aux.length != 3)  {
                    println("Por favor, ingrese los parametros necesarios");
                    break;
                }
                try {
                    int indiceCarta = Integer.parseInt(aux[0]);
                    int indiceJugada = Integer.parseInt(aux[1]);
                    List<IJugada> jugadas = this.controlador.getAllJugadas();

                    if ((indiceCarta > manoActual.getCantidadCartas()) || (indiceCarta < 1)) {
                        println("Carta fuera de rango, la mano tiene solamente: " + manoActual.getCantidadCartas());
                    } else if ((indiceJugada < 1) || (indiceJugada > jugadas.size())) {
                        println("Jugada fuera de rango");
                    } else {
                        if (aux[2].equals(OpcionVista.FINAL.getLabel())) {
                            this.controlador.agregarCartaJugada(indiceJugada - 1, indiceCarta - 1, true);
                        } else if (aux[2].equals(OpcionVista.INICIO.getLabel())) {
                            this.controlador.agregarCartaJugada(indiceJugada - 1, indiceCarta - 1, false);
                        }
                    }
                }
                catch (NumberFormatException e) {
                    printError(ErrorVista.ACCION_NO_RECONOCIDA);
                }
            }
        }
    }
    public int[] convertirAIndices(String[] texto) {
        int[] indices = new int[texto.length];
        int cantCartas = manoActual.getCantidadCartas();
        for (int i = 0; i < texto.length; i++) {
            int a = Integer.parseInt(texto[i]);
            if ((a > cantCartas) || (a < 1)) {
                return null;
            } else indices[i] = (a - 1);
        }
        return indices;
    }
    @Override
    public void setManoActual(IMano mano) {
        this.manoActual = mano;
    }
    @Override
    public void bajadaRechazada() {
        printError(ErrorVista.JUGADAS_INVALIDAS);
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
        this.estado = estado;
        println(estado.getLabel() + "\n");
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
    @Override
    public void jugadaRechazada() {
        printError(ErrorVista.JUGADA_RECHAZADA);
    }
    @Override
    public void mostrarMano() {
        //IMano mano = this.controlador.getMano();
        println("---------MANO---------" + "\n" + manoActual.mostrarCartas() + "\n");
    }
    @Override
    public void mostrarPozo() {
        println("---------POZO---------");
        println(this.controlador.getPozo().mostrarCartas() + "\n");
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
        memoPrincipal.setText("");
    }
    @Override
    public void clearTextbox() {
        this.textbox.setText("");
    }
    @Override
    public void println(String texto) {
        clearTextbox();
        memoPrincipal.append(texto + "\n");
    }
    @Override
    public void printError(ErrorVista error) {
        println(error.getLabel());
    }
}