package com.franquias.view;

import com.franquias.model.Dono;
import com.franquias.model.Gerente;
import com.franquias.model.Usuario;
import com.franquias.model.Vendedor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TelaPrincipalGUI extends JFrame {
   private Usuario usuarioLogado;
   private JLabel welcomeLabel;
   private JPanel functionsPanel;

   public TelaPrincipalGUI(Usuario usuario) {
      this.usuarioLogado = usuario;
      this.setupUI();
      this.setupFunctionsByUserType();
   }

   private void setupUI() {
      this.setTitle("Sistema de Franquias - " + this.usuarioLogado.getTipoUsuario());
      this.setDefaultCloseOperation(3);
      this.setSize(600, 400);
      this.setLocationRelativeTo((Component)null);
      JPanel mainPanel = new JPanel(new BorderLayout());
      mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
      JPanel headerPanel = new JPanel(new BorderLayout());
      this.welcomeLabel = new JLabel("Bem-vindo, " + this.usuarioLogado.getNome() + "!", 0);
      this.welcomeLabel.setFont(new Font("Arial", 1, 18));
      JLabel userTypeLabel = new JLabel("Perfil: " + this.usuarioLogado.getTipoUsuario(), 0);
      userTypeLabel.setFont(new Font("Arial", 0, 14));
      userTypeLabel.setForeground(Color.BLUE);
      headerPanel.add(this.welcomeLabel, "North");
      headerPanel.add(userTypeLabel, "Center");
      headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
      this.functionsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
      JPanel footerPanel = new JPanel(new FlowLayout());
      JButton logoutButton = new JButton("\ud83d\udeaa Sair");
      logoutButton.addActionListener((e) -> {
         this.logout();
      });
      footerPanel.add(logoutButton);
      mainPanel.add(headerPanel, "North");
      mainPanel.add(this.functionsPanel, "Center");
      mainPanel.add(footerPanel, "South");
      this.add(mainPanel);
   }

   private void setupFunctionsByUserType() {
      if (this.usuarioLogado instanceof Dono) {
         this.setupDonoFunctions();
      } else if (this.usuarioLogado instanceof Gerente) {
         this.setupGerenteFunctions();
      } else if (this.usuarioLogado instanceof Vendedor) {
         this.setupVendedorFunctions();
      }

   }

   private void setupDonoFunctions() {
      this.addFunctionButton("\ud83d\udc51 Gerenciar Franquias", "Criar, editar e remover franquias", () -> {
         this.showMessage("Funcionalidade: Gerenciar Franquias", "Dono");
      });
      this.addFunctionButton("\ud83d\udc65 Gerenciar Usuários", "Criar, editar e remover usuários", () -> {
         this.showMessage("Funcionalidade: Gerenciar Usuários", "Dono");
      });
      this.addFunctionButton("\ud83d\udcca Relatórios Gerais", "Visualizar relatórios completos", () -> {
         this.showMessage("Funcionalidade: Relatórios Gerais", "Dono");
      });
      this.addFunctionButton("\ud83d\udcb0 Gestão Financeira", "Controle financeiro das franquias", () -> {
         this.showMessage("Funcionalidade: Gestão Financeira", "Dono");
      });
      this.addFunctionButton("⚙️ Configurações", "Configurações do sistema", () -> {
         this.showMessage("Funcionalidade: Configurações", "Dono");
      });
      this.addFunctionButton("\ud83d\udcc8 Dashboard", "Visão geral do negócio", () -> {
         this.showMessage("Funcionalidade: Dashboard", "Dono");
      });
   }

   private void setupGerenteFunctions() {
      this.addFunctionButton("\ud83d\uded2 Gerenciar Produtos", "Adicionar, editar e remover produtos", () -> {
         this.showMessage("Funcionalidade: Gerenciar Produtos", "Gerente");
      });
      this.addFunctionButton("\ud83d\udc65 Gerenciar Vendedores", "Cadastrar e gerenciar vendedores", () -> {
         this.showMessage("Funcionalidade: Gerenciar Vendedores", "Gerente");
      });
      this.addFunctionButton("\ud83d\udccb Relatórios de Vendas", "Visualizar relatórios de vendas", () -> {
         this.showMessage("Funcionalidade: Relatórios de Vendas", "Gerente");
      });
      this.addFunctionButton("\ud83d\udce6 Controle de Estoque", "Gerenciar estoque de produtos", () -> {
         this.showMessage("Funcionalidade: Controle de Estoque", "Gerente");
      });
      this.addFunctionButton("\ud83d\udc64 Perfil", "Visualizar e editar perfil", () -> {
         this.showUserProfile();
      });
   }

   private void setupVendedorFunctions() {
      this.addFunctionButton("\ud83d\udecd️ Realizar Vendas", "Processar vendas para clientes", () -> {
         this.showMessage("Funcionalidade: Realizar Vendas", "Vendedor");
      });
      this.addFunctionButton("\ud83d\udc65 Gerenciar Clientes", "Cadastrar e gerenciar clientes", () -> {
         this.showMessage("Funcionalidade: Gerenciar Clientes", "Vendedor");
      });
      this.addFunctionButton("\ud83d\udccb Consultar Produtos", "Visualizar catálogo de produtos", () -> {
         this.showMessage("Funcionalidade: Consultar Produtos", "Vendedor");
      });
      this.addFunctionButton("\ud83d\udcca Minhas Vendas", "Visualizar minhas vendas realizadas", () -> {
         this.showMessage("Funcionalidade: Minhas Vendas", "Vendedor");
      });
      this.addFunctionButton("\ud83d\udc64 Meu Perfil", "Visualizar meu perfil", () -> {
         this.showUserProfile();
      });
   }

   private void addFunctionButton(String title, String description, Runnable action) {
      JPanel buttonPanel = new JPanel(new BorderLayout());
      buttonPanel.setBorder(BorderFactory.createRaisedBevelBorder());
      JButton button = new JButton("<html><div style='text-align: center;'><b>" + title + "</b><br><small>" + description + "</small></div></html>");
      button.setPreferredSize(new Dimension(200, 60));
      button.addActionListener((e) -> {
         action.run();
      });
      buttonPanel.add(button, "Center");
      this.functionsPanel.add(buttonPanel);
   }

   private void showMessage(String functionality, String userType) {
      JOptionPane.showMessageDialog(this, "✅ " + functionality + " executada com sucesso!\n\nUsuário: " + this.usuarioLogado.getNome() + " (" + userType + ")\nPermissões verificadas: " + (this.usuarioLogado.temPermissao("READ") ? "✅" : "❌"), "Funcionalidade Executada", 1);
   }

   private void showUserProfile() {
      StringBuilder profile = new StringBuilder();
      profile.append("\ud83d\udc64 PERFIL DO USUÁRIO\n");
      profile.append("═══════════════════════\n\n");
      profile.append("Nome: ").append(this.usuarioLogado.getNome()).append("\n");
      profile.append("CPF: ").append(this.usuarioLogado.getCpf()).append("\n");
      profile.append("Email: ").append(this.usuarioLogado.getEmail()).append("\n");
      profile.append("Tipo: ").append(this.usuarioLogado.getTipoUsuario()).append("\n\n");
      profile.append("\ud83d\udd10 PERMISSÕES:\n");
      profile.append("Leitura: ").append(this.usuarioLogado.temPermissao("READ") ? "✅" : "❌").append("\n");
      profile.append("Escrita: ").append(this.usuarioLogado.temPermissao("write") ? "✅" : "❌").append("\n");
      profile.append("Exclusão: ").append(this.usuarioLogado.temPermissao("delete") ? "✅" : "❌").append("\n");
      profile.append("Admin: ").append(this.usuarioLogado.temPermissao("admin") ? "✅" : "❌").append("\n");
      JTextArea textArea = new JTextArea(profile.toString());
      textArea.setEditable(false);
      textArea.setFont(new Font("Monospaced", 0, 12));
      JScrollPane scrollPane = new JScrollPane(textArea);
      scrollPane.setPreferredSize(new Dimension(400, 300));
      JOptionPane.showMessageDialog(this, scrollPane, "Perfil do Usuário", 1);
   }

   private void logout() {
      int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja sair do sistema?", "Confirmar Logout", 0, 3);
      if (option == 0) {
         this.dispose();
         (new LoginGUI()).setVisible(true);
      }

   }
}
