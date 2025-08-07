package service;

import model.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe de serviço que implementa as funcionalidades do Vendedor
 * Centraliza operações de criação e gestão de pedidos
 */
public class VendedorService {
    private Vendedor vendedorLogado;
    private List<Pedido> pedidos;
    private List<Cliente> clientes;
    private List<Produto> produtos;
    
    public VendedorService() {
        this.pedidos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.produtos = new ArrayList<>();
    }
    
    /**
     * Realiza o login do vendedor no sistema
     */
    public boolean fazerLogin(Vendedor vendedor, String email, String senha) {
        if (vendedor.validarLogin(email, senha)) {
            this.vendedorLogado = vendedor;
            System.out.println("✅ Vendedor logado: " + vendedor.getNome());
            return true;
        }
        return false;
    }
    
    // ==================== GESTÃO DE PEDIDOS ====================
    
    /**
     * Cadastra um novo pedido
     */
    public Pedido cadastrarPedido(Cliente cliente, FormaPagamento formaPagamento, 
                                ModalidadeEntrega modalidadeEntrega, String enderecoEntrega) {
        if (vendedorLogado == null) {
            throw new IllegalStateException("Vendedor deve estar logado para cadastrar pedidos");
        }
        
        Pedido pedido = new Pedido(cliente, vendedorLogado, formaPagamento, modalidadeEntrega);
        
        if (modalidadeEntrega != ModalidadeEntrega.RETIRADA && enderecoEntrega != null) {
            pedido.setEnderecoEntrega(enderecoEntrega);
        }
        
        pedidos.add(pedido);
        System.out.println("✅ Pedido cadastrado com sucesso: ID " + pedido.getId());
        
        return pedido;
    }
    
    /**
     * Adiciona produto ao pedido
     */
    public boolean adicionarProdutoAoPedido(int idPedido, Produto produto, int quantidade) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        
        if (pedido == null) {
            System.out.println("❌ Pedido não encontrado.");
            return false;
        }
        
        if (!pedido.podeSerEditado()) {
            System.out.println("❌ Pedido não pode ser editado no status atual.");
            return false;
        }
        
        if (!pertenceAoVendedor(pedido)) {
            System.out.println("❌ Você só pode editar seus próprios pedidos.");
            return false;
        }
        
        if (!produto.temEstoqueSuficiente(quantidade)) {
            System.out.println("❌ Estoque insuficiente. Disponível: " + produto.getQuantidadeEstoque());
            return false;
        }
        
        pedido.adicionarItem(produto, quantidade);
        System.out.println("✅ Produto adicionado ao pedido: " + produto.getNome() + " (Qtd: " + quantidade + ")");
        
        return true;
    }
    
    /**
     * Remove produto do pedido
     */
    public boolean removerProdutoDoPedido(int idPedido, int idProduto) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        
        if (pedido == null) {
            System.out.println("❌ Pedido não encontrado.");
            return false;
        }
        
        if (!pedido.podeSerEditado()) {
            System.out.println("❌ Pedido não pode ser editado no status atual.");
            return false;
        }
        
        if (!pertenceAoVendedor(pedido)) {
            System.out.println("❌ Você só pode editar seus próprios pedidos.");
            return false;
        }
        
        boolean removido = pedido.removerItem(idProduto);
        if (removido) {
            System.out.println("✅ Produto removido do pedido.");
        } else {
            System.out.println("❌ Produto não encontrado no pedido.");
        }
        
        return removido;
    }
    
    /**
     * Altera forma de pagamento do pedido
     */
    public boolean alterarFormaPagamento(int idPedido, FormaPagamento novaFormaPagamento) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        
        if (pedido == null) {
            System.out.println("❌ Pedido não encontrado.");
            return false;
        }
        
        if (!pedido.podeSerEditado()) {
            System.out.println("❌ Pedido não pode ser editado no status atual.");
            return false;
        }
        
        if (!pertenceAoVendedor(pedido)) {
            System.out.println("❌ Você só pode editar seus próprios pedidos.");
            return false;
        }
        
        FormaPagamento formaAnterior = pedido.getFormaPagamento();
        pedido.setFormaPagamento(novaFormaPagamento);
        
        System.out.println("✅ Forma de pagamento alterada de " + formaAnterior + " para " + novaFormaPagamento);
        return true;
    }
    
    /**
     * Altera modalidade de entrega do pedido
     */
    public boolean alterarModalidadeEntrega(int idPedido, ModalidadeEntrega novaModalidade, String enderecoEntrega) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        
        if (pedido == null) {
            System.out.println("❌ Pedido não encontrado.");
            return false;
        }
        
        if (!pedido.podeSerEditado()) {
            System.out.println("❌ Pedido não pode ser editado no status atual.");
            return false;
        }
        
        if (!pertenceAoVendedor(pedido)) {
            System.out.println("❌ Você só pode editar seus próprios pedidos.");
            return false;
        }
        
        ModalidadeEntrega modalidadeAnterior = pedido.getModalidadeEntrega();
        pedido.setModalidadeEntrega(novaModalidade);
        
        if (novaModalidade != ModalidadeEntrega.RETIRADA && enderecoEntrega != null) {
            pedido.setEnderecoEntrega(enderecoEntrega);
        }
        
        System.out.println("✅ Modalidade de entrega alterada de " + modalidadeAnterior + " para " + novaModalidade);
        System.out.println("   Nova taxa de entrega: R$ " + String.format("%.2f", pedido.getTaxaEntrega()));
        
        return true;
    }
    
    /**
     * Solicita alteração de pedido ao gerente
     */
    public boolean solicitarAlteracaoPedido(int idPedido, String motivo) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        
        if (pedido == null) {
            System.out.println("❌ Pedido não encontrado.");
            return false;
        }
        
        if (!pertenceAoVendedor(pedido)) {
            System.out.println("❌ Você só pode solicitar alteração dos seus próprios pedidos.");
            return false;
        }
        
        boolean solicitado = pedido.solicitarAlteracao(motivo);
        if (solicitado) {
            System.out.println("✅ Solicitação de alteração enviada ao gerente.");
            System.out.println("   Motivo: " + motivo);
        } else {
            System.out.println("❌ Não é possível solicitar alteração para este pedido no status atual.");
        }
        
        return solicitado;
    }
    
    /**
     * Solicita exclusão de pedido ao gerente
     */
    public boolean solicitarExclusaoPedido(int idPedido, String motivo) {
        return solicitarAlteracaoPedido(idPedido, "EXCLUSÃO: " + motivo);
    }
    
    /**
     * Finaliza o pedido (apenas se todos os itens estiverem ok)
     */
    public boolean finalizarPedido(int idPedido) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        
        if (pedido == null) {
            System.out.println("❌ Pedido não encontrado.");
            return false;
        }
        
        if (!pertenceAoVendedor(pedido)) {
            System.out.println("❌ Você só pode finalizar seus próprios pedidos.");
            return false;
        }
        
        if (pedido.getItens().isEmpty()) {
            System.out.println("❌ Pedido deve ter pelo menos um item para ser finalizado.");
            return false;
        }
        
        // Verificar estoque de todos os produtos
        for (ItemPedido item : pedido.getItens()) {
            if (!item.getProduto().temEstoqueSuficiente(item.getQuantidade())) {
                System.out.println("❌ Estoque insuficiente para: " + item.getProduto().getNome());
                return false;
            }
        }
        
        // Aprovar automaticamente se vendedor finalizar
        pedido.aprovar();
        pedido.finalizar();
        
        // Atualizar estatísticas do vendedor
        vendedorLogado.adicionarVenda(pedido.calcularTotal());
        
        System.out.println("✅ Pedido finalizado com sucesso!");
        System.out.println("   Total: R$ " + String.format("%.2f", pedido.calcularTotal()));
        
        return true;
    }
    
    // ==================== VISUALIZAÇÃO DE PEDIDOS ====================
    
    /**
     * Lista todos os pedidos do vendedor logado
     */
    public void visualizarMeusPedidos() {
        if (vendedorLogado == null) {
            System.out.println("❌ Vendedor deve estar logado.");
            return;
        }
        
        List<Pedido> meusPedidos = pedidos.stream()
                .filter(this::pertenceAoVendedor)
                .sorted((p1, p2) -> p2.getDataPedido().compareTo(p1.getDataPedido()))
                .collect(Collectors.toList());
        
        System.out.println("========== MEUS PEDIDOS ==========");
        if (meusPedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
        } else {
            System.out.println("Total de pedidos: " + meusPedidos.size());
            System.out.println();
            
            for (Pedido pedido : meusPedidos) {
                System.out.println(pedido);
                if (pedido.isSolicitacaoAlteracao()) {
                    System.out.println("   ⚠️ Solicitação pendente: " + pedido.getMotivoAlteracao());
                }
                System.out.println();
            }
        }
        System.out.println("================================");
    }
    
    /**
     * Visualiza pedidos por status
     */
    public void visualizarPedidosPorStatus(StatusPedido status) {
        if (vendedorLogado == null) {
            System.out.println("❌ Vendedor deve estar logado.");
            return;
        }
        
        List<Pedido> pedidosFiltrados = pedidos.stream()
                .filter(this::pertenceAoVendedor)
                .filter(p -> p.getStatus() == status)
                .sorted((p1, p2) -> p2.getDataPedido().compareTo(p1.getDataPedido()))
                .collect(Collectors.toList());
        
        System.out.println("========== PEDIDOS " + status.toString().toUpperCase() + " ==========");
        if (pedidosFiltrados.isEmpty()) {
            System.out.println("Nenhum pedido " + status.getDescricao().toLowerCase() + " encontrado.");
        } else {
            pedidosFiltrados.forEach(System.out::println);
        }
        System.out.println("====================================");
    }
    
    /**
     * Gera relatório de vendas do vendedor
     */
    public void gerarRelatorioVendas() {
        if (vendedorLogado == null) {
            System.out.println("❌ Vendedor deve estar logado.");
            return;
        }
        
        List<Pedido> pedidosFinalizados = pedidos.stream()
                .filter(this::pertenceAoVendedor)
                .filter(p -> p.getStatus() == StatusPedido.FINALIZADO)
                .collect(Collectors.toList());
        
        double totalVendas = pedidosFinalizados.stream()
                .mapToDouble(Pedido::calcularTotal)
                .sum();
        
        int totalPedidos = pedidosFinalizados.size();
        double ticketMedio = totalPedidos > 0 ? totalVendas / totalPedidos : 0;
        
        System.out.println("========== RELATÓRIO DE VENDAS ==========");
        System.out.println("Vendedor: " + vendedorLogado.getNome());
        System.out.println("Total de Vendas: R$ " + String.format("%.2f", totalVendas));
        System.out.println("Pedidos Finalizados: " + totalPedidos);
        System.out.println("Ticket Médio: R$ " + String.format("%.2f", ticketMedio));
        System.out.println("Meta Mensal: R$ " + String.format("%.2f", vendedorLogado.getMetaMensal()));
        System.out.println("Percentual da Meta: " + String.format("%.1f", vendedorLogado.calcularPercentualMeta()) + "%");
        System.out.println("Meta Atingida: " + (vendedorLogado.atingiuMeta() ? "✅ Sim" : "❌ Não"));
        System.out.println("=======================================");
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    /**
     * Busca pedido por ID
     */
    public Pedido buscarPedidoPorId(int id) {
        return pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Verifica se o pedido pertence ao vendedor logado
     */
    private boolean pertenceAoVendedor(Pedido pedido) {
        return vendedorLogado != null && 
               pedido.getVendedor() != null && 
               pedido.getVendedor().getCpf().equals(vendedorLogado.getCpf());
    }
    
    /**
     * Adiciona cliente ao sistema (para facilitar testes)
     */
    public Cliente adicionarCliente(String nome, String cpf, String email, String telefone, String endereco) {
        Cliente cliente = new Cliente(nome, cpf, email, telefone, endereco);
        clientes.add(cliente);
        return cliente;
    }
    
    /**
     * Adiciona produto ao sistema (para facilitar testes)
     */
    public Produto adicionarProduto(String nome, String descricao, double preco, int estoque, int estoqueMinimo) {
        Produto produto = new Produto(nome, descricao, preco, estoque, estoqueMinimo);
        produtos.add(produto);
        return produto;
    }
    
    /**
     * Busca cliente por CPF
     */
    public Cliente buscarClientePorCpf(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
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
    
    // Getters
    public Vendedor getVendedorLogado() {
        return vendedorLogado;
    }
    
    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }
    
    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }
    
    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }
}
