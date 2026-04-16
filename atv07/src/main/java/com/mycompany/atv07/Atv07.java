/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atv07;

import javax.swing.*;
import java.awt.*;

public class Atv07 extends JFrame {

    private JComboBox<String> comboPagamento;
    private JTextField txtValor;
    private JTextArea txtRecibo;
    private JButton btnCalcular;
    private JButton btnLimpar;

    public Atv07() {
        setTitle("Sistema de Pagamentos - POO");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelPrincipal.setBackground(new Color(245, 245, 250));

        JLabel lblTitulo = new JLabel("Sistema de Pagamentos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(40, 40, 100));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelEntrada = new JPanel(new GridLayout(3, 2, 10, 10));
        painelEntrada.setBackground(new Color(245, 245, 250));
        painelEntrada.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 180)),
                "Dados do Pagamento"));

        JLabel lblTipo = new JLabel("Tipo de pagamento:");
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 14));

        String[] opcoes = {"Dinheiro (10% desconto)", "Cartão (5% taxa)", "Pix (2% cashback)"};
        comboPagamento = new JComboBox<>(opcoes);
        comboPagamento.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblValor = new JLabel("Valor da compra (R$):");
        lblValor.setFont(new Font("Arial", Font.PLAIN, 14));

        txtValor = new JTextField();
        txtValor.setFont(new Font("Arial", Font.PLAIN, 14));

        btnCalcular = new JButton("Calcular e Gerar Recibo");
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 13));
        btnCalcular.setBackground(new Color(60, 120, 200));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFocusPainted(false);
        btnCalcular.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(new Font("Arial", Font.PLAIN, 13));
        btnLimpar.setBackground(new Color(200, 80, 80));
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFocusPainted(false);
        btnLimpar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painelEntrada.add(lblTipo);
        painelEntrada.add(comboPagamento);
        painelEntrada.add(lblValor);
        painelEntrada.add(txtValor);
        painelEntrada.add(btnCalcular);
        painelEntrada.add(btnLimpar);

        painelPrincipal.add(painelEntrada, BorderLayout.CENTER);

        txtRecibo = new JTextArea(10, 40);
        txtRecibo.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtRecibo.setEditable(false);
        txtRecibo.setBackground(new Color(30, 30, 40));
        txtRecibo.setForeground(new Color(0, 230, 100));
        txtRecibo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        txtRecibo.setText("O recibo aparecerá aqui...");

        JScrollPane scroll = new JScrollPane(txtRecibo);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 180)), "Recibo"));

        painelPrincipal.add(scroll, BorderLayout.SOUTH);

        btnCalcular.addActionListener(e -> calcularPagamento());
        btnLimpar.addActionListener(e -> limpar());
        txtValor.addActionListener(e -> calcularPagamento());

        add(painelPrincipal);
    }

    private void calcularPagamento() {
        String valorTexto = txtValor.getText().trim().replace(",", ".");

        if (valorTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, informe o valor da compra.",
                    "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            txtValor.requestFocus();
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorTexto);
            if (valor <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Valor inválido! Digite um número positivo (ex: 150.00).",
                    "Erro de entrada", JOptionPane.ERROR_MESSAGE);
            txtValor.selectAll();
            txtValor.requestFocus();
            return;
        }

        int opcao = comboPagamento.getSelectedIndex();
        Pagamento pagamento;

        switch (opcao) {
            case 0:  pagamento = new PagamentoDinheiro(valor); break;
            case 1:  pagamento = new PagamentoCartao(valor);   break;
            default: pagamento = new PagamentoPix(valor);      break;
        }

        txtRecibo.setText(gerarReciboTexto(pagamento, valor, opcao));
        pagamento.imprimirRecibo();
    }

    private String gerarReciboTexto(Pagamento pagamento, double valorOriginal, int opcao) {
        String[] tipos  = {"DINHEIRO", "CARTÃO", "PIX"};
        String[] labels = {"Desconto (10%)", "Taxa (5%)     ", "Cashback (2%) "};

        double totalFinal = pagamento.calcularTotal();
        double diferenca  = Math.abs(totalFinal - valorOriginal);

        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════╗\n");
        sb.append(String.format("║   RECIBO - PAGAMENTO VIA %-12s║\n", tipos[opcao]));
        sb.append("╠══════════════════════════════════════╣\n");
        sb.append(String.format("║  Valor original : R$ %15.2f   ║\n", valorOriginal));
        sb.append(String.format("║  %s: R$ %15.2f   ║\n", labels[opcao], diferenca));
        sb.append("╠══════════════════════════════════════╣\n");
        sb.append(String.format("║  TOTAL A PAGAR  : R$ %15.2f   ║\n", totalFinal));
        sb.append("╚══════════════════════════════════════╝\n");
        return sb.toString();
    }

    private void limpar() {
        txtValor.setText("");
        comboPagamento.setSelectedIndex(0);
        txtRecibo.setText("O recibo aparecerá aqui...");
        txtValor.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Atv07().setVisible(true));
    }
}