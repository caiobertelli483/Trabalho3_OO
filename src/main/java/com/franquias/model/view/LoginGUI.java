package com.franquias.view;

import com.franquias.model.Dono;
import com.franquias.model.Gerente;
import com.franquias.model.Usuario;
import com.franquias.model.Vendedor;
import com.franquias.service.UsuarioService;
import com.franquias.utils.DataManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class LoginGUI extends JFrame {
   private JTextField cpfField;
   private JPasswordField senhaField;
   private JLabel statusLabel;
   private UsuarioService usuarioService;

   public LoginGUI() {
      this.initializeServices();
      this.setupUI();
      this.createDefaultUsers();
   }

   private void initializeServices() {
      this.usuarioService = new UsuarioService();
      List<Usuario> usuarios = DataManager.carregarUsuarios();
   }

   private void setupUI() {
      this.setTitle("Sistema de Franquias - Login");
      this.setDefaultCloseOperation(3);
      this.setSize(400, 300);
      this.setLocationRelativeTo((Component)null);
      this.setResizable(false);
      JPanel mainPanel = new JPanel();
      mainPanel.setLayout(new BorderLayout());
      mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
      JLabel titleLabel = new JLabel("\ud83c\udfe2 Sistema de Franquias", 0);
      titleLabel.setFont(new Font("Arial", 1, 18));
      titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
      mainPanel.add(titleLabel, "North");
      JPanel formPanel = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.anchor = 13;
      formPanel.add(new JLabel("CPF ou Email:"), gbc);
      gbc.gridx = 1;
      gbc.gridy = 0;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      this.cpfField = new JTextField(15);
      this.cpfField.setToolTipText("Digite seu CPF ou email");
      formPanel.add(this.cpfField, gbc);
      gbc.gridx = 0;
      gbc.gridy = 1;
      gbc.anchor = 13;
      gbc.fill = 0;
      gbc.weightx = 0.0;
      formPanel.add(new JLabel("Senha:"), gbc);
      gbc.gridx = 1;
      gbc.gridy = 1;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      this.senhaField = new JPasswordField(15);
      formPanel.add(this.senhaField, gbc);
      gbc.gridx = 0;
      gbc.gridy = 2;
      gbc.gridwidth = 2;
      gbc.fill = 2;
      gbc.insets = new Insets(15, 5, 5, 5);
      JButton loginButton = new JButton("\ud83d\udd10 Entrar");
      loginButton.setFont(new Font("Arial", 1, 14));
      loginButton.setBackground(new Color(0, 123, 255));
      loginButton.setForeground(Color.WHITE);
      loginButton.setFocusPainted(false);
      loginButton.addActionListener((e) -> {
         this.realizarLogin();
      });
      formPanel.add(loginButton, gbc);
      KeyListener enterListener = new 1(this);
      this.cpfField.addKeyListener(enterListener);
      this.senhaField.addKeyListener(enterListener);
      mainPanel.add(formPanel, "Center");
      this.statusLabel = new JLabel(" ", 0);
      this.statusLabel.setFont(new Font("Arial", 0, 12));
      mainPanel.add(this.statusLabel, "South");
      JPanel infoPanel = new JPanel();
      infoPanel.setLayout(new BoxLayout(infoPanel, 1));
      infoPanel.setBorder(BorderFactory.createTitledBorder("Usuários de Teste"));
      JLabel info1 = new JLabel("\ud83d\udc51 Dono: CPF: 11144477735 | Senha: dono123");
      JLabel info2 = new JLabel("\ud83d\udc68\u200d\ud83d\udcbc Gerente: CPF: 22233344456 | Senha: gerente123");
      JLabel info3 = new JLabel("\ud83d\uded2 Vendedor: CPF: 33344455567 | Senha: vendedor123");
      info1.setFont(new Font("Monospaced", 0, 10));
      info2.setFont(new Font("Monospaced", 0, 10));
      info3.setFont(new Font("Monospaced", 0, 10));
      infoPanel.add(info1);
      infoPanel.add(info2);
      infoPanel.add(info3);
      Container container = this.getContentPane();
      container.setLayout(new BorderLayout());
      container.add(mainPanel, "Center");
      container.add(infoPanel, "South");
   }

   private void createDefaultUsers() {
      if (this.usuarioService.getTodosUsuarios().isEmpty()) {
         try {
            Dono dono = this.usuarioService.criarDono("João Silva", "11144477735", "joao@franquia.com", "dono123");
            Gerente gerente = this.usuarioService.criarGerente("Maria Santos", "22233344456", "maria@franquia.com", "gerente123");
            Vendedor vendedor = this.usuarioService.criarVendedor("Pedro Costa", "33344455567", "pedro@franquia.com", "vendedor123");
            List<Usuario> todosUsuarios = new ArrayList(this.usuarioService.getTodosUsuarios().values());
            DataManager.salvarUsuarios(todosUsuarios);
            this.showStatus("✅ Usuários de teste criados com sucesso!", false);
         } catch (Exception var5) {
            this.showStatus("❌ Erro ao criar usuários: " + var5.getMessage(), true);
         }
      }

   }

   private void realizarLogin() {
      String emailOuCpf = this.cpfField.getText().trim();
      String senha = new String(this.senhaField.getPassword());
      if (!emailOuCpf.isEmpty() && !senha.isEmpty()) {
         try {
            Usuario usuario = this.usuarioService.autenticarUsuario(emailOuCpf, senha);
            if (usuario != null) {
               this.showStatus("✅ Login realizado com sucesso!", false);
               this.abrirTelaPrincipal(usuario);
            } else {
               this.showStatus("❌ CPF/Email ou senha inválidos!", true);
               this.senhaField.setText("");
            }
         } catch (Exception var4) {
            this.showStatus("❌ Erro no login: " + var4.getMessage(), true);
         }

      } else {
         this.showStatus("❌ Por favor, preencha todos os campos!", true);
      }
   }

   private void abrirTelaPrincipal(Usuario usuario) {
      SwingUtilities.invokeLater(() -> {
         (new TelaPrincipalGUI(usuario)).setVisible(true);
         this.dispose();
      });
   }

   private void showStatus(String message, boolean isError) {
      this.statusLabel.setText(message);
      this.statusLabel.setForeground(isError ? Color.RED : new Color(0, 128, 0));
      Timer timer = new Timer(3000, (e) -> {
         this.statusLabel.setText(" ");
      });
      timer.setRepeats(false);
      timer.start();
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         (new LoginGUI()).setVisible(true);
      });
   }
}
