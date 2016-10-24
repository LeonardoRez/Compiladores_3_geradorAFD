package compiladores3.model;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import compiladores3.controller.Conversor;
import java.util.ArrayList;
import java.util.Collections;

public class AutomatoThomp {

    public Estado q0, qf; //só um estado final, já que os autômatos serão resultados do 
    //algoritmo de thompson
    public ArrayList<Estado> Q;
    public Alfabeto E;

    public AutomatoThomp(Estado q0, Estado qf, ArrayList<Estado> Q, Alfabeto E) {
        this.q0 = q0;
        this.qf = qf;
        this.Q = Q;
        this.E = E;
    }

    public void renomear() {
        for (int i = 0; i < Q.size(); i++) {
            Q.get(i).ID = i;
        }
    }

    private Estado getEstado(int estado) {
        for (Estado e : Q) {
            if (e.ID == estado) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<Estado> getFeicho(Estado e) {
        ArrayList<Estado> visitados = new ArrayList<>();
        ArrayList<Estado> aindaFalta = new ArrayList<>();
        ArrayList<Estado> fecho = new ArrayList<>();
        fecho.add(e);
        visitados.add(e);
        if (e.t.get('&') != null) {
            for (Estado k : e.t.get('&')) {
                if (!fecho.contains(k)) {
                    fecho.add(k);
                    aindaFalta.add(k);
                }
            }
            int i = aindaFalta.size();
            while (i > 0) {
                ArrayList<Estado> aux = aindaFalta.get(0).t.get('&');
                visitados.add(aindaFalta.get(0));
                aindaFalta.remove(0);
                if (aux != null) {
                    for (Estado k : aux) {
                        if (!fecho.contains(k)) {
                            fecho.add(k);
                            aindaFalta.add(k);
                        }
                    }
                }
                i = aindaFalta.size();
            }
        }
        return fecho;
    }

    public ArrayList<Estado>[] getAllFeichos() {
        ArrayList<Estado> result[] = new ArrayList[Q.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = getFeicho(Q.get(i));
        }
        return result;
    }

    private void UniFeichos(ArrayList<Estado> e, ArrayList<Estado> novo) {
        if (novo != null) {
            for (Estado q : novo) {
                for (Estado k : getFeicho(q)) {
                    if (!e.contains(k)) {
                        e.add(k);
                    }
                }
            }
        }
    }

    public static Estado novoEstadoAFD(ArrayList<Estado> aux) { //aux = estado novo composto por varios fechos
        Collections.sort(aux);
        String s = "";
        boolean inicial = false;
        boolean eFinal = false;
        for (Estado e : aux) {
            if (e.isInicial) {
                inicial = true;
            }
            if (e.isFinal) {
                eFinal = true;
            }
            s += e.ID;
        }
        Estado temp = new Estado(inicial, eFinal);
        if (s.compareTo("") == 0) {
            s = "-1";
        }
        temp.ID = Integer.parseInt(s);
        return temp;

    }

    private boolean listaNovaNAOContem(ArrayList<EstadoTemp> a, Estado e) {
        for (EstadoTemp q : a) {
            if (q.e.ID == e.ID) {
                return false;
            }
        }
        return true;
    }

    public AFD transformaAFD() {
        ArrayList<EstadoTemp> listaNova = new ArrayList<>();
        
        listaNova.add(new EstadoTemp(getFeicho(q0)));
        int i = 0;
        while (i < listaNova.size()) {
            EstadoTemp atual = listaNova.get(i);
            for (Simbolo s : E.getAlfabeto()) {
                ArrayList<Estado> aux = new ArrayList<>();
                for (Estado q : listaNova.get(i).versaoAFN) {
                    UniFeichos(aux, q.t.get(s.getSimbolo()));
                }
                EstadoTemp e = new EstadoTemp(aux);
                if (listaNovaNAOContem(listaNova, e.e)) {
                    listaNova.add(e);
                }
                atual.e.addTransicao(s.getSimbolo(), e.e);
            }

            i++;
        }
        ArrayList<Estado> finais = new ArrayList<>();
        ArrayList<Estado> conjEstados = new ArrayList<>();
        for (i = 0; i < listaNova.size(); i++) {
            conjEstados.add(listaNova.get(i).e);
            if (listaNova.get(i).e.isFinal) {
                finais.add(listaNova.get(i).e);
            }
        }
        AFD result = new AFD(q0, finais, conjEstados, E);
        return result;
    }

    public static void main(String[] args) {
        Conversor c = new Conversor();
        AutomatoThomp a = c.calcAutomato(c.converter("a+b*"));
        AFD teste = a.transformaAFD();
        teste.renomear();
        for (Estado e : teste.Q) {
            System.out.println(e);
        }

    }
}

class EstadoTemp {

    public Estado e;
    public ArrayList<Estado> versaoAFN;

    public EstadoTemp(ArrayList<Estado> versaoAFN) {
        this.e = AutomatoThomp.novoEstadoAFD(versaoAFN);
        this.versaoAFN = versaoAFN;
    }

}
