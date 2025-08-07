package com.franquias.model;

/**
 * Interface que define comportamento de autenticação para usuários do sistema
 */
public interface Autenticavel {
    
    /**
     * Realiza autenticação do usuário
     * @param email Email para login
     * @param senha Senha para login
     * @return true se autenticação for bem-sucedida
     */
    boolean autenticar(String email, String senha);
    
    /**
     * Verifica se o usuário possui permissão para uma ação
     * @param acao Ação que se deseja verificar permissão
     * @return true se possui permissão
     */
    boolean temPermissao(String acao);
    
    /**
     * Obtém o tipo/perfil do usuário
     * @return Tipo do usuário (DONO, GERENTE, VENDEDOR)
     */
    String getTipoUsuario();
}