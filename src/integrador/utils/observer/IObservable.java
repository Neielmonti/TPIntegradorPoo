package integrador.utils.observer;

public interface IObservable {
    public void notificar(Object evento);

    public void agregadorObservador(IObservador observador);
}
