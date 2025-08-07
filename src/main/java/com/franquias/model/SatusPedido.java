package com.franquias.model;

/**
 * Enum que representa os possíveis status de um pedido
 */
public enum StatusPedido {
    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    REJEITADO("Rejeitado"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado"),
    AGUARDANDO_APROVACAO("Aguardando Aprovação"),
    SOLICITACAO_ALTERACAO("Solicitação de Alteração");
    
    private final String descricao;
    
    StatusPedido(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}
