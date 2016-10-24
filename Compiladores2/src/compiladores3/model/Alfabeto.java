package compiladores3.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alfabeto {

    private List<Simbolo> alfabeto;

    public Alfabeto() {
        alfabeto = new ArrayList<>();
    }

    public Alfabeto(Alfabeto a, Alfabeto b) {
        alfabeto = new ArrayList<>();
        a.getAlfabeto().stream().forEach(this::addSimbolo);
        b.getAlfabeto().stream().forEach(this::addSimbolo);
        Collections.sort(alfabeto);

    }

    public Alfabeto(String conjunto) {
        alfabeto = new ArrayList<>();
        setAlfabeto(conjunto);
    }

    public List<Simbolo> getAlfabeto() {
        return alfabeto;
    }

    public void addSimbolo(char C) {
        if (!(temSimbolo(C))) {
            alfabeto.add(new Simbolo(C));//adiciona o símbolo na lista
        }
    }

    public void addSimbolo(Simbolo C) {
        if (!(temSimbolo(C.getSimbolo()))) {
            alfabeto.add(C);//adiciona o símbolo na lista
        }
    }

    public void setAlfabeto(String conjunto) {
        clrAlfabeto();
        int Q = 0;
        for (int i = 0; i < conjunto.length(); i++) {
            Q = automato(Q, conjunto.charAt(i));
            if (Q == -1) {
                break;
            }
        }
        if (Q != 3) {
            clrAlfabeto();
            throw new IllegalArgumentException("O alfabeto " + conjunto + " é inválido.");
        } else {
            Collections.sort(alfabeto);
        }

    }

    protected int automato(int Q, char C) { //testa a String de entrada pra validar o alfabeto
        switch (Q) {
            case 0:
                if (C == ' ') {
                    return 0;
                } else if (C == '{') {
                    return 1;
                } else {
                    return -1; //estado de erro
                }

            case 1:
                if (C == ' ') {
                    return 1;
                } else if (isValid(C)) {
                    addSimbolo(C);
                    return 2;
                } else {
                    return -1;//estado de erro
                }
            case 2:
                if (C == ' ') {
                    return 2;
                } else if (C == ',') {
                    return 4;
                } else if (isValid(C)) {
                    return -1; //estado de erro
                } else if (C == '}') {
                    return 3; //estado final
                } else {
                    return -1; //estado de erro
                }
            case 3:
                if (C == ' ') {
                    return 3; //estado final
                } else {
                    return -1; //estado de erro
                }
            case 4:
                if (C == ' ') {
                    return 4;
                } else if (isValid(C)) {
                    addSimbolo(C);
                    return 2;
                } else {
                    return -1; //estado de erro
                }
            case -1:
                return -1; //estado de erro

        }
        return -1;
    }

    public boolean isValid(char C) { //confere se o símbolo é alfanumérico ou vazio
        return C=='&' ||(C >= '0' && C <= '9') || (C >= 'A' && C <= 'Z') || (C >= 'a' && C <= 'z');
    }

    public boolean temSimbolo(char c) {
        int i;
        for (i = 0; i < alfabeto.size(); i++) {
            if (alfabeto.get(i).getSimbolo() == c) {
                break;
            }
        }
        return (i < alfabeto.size());
    }

    public boolean palavraPertence(String palavra) {
        for (int i = 0; i < palavra.length(); i++) {
            if (!temSimbolo(palavra.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("{");
        for (Simbolo s1 : alfabeto) {
            s.append(s1.toString() + ",");
        }
        s.setCharAt((s.length() - 1), '}');
        return s.toString();
    }

    public StringProperty toStringProp() {
        StringBuilder s = new StringBuilder("{");
        for (Simbolo s1 : alfabeto) {
            s.append(s1.toString() + ",");
        }
        s.setCharAt((s.length() - 1), '}');
        StringProperty temp = new SimpleStringProperty(s.toString());
        return temp;
    }

    public void clrAlfabeto() {
        this.alfabeto = new ArrayList<>();
    }

    public boolean compareTo(Alfabeto b) {
        boolean igual;
        Alfabeto a = this;
        if (a.getAlfabeto().size() < b.getAlfabeto().size()) { //garantindo que o alfabeto a tem mais simbolos que b
            Alfabeto aux = a;
            a = b;
            b = aux;
        }
        for (int i = 0; i < a.getAlfabeto().size(); i++) {
            igual = false;
            for (int j = 0; j < b.getAlfabeto().size(); j++) {
                if (a.getAlfabeto().get(i).getSimbolo() == b.getAlfabeto().get(j).getSimbolo()) {
                    igual = true;
                    break;
                }
            }
            if (!igual) {
                return false;
            }
        }
        return true;

    }
}
