/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.atv06;

/**
 *
 * @author curso
 */
public class Atv06 {
    public static void main(String[] args) {

        System.out.println("=== TESTE DA CLASSE ContaBanco ===\n");

        ContaBanco c1 = new ContaBanco();
        c1.setNumConta(1001);
        c1.setDono("Maria Silva");

        // Abre conta corrente → bônus R$ 50
        c1.abrirConta("CC");

        // Operações
        c1.depositar(500.00);
        c1.sacar(100.00);
        c1.pagarMensalidade();

        System.out.println(c1);

        System.out.println("\n--- Tentando fechar com saldo positivo ---");
        c1.fecharConta();
         System.out.println("\n--- Zerando saldo e fechando ---");
        c1.sacar(c1.getSaldo());
        c1.fecharConta();

        System.out.println(c1);

        // ── Conta Poupança ──────────────────────────────────────
        System.out.println("\n=== CONTA POUPANÇA ===\n");

        ContaBanco c2 = new ContaBanco();
        c2.setNumConta(2002);
        c2.setDono("João Ferreira");
        c2.abrirConta("CP");   // bônus R$ 150
        c2.depositar(1000.00);
        c2.pagarMensalidade(); // desconto R$ 20
        System.out.println(c2);
    }
} 