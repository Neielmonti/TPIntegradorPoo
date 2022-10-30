package integrador.modelo.commons;

public enum Formacion {
    TRIO(3),
    ESCALA(4),
    ESCALERA_SUCIA(13),
    ESCALERA_REAL(13);

    private int cantCartas;

    Formacion(int cantCartas) {
        this.cantCartas = cantCartas;
    }

    public int getCantCartas() {
        return this.cantCartas;
    }
}
