package integrador.utils.observer;

public interface IObservador {
    public void actualizar(Object evento, IObservable observado);
}
