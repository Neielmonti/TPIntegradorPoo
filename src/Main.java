import integrador.modelo.Juego;

public class Main {

    public static void main(String[] args) {
        Juego juego = new Juego();

        System.out.println(juego.cartaRandom());

        System.out.println(juego.mostrarMazo());
    }
}
