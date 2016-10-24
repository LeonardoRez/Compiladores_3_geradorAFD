package compiladores3.controller;
//CONVERTE INFIXA PARA POSFIXA
import compiladores3.model.AutomatoThomp;
import compiladores3.model.Estado;
import compiladores3.model.Simbolo;
import java.util.Stack;

public class Conversor{

    public String converter(String exp) {
        Stack<Character> pilha = new Stack<>();
        String result = "";
        boolean flag = false;//controlar operandos consecutivos que impliquem em "."
        for (int i = 0; i < exp.length(); i++) { //ETAPA 1
            if (charValid(exp.charAt(i))) { //caso I
                if (flag) { //se o concatenador está implícito
                    while (!pilha.empty()) {
                        if (pilha.peek() == '*' || pilha.peek() == '.') {
                            result += pilha.pop();
                        } else {
                            break;
                        }
                    }
                    pilha.push('.');
                    result += exp.charAt(i);
                    flag = true;
                } else { //se é só um operando sem '.' antes
                    result += exp.charAt(i);
                    flag = true;
                }
            } else if (exp.charAt(i) == '(') {//caso II
                if (flag) {
                    while (!pilha.empty()) {
                        if (pilha.peek() == '*' || pilha.peek() == '.') {
                            result += pilha.pop();
                        } else {
                            break;
                        }
                    }
                    pilha.push('.');
                    flag = false;
                }
                pilha.push('(');
            } else if (exp.charAt(i) == ')') {//caso III
                while (pilha.peek() != '(') {
                    result += pilha.pop(); //desempilha até chegar no '('
                }
                pilha.pop(); //descarta o '('
                flag = true;
            } else if (isOperador(exp.charAt(i))) { //caso IV
                switch (exp.charAt(i)) {
                    case '+':
                        flag = false;
                        while (!pilha.empty()) {
                            if (pilha.peek() == '*' || pilha.peek() == '.' || pilha.peek() == '+') {
                                result += pilha.pop();
                            } else {
                                break;
                            }
                        }
                        pilha.push('+');
                        break;
                    case '.':
                        flag = false;
                        while (!pilha.empty()) {
                            if (pilha.peek() == '*' || pilha.peek() == '.') {
                                result += pilha.pop();
                            } else {
                                break;
                            }
                        }
                        pilha.push('.');
                        break;
                    case '*':
                        while (!pilha.empty()) {
                            if (pilha.peek() == '*') {
                                result += pilha.pop();
                            } else {
                                break;
                            }
                        }
                        pilha.push('*');
                        flag = true;
                        break;
                }
            }
        }
        while (!pilha.empty()) {  //ETAPA 2
            if (pilha.peek() != '(') {
                result += pilha.pop(); //limpa a pilha, colocando os operadores restantes na expressão
            } else {
                throw new Error("Um '(' sobrou na pilha!");
            }
        }
        return result;
    }

    public AutomatoThomp calcAutomato(String s) { //Versão com pilha de autômatos
        AutomatoThomp op1, op2;
        GeradorThompson gerador = new GeradorThompson();
        Stack<AutomatoThomp> pilha = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (charValid(s.charAt(i))) {
                pilha.push(gerador.gerar(s.charAt(i)));
            } else //binario
            {
                if (s.charAt(i) == '+') {
                    op2 = pilha.pop();
                    if (!pilha.empty()) {
                        op1 = pilha.pop();
                        pilha.push(gerador.unir(op1, op2));
                    } else {
                        throw new IllegalAccessError("Operando não encontrado");
                    }
                } else if (s.charAt(i) == '.') {
                    op2 = pilha.pop();
                    if (!pilha.empty()) {
                        op1 = pilha.pop();
                        pilha.push(gerador.concatenar(op1, op2));
                    } else {
                        throw new IllegalAccessError("Operando não encontrado");
                    }

                } else {
                    op1 = pilha.pop();
                    pilha.push(gerador.fecho(op1));
                }
            }
        }
        op1 = pilha.pop();
        if (pilha.empty()) {
            op1.renomear();
            return op1;
        }
        return null;
    }

    private static boolean charValid(char c) { //testa se é um símbolo válido
        if ((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122) || c == '&') {
            return true;
        }
        return false;
    }

    private static boolean isOperador(char c) { //testa se é operador
        if (c == '.' || c == '+' || c == '*') {
            return true;
        }
        return false;
    }

    public static void main(String[] args) { //main para testes
        Conversor c = new Conversor();
        String r = c.converter("a+bc*");
        System.out.println(r);
        try {
            AutomatoThomp result = c.calcAutomato(r);
            if (result != null) {
                result.renomear();
                //titulos das colunas
                System.out.print("Q's");
                for(Simbolo s : result.E.getAlfabeto()){
                        System.out.print("\t"+s);
                 }
                System.out.print("\t"+'&');
                
                //linhas (estados)
                for (Estado q : result.Q) {
                    System.out.print("\n"+q);
                    for(Simbolo s : result.E.getAlfabeto()){
                        System.out.print("\t"+q.getTransicoes(s.getSimbolo()));
                    }
                    System.out.print("\t"+q.getTransicoes('&'));
                }
            } else {
                System.out.println("DEU RUIM");
            }
        } catch (Throwable e) {
            System.out.println("oloco");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
