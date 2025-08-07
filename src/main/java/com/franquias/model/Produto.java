package com.franquias.model;

/**
 * Classe que representa um Produto no sistema
 */
public class Produto {
    private static int proximoId = 1;
    
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidadeEstoque;
    private int estoqueMinimo;
    private String categoria;
    private boolean ativo;
    
    public Produto(String nome, String descricao, double preco, int quantidadeEstoque, int estoqueMinimo) {
        this.id = proximoId++;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.estoqueMinimo = estoqueMinimo;
        this.categoria = "Geral";
        this.ativo = true;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public void setPreco(double preco) {
        if (preco >= 0) {
            this.preco = preco;
        }
    }
    
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque >= 0) {
            this.quantidadeEstoque = quantidadeEstoque;
        }
    }
    
    public void setEstoqueMinimo(int estoqueMinimo) {
        if (estoqueMinimo >= 0) {
            this.estoqueMinimo = estoqueMinimo;
        }
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    /**
     * Verifica se o produto está com estoque baixo
     */
    public boolean isEstoqueBaixo() {
        return quantidadeEstoque <= estoqueMinimo;
    }
    
    /**
     * Adiciona quantidade ao estoque
     */
    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidadeEstoque += quantidade;
        }
    }
    
    /**
     * Remove quantidade do estoque (para vendas)
     */
    public boolean removerEstoque(int quantidade) {
        if (quantidade > 0 && quantidade <= quantidadeEstoque) {
            this.quantidadeEstoque -= quantidade;
            return true;
        }
        return false;
    }
    
    /**
     * Verifica se há estoque suficiente
     */
    public boolean temEstoqueSuficiente(int quantidade) {
        return quantidadeEstoque >= quantidade;
    }
    
    @Override
    public String toString() {
        return "Produto ID: " + id + 
               ", Nome: " + nome + 
               ", Preço: R$ " + String.format("%.2f", preco) +
               ", Estoque: " + quantidadeEstoque +
               ", Mín: " + estoqueMinimo +
               (isEstoqueBaixo() ? " ⚠️ ESTOQUE BAIXO" : "") +
               ", Ativo: " + (ativo ? "Sim" : "Não");
    }
}

