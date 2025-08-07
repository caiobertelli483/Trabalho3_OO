package com.franquias.model;

import java.util.Date;

/**
 * Classe que representa um Cliente no sistema
 */
public class Cliente {
    private static int proximoId = 1;
    
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;
    private int totalCompras;
    private double valorTotalGasto;
    private Date dataCadastro;
    
    public Cliente(String nome, String cpf, String email, String telefone, String endereco) {
        this.id = proximoId++;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.totalCompras = 0;
        this.valorTotalGasto = 0.0;
        this.dataCadastro = new Date();
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public int getTotalCompras() {
        return totalCompras;
    }
    
    public double getValorTotalGasto() {
        return valorTotalGasto;
    }
    
    public Date getDataCadastro() {
        return new Date(dataCadastro.getTime());
    }
    
    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    /**
     * Registra uma nova compra do cliente
     */
    public void registrarCompra(double valor) {
        if (valor > 0) {
            totalCompras++;
            valorTotalGasto += valor;
        }
    }
    
    /**
     * Calcula o ticket médio do cliente
     */
    public double calcularTicketMedio() {
        return totalCompras > 0 ? valorTotalGasto / totalCompras : 0.0;
    }
    
    @Override
    public String toString() {
        return "Cliente ID: " + id + 
               ", Nome: " + nome + 
               ", CPF: " + cpf +
               ", Email: " + email +
               ", Total Compras: " + totalCompras +
               ", Valor Total: R$ " + String.format("%.2f", valorTotalGasto) +
               ", Ticket Médio: R$ " + String.format("%.2f", calcularTicketMedio());
    }
}

