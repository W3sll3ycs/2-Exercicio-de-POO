package model;

public class ContaCorrente {
    private static final float LIMITE_OPERACAO = 10000f;
    private int numero;
    private String titular;
    private float saldo;

    public ContaCorrente(int numero, String titular, float saldo) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
    }

    public boolean sacar(float valor) {
        if (valor <= 0) {
            System.out.println("Erro: o valor do saque deve ser positivo.");
            return false;
        }
        if (valor > LIMITE_OPERACAO) {
            System.out.println("Erro: o limite por saque é de R$ 10000,00.");
            return false;
        }
        if (valor > saldo) {
            System.out.println("Erro: saldo insuficiente.");
            return false;
        }
        saldo -= valor;
        System.out.println("Saque realizado com sucesso.");
        return true;
    }

    public boolean depositar(float valor) {
        if (valor <= 0) {
            System.out.println("Erro: o valor do depósito deve ser positivo.");
            return false;
        }
        if (valor > LIMITE_OPERACAO) {
            System.out.println("Erro: o limite por depósito é de R$ 10000,00.");
            return false;
        }
        saldo += valor;
        System.out.println("Depósito realizado com sucesso.");
        return true;
    }

    public float consultarSaldo() {
        return saldo;
    }

    public int getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }
}
