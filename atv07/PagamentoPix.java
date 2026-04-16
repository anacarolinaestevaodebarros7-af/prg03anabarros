/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.atv07;

public class PagamentoPix implements Pagamento {
    private double valor;

    public PagamentoPix(double valor) {
        this.valor = valor;
    }

    @Override
    public double calcularTotal() {
        return valor * 0.98; // 2% de cashback (desconto no total)
    }

    @Override
    public void imprimirRecibo() {
        System.out.println("=== RECIBO - PAGAMENTO VIA PIX ===");
        System.out.printf("Valor original: R$ %.2f%n", valor);
        System.out.printf("Cashback (2%%): R$ %.2f%n", valor * 0.02);
        System.out.printf("Total a pagar: R$ %.2f%n", calcularTotal());
        System.out.println("===================================");
    }
}