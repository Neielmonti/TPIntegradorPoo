package programa.utils.observer;

import programa.controlador.Evento;

public interface IObservador {
    public void actualizar(Evento evento, IObservable observado);
}
