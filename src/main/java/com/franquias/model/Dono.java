package com.franquias.model;

/**
 * Classe que representa o Dono do sistema
 * Possui privilégios administrativos completos
 */
public class Dono extends Usuario {
    
    public Dono(String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);
    }
    
    @Override
    public boolean temPermissao(String acao) {
        // Dono tem todas as permissões
        return true;
    }
    
    @Override
    public String getTipoUsuario() {
        return "DONO";
    }
    
    @Override
    public String toString() {
        return "DONO - " + super.toString();
    }
}

