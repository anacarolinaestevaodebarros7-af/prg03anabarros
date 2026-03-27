package br.com.ifba.atividade03.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

/**
 * TelaIdade - Interface gráfica para cálculo de idade.
 * Pacote: br.com.ifba.atividade03.view
 *
 * As imagens devem estar em:
 *   src/br/com/ifba/atividade03/images/avatar.png
 *   src/br/com/ifba/atividade03/images/calculadora.png
 */
public class TelaIdade extends JFrame {

    // ── Componentes da interface ──────────────────────────────────────────────
    private JSpinner spinnerAno;
    private JButton  btnCalcular;
    private JLabel   lblIdadeValor;
    private JLabel   lblAvatar;

    // ── Construtor ────────────────────────────────────────────────────────────
    public TelaIdade() {
        super("Calculadora de Idade");
        initComponents();
        initEventos();
        configurarJanela();
    }

    // ── Inicialização dos componentes visuais ─────────────────────────────────
    private void initComponents() {

        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(new Color(245, 247, 250));
        setContentPane(painelPrincipal);

        // ── Avatar (lado esquerdo) ────────────────────────────────────────────
        lblAvatar = new JLabel();
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvatar.setPreferredSize(new Dimension(120, 120));

        ImageIcon iconAvatar = carregarImagem(
                "/br/com/ifba/atividade03/images/avatar.png", 100, 100);
        if (iconAvatar != null) {
            lblAvatar.setIcon(iconAvatar);
        } else {
            // Fallback: placeholder caso a imagem não seja encontrada
            lblAvatar.setText("👤");
            lblAvatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        }
        painelPrincipal.add(lblAvatar, BorderLayout.WEST);

        // ── Painel central (formulário) ───────────────────────────────────────
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Título
        JLabel lblTitulo = new JLabel("Calculadora de Idade");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(40, 55, 80));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painelForm.add(lblTitulo, gbc);

        // Label "Ano de Nascimento"
        JLabel lblAno = new JLabel("Ano de Nascimento:");
        lblAno.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 1; gbc.gridwidth = 1;
        painelForm.add(lblAno, gbc);

        // JSpinner — intervalo 1900 → ano atual, sem separador de milhar
        int anoAtual = LocalDate.now().getYear();
        SpinnerNumberModel modeloAno = new SpinnerNumberModel(
                anoAtual - 25,  // valor inicial
                1900,           // mínimo
                anoAtual,       // máximo
                1               // passo
        );
        spinnerAno = new JSpinner(modeloAno);
        spinnerAno.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinnerAno.setPreferredSize(new Dimension(120, 32));
        ((JSpinner.NumberEditor) spinnerAno.getEditor())
                .getFormat().setGroupingUsed(false);
        gbc.gridx = 1; gbc.gridy = 1;
        painelForm.add(spinnerAno, gbc);

        // Label "Idade"
        JLabel lblIdade = new JLabel("Idade:");
        lblIdade.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(lblIdade, gbc);

        // Label de resultado
        lblIdadeValor = new JLabel("--");
        lblIdadeValor.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblIdadeValor.setForeground(new Color(30, 120, 220));
        gbc.gridx = 1; gbc.gridy = 2;
        painelForm.add(lblIdadeValor, gbc);

        // Botão Calcular
        btnCalcular = new JButton("Calcular");
        btnCalcular.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCalcular.setBackground(new Color(30, 120, 220));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFocusPainted(false);
        btnCalcular.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCalcular.setPreferredSize(new Dimension(140, 38));

        ImageIcon iconCalc = carregarImagem(
                "/br/com/ifba/atividade03/images/calculadora.png", 20, 20);
        if (iconCalc != null) {
            btnCalcular.setIcon(iconCalc);
        }

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill   = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        painelForm.add(btnCalcular, gbc);

        painelPrincipal.add(painelForm, BorderLayout.CENTER);
    }

    // ── Registro de eventos ───────────────────────────────────────────────────
    private void initEventos() {
        btnCalcular.addActionListener((ActionEvent e) -> calcularIdade());
    }

    // ── Lógica de negócio ─────────────────────────────────────────────────────
    private void calcularIdade() {
        int anoNascimento = (Integer) spinnerAno.getValue();
        int anoAtual      = LocalDate.now().getYear();
        int idade         = anoAtual - anoNascimento;

        if (idade < 0) {
            JOptionPane.showMessageDialog(this,
                    "Ano de nascimento inválido.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lblIdadeValor.setText(idade + " anos");
    }

    // ── Utilitário: carrega e redimensiona ImageIcon ──────────────────────────
    /**
     * Carrega uma imagem do classpath — funciona em IDE e em JAR exportado.
     *
     * @param caminho  Caminho absoluto no classpath, ex: "/br/com/ifba/.../img.png"
     * @param largura  Largura desejada em pixels
     * @param altura   Altura desejada em pixels
     * @return ImageIcon redimensionado, ou null se o recurso não for encontrado
     */
    private ImageIcon carregarImagem(String caminho, int largura, int altura) {
        java.net.URL url = getClass().getResource(caminho);
        if (url == null) return null;
        Image img = new ImageIcon(url).getImage()
                        .getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // ── Configuração final da janela ──────────────────────────────────────────
    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null); // centraliza na tela
    }

    // ── Ponto de entrada ──────────────────────────────────────────────────────
    public static void main(String[] args) {

        // 1. Aplicar Look and Feel nativo — multi-catch (Java 7+)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException
               | InstantiationException
               | IllegalAccessException
               | UnsupportedLookAndFeelException e) {

            System.err.println("[WARN] Falha ao aplicar Look and Feel nativo: "
                    + e.getClass().getSimpleName() + " — " + e.getMessage());
            e.printStackTrace();
            // Fallback automático: Swing usa o Metal L&F padrão
        }

        // 2. Iniciar a GUI na Event Dispatch Thread (thread safety do Swing)
        SwingUtilities.invokeLater(() -> new TelaIdade().setVisible(true));
    }
}