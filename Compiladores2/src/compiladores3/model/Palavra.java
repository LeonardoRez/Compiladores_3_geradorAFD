package compiladores3.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Palavra {

    String palavra;
    Comparator<String> comp; //vai ter o método usado para organizar strings
    //por tamanho e depois por ordem alfabética.

    public Palavra() {
        palavra = "";
        configComp();

    }

    public Palavra(String palavra) {
        if (verificaSimbolos(palavra)) {
            this.palavra = palavra;
        } else {
            this.palavra = "";
        }
        configComp();
    }

    public void setPalavra(String palavra) {
        if (verificaSimbolos(palavra)) {
            this.palavra = palavra;
        }
    }

    public String getPalavra() {
        return palavra;
    }

    private boolean verificaSimbolos(String s) {
        Alfabeto aux = new Alfabeto();
        for (int i = 0; i < s.length(); i++) {
            if (!aux.isValid(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private void configComp() {
        comp = new Comparator<String>() { //sobrecrevendo função padrão que compara String.
            @Override            // Agora ela organiza primeiro por tambanho e depois por ordem alfabética.
            public int compare(String s1, String s2) {
                if (s1.length() < s2.length()) {
                    return -1;
                } else if (s1.length() > s2.length()) {
                    return 1;
                }
                return s1.compareTo(s2);
            }
        };
    }

    public List<String> getPrefixos() {//adicina numa lista
        List<String> prefixos = new ArrayList<>();
        int len = palavra.length();
        String saida;
        for (int i = 0; i < len; i++) {
            saida = "";
            for (int j = 0; j <= i; j++) {
                saida += palavra.charAt(j);
            }
            if (!hasPalavra(prefixos, saida)) {
                prefixos.add(saida);
            }
        }

        Collections.sort(prefixos, comp);

        char t = 0x00EA;
        prefixos.add(0, "" + t);
        return prefixos;
    }

    public void getPrefixos(boolean printar) { //lista os prefixos da palavra da classe
        int len = palavra.length();
        String saida;
        for (int i = 0; i < len; i++) {
            saida = "";
            for (int j = 0; j <= i; j++) {
                saida += palavra.charAt(j);
            }
            System.out.println(saida);
        }
    }

    public void getSufixos(boolean printar) {//da palavra da calsse
        String saida;
        for (int i = palavra.length() - 1; i > -1; i--) {
            saida = "";
            for (int j = i; j > -1; j--) {
                saida += palavra.charAt(j);
            }
            System.out.println(saida);

        }
    }

    public List<String> getSufixos() {//da palavra da calsse
        List<String> sufixos = new ArrayList<>();
        String saida;
        for (int i = 0; i < palavra.length(); i++) {
            saida = "";
            for (int j = i; j <palavra.length(); j++) {
                saida += palavra.charAt(j);
            }
            if (!hasPalavra(sufixos, saida)) {
                sufixos.add(saida);
            }

        }

        Collections.sort(sufixos, comp);

        char t = 0x00EA;
        sufixos.add(0, "" + t);
        return sufixos;
    }

    public List<String> getSubPalavras() {
        List<String> subP = new ArrayList<>();
        String temp;
        for (int i = 0; i < this.palavra.length(); i++) { //i aponta do inicio pro final da palavra
            for (int j = (this.palavra.length()); j > i; j--) {//j aponta do final para o início
                temp = this.palavra.substring(i, j); //colhe a substring que vai de i a j
                if (!hasPalavra(subP, temp)) { //adiciona na lista se não for repetido
                    subP.add(temp);
                }
            }
        }
        Collections.sort(subP, comp);

        char t = 0x00EA;
        subP.add(0, "" + t);

        return subP;
    }

    public boolean hasPalavra(List<String> lista, String palavra) { //usada para não repetir uma palavra
        for (String s : lista) {                                 //em uma lista de prefixos, etc
            if (s.compareTo(palavra) == 0) {
                return true;
            }
        }
        return false;
    }
}
