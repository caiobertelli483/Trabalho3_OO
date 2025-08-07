package service;

import model.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe de serviço que implementa as funcionalidades básicas do Gerente
 * Foca em gestão de vendedores, produtos e relatórios simples
 */
public class GerenteServiceSimples {
    private Gerente gerenteLogado;
    private Franquia franquiaGerenciada;
    private List<Vendedor> vendedores;
    private List<Produto> produtos;
    private List<Cliente> clientes;
    
    public GerenteServiceSimples(Franquia franquia) {
        this.franquiaGerenciada = franquia;
        this.vendedores = new ArrayList<>();
        this.produtos = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }
    
    /**
     * Realiza o login do gerente no sistema
     */
    public boolean fazerLogin(Gerente gerente, String email, String senha) {
        if (gerente.validarLogin(email, senha)) {
            this.gerenteLogado = gerente;
            System.out.println("✅ Gerente logado: " + gerente.getNome());
            verificarEstoqueBaixo();
            return true;
        }
        return false;
    }
    
    /**
     * Verifica produtos com estoque baixo e notifica
     */
    private void verificarEstoqueBaixo() {
        List<Produto> produtosEstoqueBaixo = produtos.stream()
                .filter(Produto::isEstoqueBaixo)
                .collect(Collectors.toList());
        
        if (!produtosEstoqueBaixo.isEmpty()) {
            System.out.println("⚠️  ATENÇÃO: " + produtosEstoqueBaixo.size() + 
                             " produto(s) com estoque baixo:");
            for (Produto produto : produtosEstoqueBaixo) {
                System.out.println("   - " + produto.getNome() + 
                                 " (Estoque: " + produto.getQuantidadeEstoque() + 
                                 ", Mín: " + produto.getEstoqueMinimo() + ")");
            }
            System.out.println();
        }
    }
    
    // ==================== GESTÃO DE VENDEDORES ====================
    
    /**
     * Cadastra um novo vendedor
     */
    public Vendedor cadastrarVendedor(String nome, String cpf, String email, String senha, String telefone) {
        // Verificar se CPF já existe
        if (buscarVendedorPorCpf(cpf) != null) {
            throw new IllegalArgumentException("Já existe um vendedor com este CPF");
        }
        
        Vendedor vendedor = new Vendedor(nome, cpf, email, senha, telefone);
        vendedores.add(vendedor);
        
        // Adicionar à franquia se houver
        if (franquiaGerenciada != null) {
            franquiaGerenciada.adicionarVendedor(vendedor);
        }
        
        System.out.println("✅ Vendedor cadastrado com sucesso: " + vendedor.getNome());
        return vendedor;
    }
    
    /**
     * Edita dados de um vendedor existente
     */
    public boolean editarVendedor(String cpf, String novoNome, String novoEmail, String novoTelefone) {
        Vendedor vendedor = buscarVendedorPorCpf(cpf);
        
        if (vendedor != null) {
            if (novoNome != null && !novoNome.trim().isEmpty()) {
                vendedor.setNome(novoNome);
            }
            if (novoEmail != null && !novoEmail.trim().isEmpty()) {
                vendedor.setEmail(novoEmail);
            }
            if (novoTelefone != null && !novoTelefone.trim().isEmpty()) {
                vendedor.setTelefone(novoTelefone);
            }
            
            System.out.println("✅ Vendedor editado com sucesso: " + vendedor.getNome());
            return true;
        }
        
        System.out.println("❌ Vendedor não encontrado.");
        return false;
    }
    
    /**
     * Remove um vendedor (desativa)
     */
    public boolean removerVendedor(String cpf) {
        Vendedor vendedor = buscarVendedorPorCpf(cpf);
        
        if (vendedor != null) {
            vendedor.setAtivo(false);
            System.out.println("✅ Vendedor desativado com sucesso: " + vendedor.getNome());
            return true;
        }
        
        System.out.println("❌ Vendedor não encontrado.");
        return false;
    }
    
    /**
     * Lista vendedores ordenados por volume de vendas
     */
    public List<Vendedor> listarVendedoresPorVendas() {
        List<Vendedor> vendedoresAtivos = vendedores.stream()
                .filter(Vendedor::isAtivo)
                .sorted((v1, v2) -> Double.compare(v2.getTotalVendas(), v1.getTotalVendas()))
                .collect(Collectors.toList());
        
        System.out.println("========== VENDEDORES POR VOLUME DE VENDAS ==========");
        if (vendedoresAtivos.isEmpty()) {
            System.out.println("Nenhum vendedor ativo encontrado.");
        } else {
            for (int i = 0; i < vendedoresAtivos.size(); i++) {
                Vendedor v = vendedoresAtivos.get(i);
                System.out.println((i + 1) + "º - " + v.getNome() + 
                                 " | Vendas: R$ " + String.format("%.2f", v.getTotalVendas()) +
                                 " | Pedidos: " + v.getNumeroVendas());
            }
        }
        System.out.println("===================================================");
        
        return vendedoresAtivos;
    }
    
    /**
     * Busca vendedor por CPF
     */
    public Vendedor buscarVendedorPorCpf(String cpf) {
        return vendedores.stream()
                .filter(v -> v.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    
    // ==================== GESTÃO DE PRODUTOS ====================
    
    /**
     * Cadastra um novo produto
     */
    public Produto cadastrarProduto(String nome, String descricao, double preco, 
                                  int quantidadeEstoque, int estoqueMinimo) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
        
        Produto produto = new Produto(nome, descricao, preco, quantidadeEstoque, estoqueMinimo);
        produtos.add(produto);
        
        System.out.println("✅ Produto cadastrado com sucesso: " + produto.getNome());
        return produto;
    }
    
    /**
     * Edita um produto existente
     */
    public boolean editarProduto(int idProduto, String novoNome, String novaDescricao, 
                               Double novoPreco, Integer novoEstoque, Integer novoMinimo) {
        Produto produto = buscarProdutoPorId(idProduto);
        
        if (produto == null) {
            System.out.println("❌ Produto não encontrado.");
            return false;
        }
        
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            produto.setNome(novoNome);
        }
        if (novaDescricao != null) {
            produto.setDescricao(novaDescricao);
        }
        if (novoPreco != null && novoPreco >= 0) {
            produto.setPreco(novoPreco);
        }
        if (novoEstoque != null && novoEstoque >= 0) {
            produto.setQuantidadeEstoque(novoEstoque);
        }
        if (novoMinimo != null && novoMinimo >= 0) {
            produto.setEstoqueMinimo(novoMinimo);
        }
        
        System.out.println("✅ Produto editado com sucesso: " + produto.getNome());
        return true;
    }
    
    /**
     * Remove um produto (desativa)
     */
    public boolean removerProduto(int idProduto) {
        Produto produto = buscarProdutoPorId(idProduto);
        
        if (produto != null) {
            produto.setAtivo(false);
            System.out.println("✅ Produto desativado com sucesso: " + produto.getNome());
            return true;
        }
        
        System.out.println("❌ Produto não encontrado.");
        return false;
    }
    
    /**
     * Lista produtos com estoque baixo
     */
    public List<Produto> listarProdutosEstoqueBaixo() {
        List<Produto> produtosEstoqueBaixo = produtos.stream()
                .filter(p -> p.isAtivo() && p.isEstoqueBaixo())
                .collect(Collectors.toList());
        
        System.out.println("========== PRODUTOS COM ESTOQUE BAIXO ==========");
        if (produtosEstoqueBaixo.isEmpty()) {
            System.out.println("✅ Nenhum produto com estoque baixo.");
        } else {
            for (Produto produto : produtosEstoqueBaixo) {
                System.out.println("⚠️  " + produto);
            }
        }
        System.out.println("==============================================");
        
        return produtosEstoqueBaixo;
    }
    
    /**
     * Busca produto por ID
     */
    public Produto buscarProdutoPorId(int id) {
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    // ==================== GESTÃO DE CLIENTES ====================
    
    /**
     * Adiciona um cliente ao sistema
     */
    public Cliente adicionarCliente(String nome, String cpf, String email, String telefone, String endereco) {
        Cliente cliente = new Cliente(nome, cpf, email, telefone, endereco);
        clientes.add(cliente);
        System.out.println("✅ Cliente cadastrado: " + cliente.getNome());
        return cliente;
    }
    
    /**
     * Lista clientes mais recorrentes por quantidade de compras
     */
    public List<Cliente> listarClientesRecorrentes() {
        List<Cliente> clientesOrdenados = clientes.stream()
                .filter(c -> c.getTotalCompras() > 0)
                .sorted((c1, c2) -> Integer.compare(c2.getTotalCompras(), c1.getTotalCompras()))
                .collect(Collectors.toList());
        
        System.out.println("========== CLIENTES MAIS RECORRENTES ==========");
        if (clientesOrdenados.isEmpty()) {
            System.out.println("Nenhum cliente com compras encontrado.");
        } else {
            for (int i = 0; i < clientesOrdenados.size(); i++) {
                Cliente c = clientesOrdenados.get(i);
                System.out.println((i + 1) + "º - " + c.getNome() + 
                                 " | Compras: " + c.getTotalCompras() +
                                 " | Total Gasto: R$ " + String.format("%.2f", c.getValorTotalGasto()));
            }
        }
        System.out.println("==============================================");
        
        return clientesOrdenados;
    }
    
    // ==================== RELATÓRIOS SIMPLIFICADOS ====================
    
    /**
     * Relatório geral da unidade
     */
    public void gerarRelatorioGeral() {
        System.out.println("========== RELATÓRIO GERAL DA UNIDADE ==========");
        System.out.println("Franquia: " + (franquiaGerenciada != null ? franquiaGerenciada.getNome() : "N/A"));
        System.out.println("Gerente: " + (gerenteLogado != null ? gerenteLogado.getNome() : "N/A"));
        System.out.println();
        
        // Vendedores
        long vendedoresAtivos = vendedores.stream().filter(Vendedor::isAtivo).count();
        System.out.println("📊 VENDEDORES:");
        System.out.println("   - Total: " + vendedores.size());
        System.out.println("   - Ativos: " + vendedoresAtivos);
        System.out.println("   - Inativos: " + (vendedores.size() - vendedoresAtivos));
        
        // Produtos
        long produtosAtivos = produtos.stream().filter(Produto::isAtivo).count();
        long produtosEstoqueBaixo = produtos.stream().filter(p -> p.isAtivo() && p.isEstoqueBaixo()).count();
        System.out.println();
        System.out.println("📦 PRODUTOS:");
        System.out.println("   - Total: " + produtos.size());
        System.out.println("   - Ativos: " + produtosAtivos);
        System.out.println("   - Com estoque baixo: " + produtosEstoqueBaixo);
        
        // Clientes
        System.out.println();
        System.out.println("👥 CLIENTES:");
        System.out.println("   - Total cadastrados: " + clientes.size());
        System.out.println("   - Com compras: " + clientes.stream().filter(c -> c.getTotalCompras() > 0).count());
        
        System.out.println("==============================================");
    }
    
    // ==================== GETTERS ====================
    
    public Gerente getGerenteLogado() {
        return gerenteLogado;
    }
    
    public Franquia getFranquiaGerenciada() {
        return franquiaGerenciada;
    }
    
    public List<Vendedor> getVendedores() {
        return new ArrayList<>(vendedores);
    }
    
    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }
    
    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }
}
