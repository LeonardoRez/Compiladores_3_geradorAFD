package compiladores3.controller;
//GERADOR DE THOMPSON PARA CRIAR AFN-e
import compiladores3.model.Alfabeto;
import compiladores3.model.AutomatoThomp;
import compiladores3.model.Estado;
import java.util.ArrayList;

public class GeradorThompson {

    public GeradorThompson() {
    }

    public AutomatoThomp gerar(char c) {
        Estado q0 = new Estado(true, false);
        Estado qf = new Estado(false, true);
        q0.addTransicao(c, qf);
        ArrayList<Estado> Q = new ArrayList<>();
        Q.add(q0);
        Q.add(qf);
        return new AutomatoThomp(q0, qf, Q, new Alfabeto("{" + c + "}"));
    }

    public AutomatoThomp unir(AutomatoThomp a, AutomatoThomp b) {
        Estado novoQ0 = new Estado(true, false);
        Estado novoQf = new Estado(false, true);
        novoQ0.addTransicao('&', a.q0);
        novoQ0.addTransicao('&', b.q0);
        a.qf.addTransicao('&', novoQf);
        b.qf.addTransicao('&', novoQf);

        a.q0.isInicial = false;
        a.qf.isFinal = false;
        b.q0.isInicial = false;
        b.qf.isFinal = false;
        AutomatoThomp result = new AutomatoThomp(novoQ0, novoQf, unirEstados(a.Q, b.Q),new Alfabeto(a.E, b.E));
        result.Q.add(0, novoQ0);
        result.Q.add(novoQf);
        return result;
    }

    public AutomatoThomp concatenar(AutomatoThomp a, AutomatoThomp b) {

        ArrayList<Estado> Q = unirEstados(a.Q, b.Q);
        AutomatoThomp result = new AutomatoThomp(a.q0, b.qf, Q,new Alfabeto(a.E, b.E));
        b.q0.isInicial = false; //atualizando estado inicial do automato b
        a.qf.isFinal = false; //atualizando estado final do automato a
        a.qf.addTransicao('&', b.q0); //atualizando a transição de a para b
        return result;
    }

    public AutomatoThomp fecho(AutomatoThomp a) {
        Estado novoQ0 = new Estado(true, false);
        Estado novoQf = new Estado(false, true);
        novoQ0.addTransicao('&', novoQf);
        novoQ0.addTransicao('&', a.q0);
        a.q0.isInicial = false;
        a.qf.isFinal = false;
//        a.qf.t.put('&', new ArrayList<>());
        a.qf.addTransicao('&', a.q0);
        a.qf.addTransicao('&', novoQf);
        AutomatoThomp result = new AutomatoThomp(novoQ0, novoQf, new ArrayList<>(a.Q),a.E);
        result.Q.add(0, novoQ0);
        result.Q.add(novoQf);

        return result;
    }

    private ArrayList<Estado> unirEstados(ArrayList<Estado> e1, ArrayList<Estado> e2) {
        e1.removeAll(e2);
        e1.addAll(e2);
        return e1;
    }
}
