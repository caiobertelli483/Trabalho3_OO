package com.franquias.model;

/**
 * Classe que representa um item dentro de um pedido
 */
public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private double precoUnitario;
    
    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco(); // Preço no momento do pedido
    }
    
    // Getters
    public Produto getProduto() {
        return produto;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public double getPrecoUnitario() {
        return precoUnitario;
    }
    
    // Setters
    public void setQuantidade(int quantidade) {
        if (quantidade > 0) {
            this.quantidade = quantidade;
        }
    }
    
    /**
     * Calcula o subtotal do item (quantidade × preço unitário)
     */
    public double calcularSubtotal() {
        return quantidade * precoUnitario;
    }
    
    @Override
    public String toString() {
        return produto.getNome() + 
               " - Qtd: " + quantidade + 
               " × R$ " + String.format("%.2f", precoUnitario) +
               " = R$ " + String.format("%.2f", calcularSubtotal());
    }
}
