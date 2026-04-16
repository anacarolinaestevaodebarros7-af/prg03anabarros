/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.atv07;

/**
 *
 * @author curso
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SistemaPagamento extends JFrame {

    private JComboBox<String> comboPagamento;
    private JTextField txtValor;
    private JTextArea txtRecibo;
    private JButton btnCalcular;
    private JButton btnLimpar;

    public SistemaPagamento() {
        setTitle("Sistema de Pagamentos - POO");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelPrincipal.setBackground(new Color(245, 245, 250));

        // Título
        JLabel lblTitulo = new JLabel("Sistema de Pagamentos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(40, 40, 100));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Painel de entrada
        JPanel painelEntrada = new JPanel(new GridLayout(3, 2, 10, 10));
        painelEntrada.setBackground(new Color(245, 245, 250));
        painelEntrada.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 180)),
                "Dados do Pagamento"));

        JLabel lblTipoPagamento = new JLabel("Tipo de pagamento:");
        lblTipoPagamento.setFont(new Font("Arial", Font.PLAIN, 14));

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

        painelEntrada.add(lblTipoPagamento);
        painelEntrada.add(comboPagamento);
        painelEntrada.add(lblValor);
        painelEntrada.add(txtValor);
        painelEntrada.add(btnCalcular);
        painelEntrada.add(btnLimpar);

        painelPrincipal.add(painelEntrada, BorderLayout.CENTER);

        // Área do recibo
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

        // Ações dos botões
        btnCalcular.addActionListener(e -> calcularPagamento());
        btnLimpar.addActionListener(e -> limpar());

        // Enter no campo de valor também calcula
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

        // Cria o objeto dinamicamente conforme o tipo escolhido
        int opcao = comboPagamento.getSelectedIndex();
        Pagamento pagamento;

        switch (opcao) {
            case 0:
                pagamento = new PagamentoDinheiro(valor);
                break;
            case 1:
                pagamento = new PagamentoCartao(valor);
                break;
            case 2:
                pagamento = new PagamentoPix(valor);
                break;
            default:
                return;
        }

        // Captura o recibo como texto
        String recibo = gerarReciboTexto(pagamento, valor, opcao);
        txtRecibo.setText(recibo);

        // Também imprime no console
        pagamento.imprimirRecibo();
    }

    private String gerarReciboTexto(Pagamento pagamento, double valorOriginal, int opcao) {
        String[] tipos = {"DINHEIRO", "CARTÃO", "PIX"};
        String tipo = tipos[opcao];

        double totalFinal = pagamento.calcularTotal();
        double diferenca = Math.abs(totalFinal - valorOriginal);
        String labelDiferenca;
        String[] labels = {"Desconto (10%)", "Taxa (5%)", "Cashback (2%)"};
        labelDiferenca = labels[opcao];

        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════╗\n");
        sb.append(String.format("║   RECIBO - PAGAMENTO VIA %-12s║\n", tipo));
        sb.append("╠══════════════════════════════════════╣\n");
        sb.append(String.format("║  Valor original : R$ %15.2f   ║\n", valorOriginal));
        sb.append(String.format("║  %-16s : R$ %15.2f   ║\n", labelDiferenca, diferenca));
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
        SwingUtilities.invokeLater(() -> {
            new SistemaPagamento().setVisible(true);
        });
    }
}
