package integrador.controlador;

import integrador.modelo.Juego;
import integrador.utils.observer.IObservable;
import integrador.utils.observer.IObservador;
import integrador.vista.IVista;

public class Controlador implements IObservador {

    private Juego modelo;

    private IVista vista;
    @Override
    public void actualizar(Object evento, IObservable observado) {

    }
}
