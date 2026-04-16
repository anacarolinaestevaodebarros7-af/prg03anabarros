/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atv07;

/**
 *
 * @author curso
 */
public class PagamentoDinheiro implements Pagamento {
    private double valor;

    public PagamentoDinheiro(double valor) {
        this.valor = valor;
    }

    @Override
    public double calcularTotal() {
        return valor * 0.90; // 10% de desconto
    }

    @Override
    public void imprimirRecibo() {
        System.out.println("=== RECIBO - PAGAMENTO EM DINHEIRO ===");
        System.out.printf("Valor original: R$ %.2f%n", valor);
        System.out.printf("Desconto (10%%): R$ %.2f%n", valor * 0.10);
        System.out.printf("Total a pagar:  R$ %.2f%n", calcularTotal());
        System.out.println("======================================");
    }
}