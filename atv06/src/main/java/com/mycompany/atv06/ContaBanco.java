/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atv06;

/**
 *
 * @author curso
 */
public class ContaBanco {
    // ─── ATRIBUTOS ───────────────────────────────────────────────
    public int numConta;
    protected String tipo;   // "CC" = Conta Corrente | "CP" = Conta Poupança
    private String dono;
    private double saldo;
    private boolean status;  // true = conta aberta | false = conta fechada

    // ─── CONSTRUTOR ──────────────────────────────────────────────
    public ContaBanco() {
        this.saldo  = 0.0;
        this.status = false;
    }

    // ─── GETTERS & SETTERS ───────────────────────────────────────
    public int getNumConta() { return numConta; }
    public void setNumConta(int numConta) { this.numConta = numConta; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) {
        if (tipo.equals("CC") || tipo.equals("CP")) {
            this.tipo = tipo;
        } else {
            System.out.println("ERRO: Tipo inválido! Use 'CC' ou 'CP'.");
        }
    }
  public String getDono() { return dono; }
    public void setDono(String dono) { this.dono = dono; }

    public double getSaldo() { return saldo; }
    // Saldo não possui setter público — é controlado pelos métodos funcionais

    public boolean isStatus() { return status; }
    // Status não possui setter público — é controlado por abrirConta/fecharConta

    // ─── MÉTODOS FUNCIONAIS ──────────────────────────────────────

    /**
     * Abre a conta, define o tipo e concede bônus de boas-vindas.
     * CC → R$ 50,00 | CP → R$ 150,00
     */
     /**
     * Abre a conta, define o tipo e concede bônus de boas-vindas.
     * CC → R$ 50,00 | CP → R$ 150,00
     */
    public void abrirConta(String t) {
        if (t.equals("CC") || t.equals("CP")) {
            this.tipo   = t;
            this.status = true;
            this.saldo += (t.equals("CC")) ? 50.0 : 150.0;
            System.out.printf("Conta aberta com sucesso! Bônus de R$ %.2f creditado.%n",
                              t.equals("CC") ? 50.0 : 150.0);
        } else {
            System.out.println("ERRO: Tipo inválido! Use 'CC' ou 'CP'.");
        }
    }

    /**
     * Fecha a conta apenas se o saldo for exatamente zero
     * (sem crédito positivo e sem débito pendente).
     */
    public void fecharConta() {
        if (!status) {
            System.out.println("ERRO: A conta já está fechada.");
            return;
        }
        if (saldo > 0) {
            System.out.printf("ERRO: Retire o saldo de R$ %.2f antes de fechar a conta.%n", saldo);
        } else if (saldo < 0) {
            System.out.printf("ERRO: Quite o débito de R$ %.2f antes de fechar a conta.%n", saldo);
        } else {
            status = false;
            System.out.println("Conta fechada com sucesso.");
        }
    }

    /**
     * Deposita um valor positivo se a conta estiver ativa.
     */
    public void depositar(double v) {
        if (!status) {
            System.out.println("ERRO: Conta inativa. Não é possível depositar.");
            return;
        }
        if (v <= 0) {
            System.out.println("ERRO: O valor do depósito deve ser positivo.");
            return;
        }
        saldo += v;
        System.out.printf("Depósito de R$ %.2f realizado. Saldo atual: R$ %.2f%n", v, saldo);
    }

    /**
     * Saca um valor se a conta estiver ativa e houver saldo suficiente.
     */
    public void sacar(double v) {
        if (!status) {
            System.out.println("ERRO: Conta inativa. Não é possível sacar.");
            return;
        }
        if (v <= 0) {
            System.out.println("ERRO: O valor do saque deve ser positivo.");
            return;
        }
        if (v > saldo) {
            System.out.printf("ERRO: Saldo insuficiente (R$ %.2f) para saque de R$ %.2f.%n", saldo, v);
            return;
        }
        saldo -= v;
        System.out.printf("Saque de R$ %.2f realizado. Saldo atual: R$ %.2f%n", v, saldo);
    }

    /**
     * Desconta a mensalidade conforme o tipo da conta.
     * CC → R$ 12,00 | CP → R$ 20,00
     */
    public void pagarMensalidade() {
        if (!status) {
            System.out.println("ERRO: Conta inativa. Mensalidade não cobrada.");
            return;
        }
        double taxa = tipo.equals("CC") ? 12.0 : 20.0;
        saldo -= taxa;
        System.out.printf("Mensalidade de R$ %.2f descontada. Saldo atual: R$ %.2f%n", taxa, saldo);
    }

    // ─── EXIBIÇÃO ────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
            "╔══════════════════════════════╗%n" +
            "  Conta nº : %d%n" +
            "  Dono     : %s%n" +
            "  Tipo     : %s%n" +
            "  Saldo    : R$ %.2f%n" +
            "  Status   : %s%n" +
            "╚══════════════════════════════╝",
            numConta, dono, tipo, saldo,
            status ? "Ativa ✔" : "Inativa ✘"
        );
    }
    
}
