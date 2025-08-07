package com.franquias.model;

/**
 * Classe que representa um Vendedor do sistema
 */
public class Vendedor extends Usuario {
    private double totalVendas;
    private int numeroVendas;
    private boolean ativo;
    private String telefone;
    private double metaMensal;
    
    public Vendedor(String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);
        this.totalVendas = 0.0;
        this.numeroVendas = 0;
        this.ativo = true;
        this.metaMensal = 0.0;
    }
    
    public Vendedor(String nome, String cpf, String email, String senha, String telefone) {
        this(nome, cpf, email, senha);
        this.telefone = telefone;
    }
    
    // Getters
    public double getTotalVendas() {
        return totalVendas;
    }
    
    public int getNumeroVendas() {
        return numeroVendas;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public double getMetaMensal() {
        return metaMensal;
    }
    
    // Setters
    public void setTotalVendas(double totalVendas) {
        this.totalVendas = totalVendas;
    }
    
    public void setNumeroVendas(int numeroVendas) {
        this.numeroVendas = numeroVendas;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public void setMetaMensal(double metaMensal) {
        this.metaMensal = metaMensal;
    }
    
    /**
     * Adiciona uma venda às estatísticas do vendedor
     */
    public void adicionarVenda(double valorVenda) {
        if (valorVenda > 0) {
            this.totalVendas += valorVenda;
            this.numeroVendas++;
        }
    }
    
    /**
     * Calcula o ticket médio do vendedor
     */
    public double calcularTicketMedio() {
        if (numeroVendas == 0) {
            return 0.0;
        }
        return totalVendas / numeroVendas;
    }
    
    /**
     * Calcula o percentual da meta atingida
     */
    public double calcularPercentualMeta() {
        if (metaMensal == 0) {
            return 0.0;
        }
        return (totalVendas / metaMensal) * 100;
    }
    
    /**
     * Verifica se atingiu a meta
     */
    public boolean atingiuMeta() {
        return totalVendas >= metaMensal;
    }
    
    /**
     * Reseta as vendas (para novo período)
     */
    public void resetarVendas() {
        this.totalVendas = 0.0;
        this.numeroVendas = 0;
    }
    
    @Override
    public boolean temPermissao(String acao) {
        // Vendedor tem permissões limitadas apenas a vendas
        return acao.startsWith("criar_pedido") || acao.startsWith("visualizar_proprios") ||
               acao.startsWith("alterar_pedido") || acao.startsWith("solicitar");
    }
    
    @Override
    public String getTipoUsuario() {
        return "VENDEDOR";
    }
    
    @Override
    public String toString() {
        String status = ativo ? "Ativo" : "Inativo";
        return String.format(
            "Vendedor: %s | CPF: %s | Status: %s | Vendas: R$ %.2f | Pedidos: %d | Meta: R$ %.2f (%.1f%%)",
            getNome(), getCpf(), status, totalVendas, numeroVendas, metaMensal, calcularPercentualMeta()
        );
    }
}