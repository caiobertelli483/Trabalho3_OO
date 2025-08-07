package com.franquias.view;

import com.franquias.model.Cliente;
import com.franquias.model.FormaPagamento;
import com.franquias.model.ModalidadeEntrega;
import com.franquias.model.Pedido;
import com.franquias.model.Produto;
import com.franquias.model.StatusPedido;
import com.franquias.model.Usuario;
import com.franquias.model.Vendedor;
import com.franquias.service.UsuarioService;
import com.franquias.service.VendedorService;
import java.io.PrintStream;

public class TesteVendedorAutomatico {
   public TesteVendedorAutomatico() {
   }

   public static void main(String[] args) {
      System.out.println("\ud83c\udfea TESTE AUTOMÁTICO - FUNCIONALIDADES DO VENDEDOR");
      System.out.println("=".repeat(60));
      VendedorService vendedorService = new VendedorService();
      UsuarioService usuarioService = new UsuarioService();
      Vendedor vendedor = usuarioService.criarVendedor("Carlos Vendedor", "888.888.888-88", "carlos@vendedor.com", "senha123", "(11) 99999-8888");
      vendedor.setMetaMensal(10000.0);
      vendedorService.fazerLogin(vendedor, "carlos@vendedor.com", "senha123");
      System.out.println("\n\ud83d\udce6 === TESTE 1: PREPARAÇÃO (PRODUTOS E CLIENTES) ===");
      prepararDados(vendedorService);
      System.out.println("\n\ud83d\uded2 === TESTE 2: CADASTRO DE PEDIDOS ===");
      testarCadastroPedidos(vendedorService);
      System.out.println("\n✏️ === TESTE 3: ALTERAÇÕES EM PEDIDOS ===");
      testarAlteracoesPedidos(vendedorService);
      System.out.println("\n\ud83d\udc40 === TESTE 4: VISUALIZAÇÃO DE PEDIDOS ===");
      testarVisualizacaoPedidos(vendedorService);
      System.out.println("\n\ud83d\udccb === TESTE 5: SOLICITAÇÕES AO GERENTE ===");
      testarSolicitacoesGerente(vendedorService);
      System.out.println("\n\ud83d\udcca === TESTE 6: RELATÓRIOS DE VENDAS ===");
      vendedorService.gerarRelatorioVendas();
      System.out.println("\n\ud83d\udc65 === TESTE 7: SERVIÇO DE USUÁRIOS ===");
      testarServicoUsuarios(usuarioService);
      System.out.println("\n\ud83c\udf89 TODOS OS TESTES CONCLUÍDOS COM SUCESSO! \ud83c\udf89");
   }

   private static void prepararDados(VendedorService vendedorService) {
      System.out.println("\ud83d\udcdd Cadastrando produtos...");
      vendedorService.adicionarProduto("iPhone 15", "Smartphone Apple", 4500.0, 10, 2);
      vendedorService.adicionarProduto("Samsung Galaxy S24", "Smartphone Samsung", 3200.0, 15, 3);
      vendedorService.adicionarProduto("AirPods Pro", "Fones Apple", 1200.0, 20, 5);
      vendedorService.adicionarProduto("Carregador Wireless", "Carregador sem fio", 150.0, 50, 10);
      vendedorService.adicionarProduto("Capa Resistente", "Capa para celular", 80.0, 100, 20);
      System.out.println("\n\ud83d\udc64 Cadastrando clientes...");
      vendedorService.adicionarCliente("João Cliente", "111.222.333-44", "joao@cliente.com", "(11) 91111-1111", "Rua das Flores, 123");
      vendedorService.adicionarCliente("Maria Cliente", "555.666.777-88", "maria@cliente.com", "(11) 92222-2222", "Av. Principal, 456");
      vendedorService.adicionarCliente("Pedro Cliente", "999.000.111-22", "pedro@cliente.com", "(11) 93333-3333", "Rua do Comércio, 789");
      System.out.println("✅ Dados preparados com sucesso!");
   }

   private static void testarCadastroPedidos(VendedorService vendedorService) {
      Cliente joao = vendedorService.buscarClientePorCpf("111.222.333-44");
      Cliente maria = vendedorService.buscarClientePorCpf("555.666.777-88");
      Cliente pedro = vendedorService.buscarClientePorCpf("999.000.111-22");
      Produto iphone = vendedorService.buscarProdutoPorId(1);
      Produto samsung = vendedorService.buscarProdutoPorId(2);
      Produto airpods = vendedorService.buscarProdutoPorId(3);
      Produto carregador = vendedorService.buscarProdutoPorId(4);
      Produto capa = vendedorService.buscarProdutoPorId(5);
      System.out.println("\n\ud83d\udcf1 Criando pedido para João (iPhone + AirPods)...");
      Pedido pedido1 = vendedorService.cadastrarPedido(joao, FormaPagamento.CARTAO_CREDITO, ModalidadeEntrega.RETIRADA, (String)null);
      vendedorService.adicionarProdutoAoPedido(pedido1.getId(), iphone, 1);
      vendedorService.adicionarProdutoAoPedido(pedido1.getId(), airpods, 1);
      vendedorService.finalizarPedido(pedido1.getId());
      System.out.println("\n\ud83d\udcf1 Criando pedido para Maria (Samsung + acessórios)...");
      Pedido pedido2 = vendedorService.cadastrarPedido(maria, FormaPagamento.PIX, ModalidadeEntrega.ENTREGA_DOMICILIO, "Av. Principal, 456");
      vendedorService.adicionarProdutoAoPedido(pedido2.getId(), samsung, 1);
      vendedorService.adicionarProdutoAoPedido(pedido2.getId(), carregador, 2);
      vendedorService.adicionarProdutoAoPedido(pedido2.getId(), capa, 1);
      vendedorService.finalizarPedido(pedido2.getId());
      System.out.println("\n\ud83c\udf81 Criando pedido para Pedro (múltiplos acessórios)...");
      Pedido pedido3 = vendedorService.cadastrarPedido(pedro, FormaPagamento.DINHEIRO, ModalidadeEntrega.ENTREGA_EXPRESSA, "Rua do Comércio, 789");
      vendedorService.adicionarProdutoAoPedido(pedido3.getId(), airpods, 1);
      vendedorService.adicionarProdutoAoPedido(pedido3.getId(), carregador, 1);
      vendedorService.adicionarProdutoAoPedido(pedido3.getId(), capa, 3);
      vendedorService.finalizarPedido(pedido3.getId());
      System.out.println("✅ Pedidos cadastrados e finalizados com sucesso!");
   }

   private static void testarAlteracoesPedidos(VendedorService vendedorService) {
      Cliente maria = vendedorService.buscarClientePorCpf("555.666.777-88");
      Produto iphone = vendedorService.buscarProdutoPorId(1);
      Produto capa = vendedorService.buscarProdutoPorId(5);
      System.out.println("\ud83d\udcdd Criando pedido para teste de alterações...");
      Pedido pedidoTeste = vendedorService.cadastrarPedido(maria, FormaPagamento.DINHEIRO, ModalidadeEntrega.RETIRADA, (String)null);
      vendedorService.adicionarProdutoAoPedido(pedidoTeste.getId(), iphone, 1);
      System.out.println("\n\ud83d\udcb3 Testando alteração de forma de pagamento...");
      vendedorService.alterarFormaPagamento(pedidoTeste.getId(), FormaPagamento.CARTAO_CREDITO);
      System.out.println("\n\ud83d\ude9a Testando alteração de modalidade de entrega...");
      vendedorService.alterarModalidadeEntrega(pedidoTeste.getId(), ModalidadeEntrega.ENTREGA_DOMICILIO, "Av. Principal, 456");
      System.out.println("\n➕ Adicionando mais produtos ao pedido...");
      vendedorService.adicionarProdutoAoPedido(pedidoTeste.getId(), capa, 2);
      System.out.println("\n➖ Removendo produto do pedido...");
      vendedorService.removerProdutoDoPedido(pedidoTeste.getId(), 5);
      vendedorService.finalizarPedido(pedidoTeste.getId());
      System.out.println("✅ Alterações testadas com sucesso!");
   }

   private static void testarVisualizacaoPedidos(VendedorService vendedorService) {
      System.out.println("\ud83d\udc40 Visualizando todos os pedidos do vendedor...");
      vendedorService.visualizarMeusPedidos();
      System.out.println("\n\ud83d\udcca Visualizando pedidos finalizados...");
      vendedorService.visualizarPedidosPorStatus(StatusPedido.FINALIZADO);
      System.out.println("\n\ud83d\udccb Visualizando pedidos pendentes...");
      vendedorService.visualizarPedidosPorStatus(StatusPedido.PENDENTE);
   }

   private static void testarSolicitacoesGerente(VendedorService vendedorService) {
      Cliente joao = vendedorService.buscarClientePorCpf("111.222.333-44");
      Produto samsung = vendedorService.buscarProdutoPorId(2);
      System.out.println("\ud83d\udcdd Criando pedido para teste de solicitações...");
      Pedido pedidoSolicitacao = vendedorService.cadastrarPedido(joao, FormaPagamento.CARTAO_DEBITO, ModalidadeEntrega.RETIRADA, (String)null);
      vendedorService.adicionarProdutoAoPedido(pedidoSolicitacao.getId(), samsung, 1);
      System.out.println("\n\ud83d\udccb Solicitando alteração ao gerente...");
      vendedorService.solicitarAlteracaoPedido(pedidoSolicitacao.getId(), "Cliente solicitou desconto de 10% por ser cliente fiel");
      Pedido pedidoExclusao = vendedorService.cadastrarPedido(joao, FormaPagamento.BOLETO, ModalidadeEntrega.RETIRADA, (String)null);
      vendedorService.adicionarProdutoAoPedido(pedidoExclusao.getId(), samsung, 1);
      System.out.println("\n\ud83d\uddd1️ Solicitando exclusão ao gerente...");
      vendedorService.solicitarExclusaoPedido(pedidoExclusao.getId(), "Cliente desistiu da compra por questões financeiras");
      System.out.println("\n\ud83d\udc40 Visualizando pedidos com solicitações pendentes...");
      vendedorService.visualizarMeusPedidos();
      System.out.println("✅ Solicitações ao gerente testadas com sucesso!");
   }

   private static void testarServicoUsuarios(UsuarioService usuarioService) {
      System.out.println("\ud83d\udc65 Testando serviço centralizado de usuários...");
      System.out.println("\n\ud83d\udcdd Criando usuários...");
      usuarioService.criarDono("Roberto Dono", "100.100.100-10", "roberto@dono.com", "senha123");
      usuarioService.criarGerente("Ana Gerente", "200.200.200-20", "ana@gerente.com", "senha123");
      usuarioService.criarVendedor("Paulo Vendedor", "300.300.300-30", "paulo@vendedor.com", "senha123", "(11) 94444-4444");
      System.out.println("\n✏️ Editando usuário...");
      usuarioService.editarVendedor("300.300.300-30", "Paulo Vendedor Silva", (String)null, (String)null, "(11) 95555-5555");
      System.out.println("\n\ud83d\udd0d Buscando usuários...");
      Usuario usuarioEncontrado = usuarioService.buscarUsuarioPorEmail("ana@gerente.com");
      if (usuarioEncontrado != null) {
         PrintStream var10000 = System.out;
         String var10001 = usuarioEncontrado.getNome();
         var10000.println("✅ Usuário encontrado: " + var10001 + " (" + usuarioService.obterTipoUsuario(usuarioEncontrado) + ")");
      }

      System.out.println("\n\ud83d\udccb Listando todos os usuários...");
      usuarioService.listarTodosUsuarios();
      System.out.println("\n\ud83d\udcca Gerando relatório de usuários...");
      usuarioService.gerarRelatorioUsuarios();
      System.out.println("✅ Serviço de usuários testado com sucesso!");
   }
}