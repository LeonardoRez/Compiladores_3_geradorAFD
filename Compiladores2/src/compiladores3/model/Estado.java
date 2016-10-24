package compiladores3.model;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Estado implements Comparable {

    private static int IDcontroller;
    public int ID;
    public StringProperty idProp;
    public boolean isInicial, isFinal;
    public HashMap<Character, ArrayList<Estado>> t;

    public Estado() {
        t = new HashMap<>();
        ID = IDcontroller++;
    }

    public Estado(boolean isInicial, boolean isFinal) {
        ID = IDcontroller++;
        this.isInicial = isInicial;
        this.isFinal = isFinal;
        t = new HashMap<>();
    }

    public void addTransicao(char c, Estado q) {
        if (t.containsKey(c)) {
            if (!t.get(c).contains(q)) {
                t.get(c).add(q);
            }
        } else {
            t.put(c, new ArrayList<>());
            t.get(c).add(q);
        }
    }

    @Override
    public String toString() {
        String s = "Q" + ID;
        if (isFinal) {
            s = "*" + s;
        }
        if (isInicial) {
            s = "->" + s;
        }
        return s;
    }

    public StringProperty toStringProp() {
        return new SimpleStringProperty(toString());
    }

    public String getTransicoes(char c) {
        ArrayList<Estado> lista = t.get(c);
        if (lista == null) {
            return "{}";
        }
        String s = "{" + lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            s = s + "," + lista.get(i);
        }
        s += "}";
        return s;

    }

    public StringProperty getTransicoesProp(char c) {
        return new SimpleStringProperty(getTransicoes(c));
    }

    public static void main(String[] args) {
        Estado a = new Estado();
        Estado b = new Estado();
        Estado c = new Estado();
//        a.addTransicao('a', b);
//        a.addTransicao('a', c);
        System.out.println(a.getTransicoes('a'));
    }

    @Override
    public int compareTo(Object outroEstado) {
        if (this.ID > ((Estado) outroEstado).ID) //vai comparar o atributo "simbolo" (que nesse caso é o único atributo)
        {
            return 1;
        } else {
            return -1;
        }
    }
}
