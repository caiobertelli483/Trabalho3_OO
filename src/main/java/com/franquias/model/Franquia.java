package com.franquias.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa uma Franquia no sistema
 */
public class Franquia {
    private static int proximoId = 1;
    
    private int id;
    private String nome;
    private String endereco;
    private Gerente gerente;
    private List<Vendedor> vendedores;
    
    public Franquia(String nome, String endereco) {
        this.id = proximoId++;
        this.nome = nome;
        this.endereco = endereco;
        this.vendedores = new ArrayList<>();
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public Gerente getGerente() {
        return gerente;
    }
    
    public List<Vendedor> getVendedores() {
        return new ArrayList<>(vendedores);
    }
    
    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }
    
    /**
     * Adiciona um vendedor à franquia
     */
    public void adicionarVendedor(Vendedor vendedor) {
        if (vendedor != null && !vendedores.contains(vendedor)) {
            vendedores.add(vendedor);
            if (gerente != null) {
                gerente.adicionarVendedor(vendedor);
            }
        }
    }
    
    /**
     * Remove um vendedor da franquia
     */
    public boolean removerVendedor(Vendedor vendedor) {
        boolean removido = vendedores.remove(vendedor);
        if (removido && gerente != null) {
            gerente.removerVendedor(vendedor);
        }
        return removido;
    }
    
    /**
     * Verifica se a franquia tem gerente
     */
    public boolean temGerente() {
        return gerente != null;
    }
    
    @Override
    public String toString() {
        return "Franquia ID: " + id + 
               ", Nome: " + nome + 
               ", Endereço: " + endereco +
               ", Gerente: " + (gerente != null ? gerente.getNome() : "SEM GERENTE") +
               ", Vendedores: " + vendedores.size();
    }
}
