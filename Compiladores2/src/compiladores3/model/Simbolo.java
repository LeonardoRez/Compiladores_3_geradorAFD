package compiladores3.model;
public class Simbolo implements Comparable{ //para poder ordenar conjuntos de "símbolos"
    private char simbolo;

    public Simbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    @Override
    public String toString() {
        return ""+simbolo;
    }

    @Override
    public int compareTo(Object outroSimbolo) {
        if(this.simbolo > ((Simbolo)outroSimbolo).getSimbolo()) //vai comparar o atributo "simbolo" (que nesse caso é o único atributo)
            return 1;
        else
            return -1;
    }

    
}
