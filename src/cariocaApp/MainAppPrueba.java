package cariocaApp;
public class MainAppPrueba {
    public static void main(String[] args) {
        AppServidor s = new AppServidor();
        AppCliente a1 = new AppCliente();
        AppCliente a2 = new AppCliente();
        AppCliente a3 = new AppCliente();
        s.iniciar();
        a1.inicioGrafico();
        a2.inicioGrafico();
        a3.inicioGrafico();
    }
}