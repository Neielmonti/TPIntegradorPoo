package integrador.modelo;

public class Jugador {
    private String nombre;
    private int puntaje;
    private boolean bajo;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
        this.bajo = false;
    }

    public int getPuntaje() {
        return this.puntaje;
    }

    public boolean yaBajo() {
        return this.bajo;
    }

    public void agregarPuntos(int puntos) {
        if (puntos > 0) {
            this.puntaje += puntos;
        }
    }
}
