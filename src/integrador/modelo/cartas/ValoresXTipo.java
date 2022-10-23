package integrador.modelo.cartas;
import java.util.ArrayList;
import java.util.List;

public class ValoresXTipo {
    private final List<ValorXTipo> valores;

    ValoresXTipo() {
        List<ValorXTipo> list = new ArrayList<>();
        ValorXTipo aux = new ValorXTipo(TipoCarta.A,20);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.DOS,2);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.TRES,3);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.CUATRO,4);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.CINCO,5);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.SEIS,6);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.SIETE,7);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.OCHO,8);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.NUEVE,9);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.DIEZ,10);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.J,10);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.Q,10);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.K,10);
        list.add(aux);
        aux = new ValorXTipo(TipoCarta.JOKER,50);
        list.add(aux);
        this.valores = list;
    }

    public int valorXTipo(TipoCarta tipo) {
        int i = 0;
        int respuesta = 0;
        while (i < this.valores.size()) {
            if (tipo.equals(this.valores.get(i).getTipo())) {
                respuesta = this.valores.get(i).getValor();
            }
            i++;
        }
        return respuesta;
    }
}
