package service;

import model.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço centralizado para gerenciamento de todos os tipos de usuários
 * Permite criar, editar e remover Donos, Gerentes e Vendedores
 */
public class UsuarioService {
    private List<Dono> donos;
    private List<Gerente> gerentes;
    private List<Vendedor> vendedores;
    private Map<String, Usuario> todosUsuarios; // CPF -> Usuario
    
    public UsuarioService() {
        this.donos = new ArrayList<>();
        this.gerentes = new ArrayList<>();
        this.vendedores = new ArrayList<>();
        this.todosUsuarios = new HashMap<>();
    }
    
    // ==================== GESTÃO DE DONOS ====================
    
    /**
     * Cria um novo Dono
     */
    public Dono criarDono(String nome, String cpf, String email, String senha) {
        validarDadosUsuario(nome, cpf, email, senha);
        
        if (usuarioExiste(cpf)) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF: " + cpf);
        }
        
        Dono dono = new Dono(nome, cpf, email, senha);
        donos.add(dono);
        todosUsuarios.put(cpf, dono);
        
        System.out.println("✅ Dono criado com sucesso: " + dono.getNome());
        return dono;
    }
    
    /**
     * Edita um Dono existente
     */
    public boolean editarDono(String cpf, String novoNome, String novoEmail, String novaSenha) {
        Dono dono = buscarDonoPorCpf(cpf);
        
        if (dono == null) {
            System.out.println("❌ Dono não encontrado com CPF: " + cpf);
            return false;
        }
        
        return editarUsuario(dono, novoNome, novoEmail, novaSenha);
    }
    
    /**
     * Remove um Dono
     */
    public boolean removerDono(String cpf) {
        Dono dono = buscarDonoPorCpf(cpf);
        
        if (dono == null) {
            System.out.println("❌ Dono não encontrado com CPF: " + cpf);
            return false;
        }
        
        donos.remove(dono);
        todosUsuarios.remove(cpf);
        
        System.out.println("✅ Dono removido com sucesso: " + dono.getNome());
        return true;
    }
    
    // ==================== GESTÃO DE GERENTES ====================
    
    /**
     * Cria um novo Gerente
     */
    public Gerente criarGerente(String nome, String cpf, String email, String senha) {
        validarDadosUsuario(nome, cpf, email, senha);
        
        if (usuarioExiste(cpf)) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF: " + cpf);
        }
        
        Gerente gerente = new Gerente(nome, cpf, email, senha);
        gerentes.add(gerente);
        todosUsuarios.put(cpf, gerente);
        
        System.out.println("✅ Gerente criado com sucesso: " + gerente.getNome());
        return gerente;
    }
    
    /**
     * Edita um Gerente existente
     */
    public boolean editarGerente(String cpf, String novoNome, String novoEmail, String novaSenha) {
        Gerente gerente = buscarGerentePorCpf(cpf);
        
        if (gerente == null) {
            System.out.println("❌ Gerente não encontrado com CPF: " + cpf);
            return false;
        }
        
        return editarUsuario(gerente, novoNome, novoEmail, novaSenha);
    }
    
    /**
     * Remove um Gerente
     */
    public boolean removerGerente(String cpf) {
        Gerente gerente = buscarGerentePorCpf(cpf);
        
        if (gerente == null) {
            System.out.println("❌ Gerente não encontrado com CPF: " + cpf);
            return false;
        }
        
        gerentes.remove(gerente);
        todosUsuarios.remove(cpf);
        
        System.out.println("✅ Gerente removido com sucesso: " + gerente.getNome());
        return true;
    }
    
    // ==================== GESTÃO DE VENDEDORES ====================
    
    /**
     * Cria um novo Vendedor
     */
    public Vendedor criarVendedor(String nome, String cpf, String email, String senha, String telefone) {
        validarDadosUsuario(nome, cpf, email, senha);
        
        if (usuarioExiste(cpf)) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF: " + cpf);
        }
        
        Vendedor vendedor = new Vendedor(nome, cpf, email, senha, telefone);
        vendedores.add(vendedor);
        todosUsuarios.put(cpf, vendedor);
        
        System.out.println("✅ Vendedor criado com sucesso: " + vendedor.getNome());
        return vendedor;
    }
    
    /**
     * Cria um novo Vendedor (sem telefone)
     */
    public Vendedor criarVendedor(String nome, String cpf, String email, String senha) {
        return criarVendedor(nome, cpf, email, senha, null);
    }
    
    /**
     * Edita um Vendedor existente
     */
    public boolean editarVendedor(String cpf, String novoNome, String novoEmail, String novaSenha, String novoTelefone) {
        Vendedor vendedor = buscarVendedorPorCpf(cpf);
        
        if (vendedor == null) {
            System.out.println("❌ Vendedor não encontrado com CPF: " + cpf);
            return false;
        }
        
        boolean sucesso = editarUsuario(vendedor, novoNome, novoEmail, novaSenha);
        
        if (sucesso && novoTelefone != null && !novoTelefone.trim().isEmpty()) {
            vendedor.setTelefone(novoTelefone);
        }
        
        return sucesso;
    }
    
    /**
     * Remove um Vendedor
     */
    public boolean removerVendedor(String cpf) {
        Vendedor vendedor = buscarVendedorPorCpf(cpf);
        
        if (vendedor == null) {
            System.out.println("❌ Vendedor não encontrado com CPF: " + cpf);
            return false;
        }
        
        // Desativar em vez de remover completamente (para manter histórico de vendas)
        vendedor.setAtivo(false);
        
        System.out.println("✅ Vendedor desativado com sucesso: " + vendedor.getNome());
        return true;
    }
    
    /**
     * Reativa um Vendedor
     */
    public boolean reativarVendedor(String cpf) {
        Vendedor vendedor = buscarVendedorPorCpf(cpf);
        
        if (vendedor == null) {
            System.out.println("❌ Vendedor não encontrado com CPF: " + cpf);
            return false;
        }
        
        vendedor.setAtivo(true);
        System.out.println("✅ Vendedor reativado com sucesso: " + vendedor.getNome());
        return true;
    }
    
    // ==================== MÉTODOS DE BUSCA ====================
    
    /**
     * Busca qualquer usuário por CPF
     */
    public Usuario buscarUsuarioPorCpf(String cpf) {
        return todosUsuarios.get(cpf);
    }
    
    /**
     * Busca Dono por CPF
     */
    public Dono buscarDonoPorCpf(String cpf) {
        return donos.stream()
                .filter(d -> d.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Busca Gerente por CPF
     */
    public Gerente buscarGerentePorCpf(String cpf) {
        return gerentes.stream()
                .filter(g -> g.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Busca Vendedor por CPF
     */
    public Vendedor buscarVendedorPorCpf(String cpf) {
        return vendedores.stream()
                .filter(v -> v.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Busca usuários por nome (busca parcial)
     */
    public List<Usuario> buscarUsuariosPorNome(String nome) {
        return todosUsuarios.values().stream()
                .filter(u -> u.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca usuários por email
     */
    public Usuario buscarUsuarioPorEmail(String email) {
        return todosUsuarios.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
    
    // ==================== RELATÓRIOS E LISTAGENS ====================
    
    /**
     * Lista todos os usuários por tipo
     */
    public void listarTodosUsuarios() {
        System.out.println("========== TODOS OS USUÁRIOS ==========");
        
        System.out.println("\n👑 DONOS (" + donos.size() + "):");
        donos.forEach(d -> System.out.println("  " + d));
        
        System.out.println("\n👨‍💼 GERENTES (" + gerentes.size() + "):");
        gerentes.forEach(g -> System.out.println("  " + g));
        
        System.out.println("\n🏪 VENDEDORES (" + vendedores.size() + "):");
        vendedores.forEach(v -> System.out.println("  " + v));
        
        System.out.println("\n📊 RESUMO:");
        System.out.println("  Total de usuários: " + todosUsuarios.size());
        System.out.println("  Donos: " + donos.size());
        System.out.println("  Gerentes: " + gerentes.size());
        System.out.println("  Vendedores ativos: " + vendedores.stream().filter(Vendedor::isAtivo).count());
        System.out.println("  Vendedores inativos: " + vendedores.stream().filter(v -> !v.isAtivo()).count());
        
        System.out.println("=====================================");
    }
    
    /**
     * Lista vendedores por status
     */
    public void listarVendedoresPorStatus(boolean ativos) {
        String titulo = ativos ? "VENDEDORES ATIVOS" : "VENDEDORES INATIVOS";
        List<Vendedor> vendedoresFiltrados = vendedores.stream()
                .filter(v -> v.isAtivo() == ativos)
                .collect(Collectors.toList());
        
        System.out.println("========== " + titulo + " ==========");
        if (vendedoresFiltrados.isEmpty()) {
            System.out.println("Nenhum vendedor " + (ativos ? "ativo" : "inativo") + " encontrado.");
        } else {
            vendedoresFiltrados.forEach(v -> System.out.println("  " + v));
        }
        System.out.println("====================================");
    }
    
    /**
     * Gera relatório de usuários
     */
    public void gerarRelatorioUsuarios() {
        System.out.println("========== RELATÓRIO DE USUÁRIOS ==========");
        
        System.out.println("📊 ESTATÍSTICAS GERAIS:");
        System.out.println("  Total de usuários cadastrados: " + todosUsuarios.size());
        System.out.println("  Donos: " + donos.size());
        System.out.println("  Gerentes: " + gerentes.size());
        System.out.println("  Vendedores: " + vendedores.size());
        
        long vendedoresAtivos = vendedores.stream().filter(Vendedor::isAtivo).count();
        long vendedoresInativos = vendedores.size() - vendedoresAtivos;
        
        System.out.println("    - Ativos: " + vendedoresAtivos);
        System.out.println("    - Inativos: " + vendedoresInativos);
        
        // Vendedores com melhor performance
        System.out.println("\n🏆 TOP 3 VENDEDORES (por volume de vendas):");
        vendedores.stream()
                .filter(Vendedor::isAtivo)
                .sorted((v1, v2) -> Double.compare(v2.getTotalVendas(), v1.getTotalVendas()))
                .limit(3)
                .forEach(v -> System.out.println("  " + v.getNome() + " - R$ " + 
                             String.format("%.2f", v.getTotalVendas())));
        
        System.out.println("=========================================");
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    /**
     * Valida os dados básicos de um usuário
     */
    private void validarDadosUsuario(String nome, String cpf, String email, String senha) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        
        // Validar formato do email
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email deve ter formato válido");
        }
    }
    
    /**
     * Verifica se já existe um usuário com o CPF informado
     */
    private boolean usuarioExiste(String cpf) {
        return todosUsuarios.containsKey(cpf);
    }
    
    /**
     * Edita dados comuns de qualquer usuário
     */
    private boolean editarUsuario(Usuario usuario, String novoNome, String novoEmail, String novaSenha) {
        boolean alterado = false;
        
        if (novoNome != null && !novoNome.trim().isEmpty() && !novoNome.equals(usuario.getNome())) {
            usuario.setNome(novoNome);
            alterado = true;
        }
        
        if (novoEmail != null && !novoEmail.trim().isEmpty() && !novoEmail.equals(usuario.getEmail())) {
            // Verificar se o novo email já existe
            Usuario usuarioComEmail = buscarUsuarioPorEmail(novoEmail);
            if (usuarioComEmail != null && !usuarioComEmail.getCpf().equals(usuario.getCpf())) {
                System.out.println("❌ Já existe um usuário com este email: " + novoEmail);
                return false;
            }
            usuario.setEmail(novoEmail);
            alterado = true;
        }
        
        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            usuario.setSenha(novaSenha);
            alterado = true;
        }
        
        if (alterado) {
            System.out.println("✅ Usuário editado com sucesso: " + usuario.getNome());
        } else {
            System.out.println("ℹ️ Nenhuma alteração foi realizada.");
        }
        
        return true;
    }
    
    /**
     * Autentica um usuário (login com CPF ou email)
     */
    public Usuario autenticarUsuario(String emailOuCpf, String senha) {
        return todosUsuarios.values().stream()
                .filter(u -> u.validarLogin(emailOuCpf, senha))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Verifica o tipo de um usuário
     */
    public String obterTipoUsuario(Usuario usuario) {
        if (usuario instanceof Dono) return "Dono";
        if (usuario instanceof Gerente) return "Gerente";
        if (usuario instanceof Vendedor) return "Vendedor";
        return "Desconhecido";
    }
    
    // ==================== GETTERS ====================
    
    public List<Dono> getDonos() {
        return new ArrayList<>(donos);
    }
    
    public List<Gerente> getGerentes() {
        return new ArrayList<>(gerentes);
    }
    
    public List<Vendedor> getVendedores() {
        return new ArrayList<>(vendedores);
    }
    
    public List<Vendedor> getVendedoresAtivos() {
        return vendedores.stream()
                .filter(Vendedor::isAtivo)
                .collect(Collectors.toList());
    }
    
    public Map<String, Usuario> getTodosUsuarios() {
        return new HashMap<>(todosUsuarios);
    }
    
    public int getTotalUsuarios() {
        return todosUsuarios.size();
    }
}
