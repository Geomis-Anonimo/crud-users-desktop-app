package app.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

import app.controller.UsuarioController;
import app.model.Usuario;

public class AppFrame extends JFrame {
    private UsuarioController controller;
    private UsuarioTable tableModel;
    private JTable tabelaUsuarios;

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;

    private JTextField txtBusca;

    private JButton btnSalvar;
    private JButton btnNovo;
    private JButton btnExcluir;
    private JButton btnLimpar;
    private JButton btnBuscar;

    public AppFrame() {
        controller = new UsuarioController();
        initComponents();
        atualizarTabela();
    }

    private void initComponents() {
        setTitle("CRUD de Usuários com SQLite");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel painelSuperior = criarPainelSuperior();
        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);

        JPanel painelFormulario = criarPainelFormulario();
        painelPrincipal.add(painelFormulario, BorderLayout.WEST);

        JPanel painelTabela = criarPainelTabela();
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painel.setBorder(new TitledBorder("Busca"));

        txtBusca = new JTextField(20);
        txtBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { buscarUsuarios(); }
            @Override
            public void removeUpdate(DocumentEvent e) { buscarUsuarios(); }
            @Override
            public void changedUpdate(DocumentEvent e) { buscarUsuarios(); }
        });

        btnBuscar = new JButton("🔍");
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.addActionListener(e -> buscarUsuarios());

        btnBuscar.setPreferredSize(new Dimension(20, 20));
        btnBuscar.setMargin(new Insets(0, 0, 0, 0));

        painel.add(new JLabel("Buscar por nome:"));
        painel.add(txtBusca);
        painel.add(btnBuscar);

        return painel;
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(new TitledBorder("Formulário de Usuário"));
        painel.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        txtId = new JTextField();
        txtId.setVisible(false);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        painel.add(new JLabel("Nome *:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtNome = new JTextField(15);
        painel.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        painel.add(new JLabel("Email *:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtEmail = new JTextField(15);
        painel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        painel.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.weightx = 1.0;
        txtTelefone = new JTextField(15);
        painel.add(txtTelefone, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0;
        JPanel painelBotoesForm = criarPainelBotoesFormulario();
        painel.add(painelBotoesForm, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1.0;
        painel.add(Box.createVerticalGlue(), gbc);

        return painel;
    }

    private JPanel criarPainelBotoesFormulario() {
        JPanel painel = new JPanel(new GridLayout(1, 4, 5, 5));

        btnNovo = new JButton("Novo");
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");

        btnNovo.setBackground(new Color(70, 130, 180));
        btnSalvar.setBackground(new Color(34, 139, 34));
        btnExcluir.setBackground(new Color(220, 20, 60));
        btnLimpar.setBackground(new Color(211, 211, 211));

        btnNovo.setForeground(Color.WHITE);
        btnSalvar.setForeground(Color.WHITE);
        btnExcluir.setForeground(Color.WHITE);

        btnNovo.addActionListener(e -> novoUsuario());
        btnSalvar.addActionListener(e -> salvarUsuario());
        btnExcluir.addActionListener(e -> excluirUsuario());
        btnLimpar.addActionListener(e -> limparFormulario());

        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);

        return painel;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(new TitledBorder("Lista de Usuários"));

        tableModel = new UsuarioTable();
        tabelaUsuarios = new JTable(tableModel);

        configurarColunasTabela();

        tabelaUsuarios.setRowHeight(25);
        tabelaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaUsuarios.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        tabelaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selecionarUsuarioDaTabela();
            }
        });

        tabelaUsuarios.setToolTipText("Clique em um usuário para editar");

        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    private void configurarColunasTabela() {
        TableColumnModel columnModel = tabelaUsuarios.getColumnModel();

        TableColumn colunaId = columnModel.getColumn(0);
        colunaId.setHeaderValue("ID");
        colunaId.setPreferredWidth(60);
        colunaId.setMinWidth(50);
        colunaId.setMaxWidth(100);
        colunaId.setResizable(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        colunaId.setCellRenderer(centerRenderer);

        TableColumn colunaNome = columnModel.getColumn(1);
        colunaNome.setHeaderValue("Nome");
        colunaNome.setPreferredWidth(200);
        colunaNome.setMinWidth(150);

        TableColumn colunaEmail = columnModel.getColumn(2);
        colunaEmail.setHeaderValue("E-mail");
        colunaEmail.setPreferredWidth(250);
        colunaEmail.setMinWidth(200);

        TableColumn colunaTelefone = columnModel.getColumn(3);
        colunaTelefone.setHeaderValue("Telefone");
        colunaTelefone.setPreferredWidth(150);
        colunaTelefone.setMinWidth(120);

        tabelaUsuarios.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    private void buscarUsuarios() {
        String busca = txtBusca.getText().trim();
        if (busca.isEmpty()) {
            atualizarTabela();
        } else {
            tableModel.setUsuarios(controller.buscarUsuariosPorNome(busca));
        }
    }

    private void selecionarUsuarioDaTabela() {
        int linhaSelecionada = tabelaUsuarios.getSelectedRow();
        if (linhaSelecionada >= 0) {
            Usuario usuario = tableModel.getUsuario(linhaSelecionada);
            if (usuario != null) {
                txtId.setText(String.valueOf(usuario.getId()));
                txtNome.setText(usuario.getNome());
                txtEmail.setText(usuario.getEmail());
                txtTelefone.setText(usuario.getTelefone());
            }
        }
    }

    private void novoUsuario() {
        limparFormulario();
        txtNome.requestFocus();
    }

    private void salvarUsuario() {
        try {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String idStr = txtId.getText().trim();

            if (idStr.isEmpty()) {
                controller.criarUsuario(nome, email, telefone);
                JOptionPane.showMessageDialog(this,
                        "Usuário criado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                int id = Integer.parseInt(idStr);
                boolean sucesso = controller.atualizarUsuario(id, nome, email, telefone);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Usuário atualizado com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erro ao atualizar usuário!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            atualizarTabela();
            limparFormulario();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Validação",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro inesperado: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirUsuario() {
        String idStr = txtId.getText().trim();

        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um usuário para excluir!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este usuário?\nEsta ação não pode ser desfeita.",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(idStr);
                boolean sucesso = controller.excluirUsuario(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Usuário excluído com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                    atualizarTabela();
                    limparFormulario();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erro ao excluir usuário!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erro: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparFormulario() {
        txtId.setText("");
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtBusca.setText("");
        tabelaUsuarios.clearSelection();
    }

    private void atualizarTabela() {
        tableModel.setUsuarios(controller.listarUsuarios());
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");

        JMenuItem menuSair = new JMenuItem("Sair");
        menuSair.addActionListener(e -> System.exit(0));

        menuArquivo.add(menuSair);
        menuBar.add(menuArquivo);
        setJMenuBar(menuBar);
    }
}