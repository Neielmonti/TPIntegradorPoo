package programa.utils.observer;

import programa.controlador.Evento;

public interface IObservable {
    public void notificar(Evento evento);

    void agregadorObservador(IObservador observador);
}
