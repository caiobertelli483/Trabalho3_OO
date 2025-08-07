package com.franquias.model;

/**
 * Enum que representa as modalidades de entrega disponíveis
 */
public enum ModalidadeEntrega {
    RETIRADA("Retirada no local"),
    ENTREGA_DOMICILIO("Entrega em domicílio"),
    ENTREGA_EXPRESSA("Entrega expressa"),
    MOTOBOY("Entrega via motoboy");
    
    private final String descricao;
    
    ModalidadeEntrega(String descricao) {
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
