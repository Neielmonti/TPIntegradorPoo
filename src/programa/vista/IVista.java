package programa.vista;
import programa.controlador.Controlador;
public interface IVista {
    public void setControlador(Controlador controlador);
    public void mostrarMano(IMano mano);
    public void iniciar();
}