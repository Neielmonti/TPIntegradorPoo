package integrador.vista;

import integrador.controlador.Controlador;


public interface IVista {
    public void setControlador(Controlador controlador);
    public void mostrarMano(IMano mano);
    public void iniciar();
}
