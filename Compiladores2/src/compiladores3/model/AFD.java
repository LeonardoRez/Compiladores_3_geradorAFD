package compiladores3.model;

import java.util.ArrayList;

public class AFD {

    public Estado q0;
    public ArrayList<Estado> qf;
    public ArrayList<Estado> Q;
    public Alfabeto E;

    public AFD(Estado q0, ArrayList<Estado> qf, ArrayList<Estado> Q, Alfabeto E) {
        this.q0 = q0;
        this.qf = qf;
        this.Q = Q;
        this.E = E;
    }

    public void renomear() {
        for (int i = 0; i < Q.size(); i++) {
            if (Q.get(i).ID != -1) {
                Q.get(i).ID = i;
            }
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

}
