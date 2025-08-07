package service;

import model.*;
import java.util.*;

/**
 * Classe de serviço que implementa as funcionalidades básicas do Dono
 * Centraliza operações de gestão de franquias e gerentes
 */
public class DonoService {
    private List<Franquia> franquias;
    private List<Gerente> gerentes;
    private Dono donoLogado;
    
    public DonoService() {
        this.franquias = new ArrayList<>();
        this.gerentes = new ArrayList<>();
    }
    
    /**
     * Realiza o login do dono no sistema
     */
    public boolean fazerLogin(Dono dono, String email, String senha) {
        if (dono.validarLogin(email, senha)) {
            this.donoLogado = dono;
            verificarFranquiasSemGerente();
            return true;
        }
        return false;
    }
    
    /**
     * Verifica e notifica sobre franquias sem gerente
     */
    private void verificarFranquiasSemGerente() {
        List<Franquia> franquiasSemGerente = new ArrayList<>();
        
        for (Franquia franquia : franquias) {
            if (!franquia.temGerente()) {
                franquiasSemGerente.add(franquia);
            }
        }
        
        if (!franquiasSemGerente.isEmpty()) {
            System.out.println("⚠️  ATENÇÃO: Existem " + franquiasSemGerente.size() + 
                             " franquia(s) sem gerente alocado:");
            for (Franquia franquia : franquiasSemGerente) {
                System.out.println("   - " + franquia.getNome() + " (" + franquia.getEndereco() + ")");
            }
            System.out.println();
        }
    }
    
    // ==================== GESTÃO DE FRANQUIAS ====================
    
    /**
     * Cadastra uma nova franquia
     */
    public Franquia cadastrarFranquia(String nome, String endereco, Gerente gerente) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da franquia não pode ser vazio");
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço da franquia não pode ser vazio");
        }
        
        Franquia franquia = new Franquia(nome, endereco);
        if (gerente != null) {
            franquia.setGerente(gerente);
        }
        
        franquias.add(franquia);
        System.out.println("✅ Franquia cadastrada com sucesso: " + franquia.getNome());
        return franquia;
    }
    
    /**
     * Lista todas as franquias
     */
    public void listarFranquias() {
        if (franquias.isEmpty()) {
            System.out.println("Nenhuma franquia cadastrada.");
            return;
        }
        
        System.out.println("========== LISTA DE FRANQUIAS ==========");
        for (Franquia franquia : franquias) {
            System.out.println(franquia);
        }
        System.out.println("=======================================");
    }
    
    /**
     * Remove uma franquia pelo ID
     */
    public boolean removerFranquia(int idFranquia) {
        Franquia franquiaParaRemover = buscarFranquiaPorId(idFranquia);
        
        if (franquiaParaRemover != null) {
            franquias.remove(franquiaParaRemover);
            System.out.println("✅ Franquia removida com sucesso: " + franquiaParaRemover.getNome());
            return true;
        } else {
            System.out.println("❌ Franquia com ID " + idFranquia + " não encontrada.");
            return false;
        }
    }
    
    /**
     * Busca franquia por ID
     */
    public Franquia buscarFranquiaPorId(int id) {
        return franquias.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    // ==================== GESTÃO DE GERENTES ====================
    
    /**
     * Cadastra um novo gerente
     */
    public Gerente cadastrarGerente(String nome, String cpf, String email, String senha) {
        if (buscarGerentePorCpf(cpf) != null) {
            throw new IllegalArgumentException("Já existe um gerente com este CPF");
        }
        if (buscarGerentePorEmail(email) != null) {
            throw new IllegalArgumentException("Já existe um gerente com este email");
        }
        
        Gerente gerente = new Gerente(nome, cpf, email, senha);
        gerentes.add(gerente);
        System.out.println("✅ Gerente cadastrado com sucesso: " + gerente.getNome());
        return gerente;
    }
    
    /**
     * Remove um gerente pelo CPF
     */
    public boolean removerGerente(String cpf) {
        Gerente gerenteParaRemover = buscarGerentePorCpf(cpf);
        
        if (gerenteParaRemover != null) {
            // Remove o gerente de todas as franquias onde ele está alocado
            for (Franquia franquia : franquias) {
                if (franquia.getGerente() != null && 
                    franquia.getGerente().equals(gerenteParaRemover)) {
                    franquia.setGerente(null);
                }
            }
            
            gerentes.remove(gerenteParaRemover);
            System.out.println("✅ Gerente removido com sucesso: " + gerenteParaRemover.getNome());
            return true;
        } else {
            System.out.println("❌ Gerente com CPF " + cpf + " não encontrado.");
            return false;
        }
    }
    
    /**
     * Lista todos os gerentes
     */
    public void listarGerentes() {
        if (gerentes.isEmpty()) {
            System.out.println("Nenhum gerente cadastrado.");
            return;
        }
        
        System.out.println("========== LISTA DE GERENTES ==========");
        for (Gerente gerente : gerentes) {
            System.out.println(gerente);
        }
        System.out.println("======================================");
    }
    
    /**
     * Busca gerente por CPF
     */
    public Gerente buscarGerentePorCpf(String cpf) {
        return gerentes.stream()
                .filter(g -> g.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Busca gerente por email
     */
    public Gerente buscarGerentePorEmail(String email) {
        return gerentes.stream()
                .filter(g -> g.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
    
    // Getters para acesso às listas (somente leitura)
    public List<Franquia> getFranquias() {
        return new ArrayList<>(franquias);
    }
    
    public List<Gerente> getGerentes() {
        return new ArrayList<>(gerentes);
    }
    
    public Dono getDonoLogado() {
        return donoLogado;
    }
}
