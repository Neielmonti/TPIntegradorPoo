package programa.vista;

import programa.controlador.Controlador;

import java.util.Scanner;

public class VistaConsola implements IVista {
    private Scanner entrada = new Scanner(System.in);
    private Controlador controlador;
    private boolean seguir = true;
    private boolean onGame = false;
    @Override
    public void mostrarMano(IMano mano) {
        System.out.println("================MANO================");
        System.out.println(mano.mostrarCartas() + "\n");
    }

    public void mostrarConjuntoCarta(IConjuntoCartas cc) {
        System.out.println(cc.mostrarCartas() + "\n");
    }
    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
    @Override
    public void iniciar() {
        while (this.seguir) {
            if (!onGame) {
                //tomarInput();
            }
            else {
                iniciarJuego();
                presioneEnterParaContinuar();
            }
        }
    }
    public void mostrarMenu() {
        System.out.println("-----------MENU PRINCIPAL-----------\n");
        System.out.println("1 - Agregar Jugador");
        System.out.println("2 - Iniciar juego");
        System.out.println("3 - Salir");
        System.out.println("\n");
        System.out.println("Ingrese la opcion que desea elegir");
    }
    public void agregarJugador(){
        System.out.println("Nuevo jugador, ingrese su nombre:\n");
        String in = this.entrada.nextLine();
        //this.controlador.agregarJugador(in);
    }

    public void mostrarRonda(IRonda ronda) {
        System.out.println("JUGADAR POR ARMAR: \n");
        System.out.println(ronda.mostrarRonda() + "\n");
    }

    private void presioneEnterParaContinuar() {
        System.out.println("Presione cualquier tecla para continuar:\n");
        this.entrada.nextLine();
    }

    public void jugadorNoAgregado() {
        System.out.println("[ERROR] El jugador no pudo ser agregado, cantidad maxima alcanzada");
        presioneEnterParaContinuar();
    }

    public void iniciarJuego() {
        System.out.println("INICIANDO JUEGO \n");
    }

    /**
    public void tomarInput() {
        mostrarMenu();
        String in = this.entrada.nextLine();
        switch (in) {
            case "1" -> {
                agregarJugador();
                break;
            }
            case "2" -> {
                if (this.controlador.getPreparadoParaJugar()) {
                    onGame = true;
                }
                else {
                    System.out.println("[ERROR] Cantidad insuficiente de jugadores!");
                    presioneEnterParaContinuar();
                }
                break;
            }
            case "3" -> {
                System.out.println("Gracias Por Jugar!");
                this.seguir = false;
            }
            default -> System.out.println("[ERROR] - Comando no reconocido :/");
        }
    }
     **/

    public boolean tomarDeMazoOPozo() {
        System.out.println("De donde quiere tomar la carta?:");
        System.out.println("1 - del Mazo");
        System.out.println("2 - del Pozo");
        System.out.println("Elija su opcion:");
        String opcion = this.entrada.nextLine();
        switch (opcion) {
            case "1" -> {
                return true;
            }
            case "2" -> {
                return false;
            }
            default -> {
                System.out.println("[ERROR] - Accion no soportada, selecione una opcion valida");
                return tomarDeMazoOPozo();
            }
        }
    }

}//END.
