package com.franquias.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa um Gerente do sistema
 */
public class Gerente extends Usuario {
    private List<Vendedor> vendedores;
    private boolean ativo;
    
    public Gerente(String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);
        this.vendedores = new ArrayList<>();
        this.ativo = true;
    }
    
    public List<Vendedor> getVendedores() {
        return new ArrayList<>(vendedores);
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    /**
     * Adiciona um vendedor à equipe do gerente
     */
    public void adicionarVendedor(Vendedor vendedor) {
        if (vendedor != null && !vendedores.contains(vendedor)) {
            vendedores.add(vendedor);
        }
    }
    
    /**
     * Remove um vendedor da equipe do gerente
     */
    public boolean removerVendedor(Vendedor vendedor) {
        return vendedores.remove(vendedor);
    }
    
    /**
     * Calcula o total de vendas da equipe
     */
    public double calcularTotalVendasEquipe() {
        return vendedores.stream()
                .mapToDouble(Vendedor::getTotalVendas)
                .sum();
    }
    
    /**
     * Conta o número total de pedidos da equipe
     */
    public int contarPedidosEquipe() {
        return vendedores.stream()
                .mapToInt(Vendedor::getNumeroVendas)
                .sum();
    }
    
    @Override
    public boolean temPermissao(String acao) {
        // Gerente tem permissões de gestão da franquia
        return acao.startsWith("gerenciar") || acao.startsWith("aprovar") || 
               acao.startsWith("cadastrar") || acao.startsWith("editar") ||
               acao.startsWith("visualizar");
    }
    
    @Override
    public String getTipoUsuario() {
        return "GERENTE";
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               ", Vendedores na equipe: " + vendedores.size() +
               ", Ativo: " + (ativo ? "Sim" : "Não");
    }
}
