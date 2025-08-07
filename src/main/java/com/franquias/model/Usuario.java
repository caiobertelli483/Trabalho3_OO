package com.franquias.model;

/**
 * Classe abstrata que representa um usuário do sistema
 * Serve como base para Dono, Gerente e Vendedor
 */
public abstract class Usuario implements Autenticavel {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    
    public Usuario(String nome, String cpf, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    /**
     * Método para validar login (aceita CPF ou email)
     */
    public boolean validarLogin(String emailOuCpf, String senha) {
        return (this.email.equals(emailOuCpf) || this.cpf.equals(emailOuCpf)) && this.senha.equals(senha);
    }
    
    @Override
    public boolean autenticar(String emailOuCpf, String senha) {
        return validarLogin(emailOuCpf, senha);
    }
    
    @Override
    public abstract boolean temPermissao(String acao);
    
    @Override
    public abstract String getTipoUsuario();
    
    @Override
    public String toString() {
        return "Nome: " + nome + ", CPF: " + cpf + ", Email: " + email;
    }
}

