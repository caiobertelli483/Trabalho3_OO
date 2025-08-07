package com.franquias.view;

import com.franquias.model.Cliente;
import com.franquias.model.Franquia;
import com.franquias.model.Gerente;
import com.franquias.model.Produto;
import com.franquias.model.Vendedor;
import com.franquias.service.GerenteServiceSimples;
import java.io.PrintStream;

public class TesteGerenteAutomatico {
   public TesteGerenteAutomatico() {
   }

   public static void main(String[] args) {
      System.out.println("\ud83c\udfaf TESTE AUTOMÁTICO - FUNCIONALIDADES DO GERENTE");
      System.out.println("=".repeat(60));
      Franquia franquia = new Franquia("Franquia Centro", "Centro da Cidade");
      Gerente gerente = new Gerente("Maria Silva", "111.111.111-11", "maria@franquia.com", "senha123");
      franquia.setGerente(gerente);
      GerenteServiceSimples gerenteService = new GerenteServiceSimples(franquia);
      gerenteService.fazerLogin(gerente, "maria@franquia.com", "senha123");
      System.out.println("\n\ud83d\udc65 === TESTE 1: GESTÃO DE VENDEDORES ===");
      testarGestaoVendedores(gerenteService);
      System.out.println("\n\ud83d\udce6 === TESTE 2: GESTÃO DE PRODUTOS ===");
      testarGestaoProdutos(gerenteService);
      System.out.println("\n\ud83d\udc64 === TESTE 3: GESTÃO DE CLIENTES ===");
      testarGestaoClientes(gerenteService);
      System.out.println("\n\ud83d\udcca === TESTE 4: RELATÓRIOS ===");
      testarRelatorios(gerenteService);
      System.out.println("\n\ud83c\udfaf === RELATÓRIO GERAL FINAL ===");
      gerenteService.gerarRelatorioGeral();
      System.out.println("\n\ud83c\udf89 TODOS OS TESTES CONCLUÍDOS COM SUCESSO! \ud83c\udf89");
   }

   private static void testarGestaoVendedores(GerenteServiceSimples gerenteService) {
      System.out.println("\ud83d\udcdd Cadastrando vendedores...");
      gerenteService.cadastrarVendedor("João Santos", "222.222.222-22", "joao@franquia.com", "senha123", "(11) 99999-1111");
      gerenteService.cadastrarVendedor("Ana Costa", "333.333.333-33", "ana@franquia.com", "senha123", "(11) 99999-2222");
      gerenteService.cadastrarVendedor("Pedro Lima", "444.444.444-44", "pedro@franquia.com", "senha123", "(11) 99999-3333");
      System.out.println("\n\ud83d\udcb0 Simulando vendas...");
      Vendedor joao = gerenteService.buscarVendedorPorCpf("222.222.222-22");
      Vendedor ana = gerenteService.buscarVendedorPorCpf("333.333.333-33");
      Vendedor pedro = gerenteService.buscarVendedorPorCpf("444.444.444-44");
      joao.setMetaMensal(5000.0);
      joao.adicionarVenda(1200.0);
      joao.adicionarVenda(800.0);
      joao.adicionarVenda(950.0);
      joao.adicionarVenda(1100.0);
      joao.adicionarVenda(1300.0);
      PrintStream var10000 = System.out;
      int var10001 = joao.getNumeroVendas();
      var10000.println("  ✅ João: " + var10001 + " vendas, Total: R$ " + String.format("%.2f", joao.getTotalVendas()));
      ana.setMetaMensal(4000.0);
      ana.adicionarVenda(600.0);
      ana.adicionarVenda(750.0);
      ana.adicionarVenda(850.0);
      ana.adicionarVenda(400.0);
      var10000 = System.out;
      var10001 = ana.getNumeroVendas();
      var10000.println("  ✅ Ana: " + var10001 + " vendas, Total: R$ " + String.format("%.2f", ana.getTotalVendas()));
      pedro.setMetaMensal(3000.0);
      pedro.adicionarVenda(300.0);
      pedro.adicionarVenda(450.0);
      pedro.adicionarVenda(200.0);
      var10000 = System.out;
      var10001 = pedro.getNumeroVendas();
      var10000.println("  ✅ Pedro: " + var10001 + " vendas, Total: R$ " + String.format("%.2f", pedro.getTotalVendas()));
      System.out.println("\n\ud83d\udcca Ranking de vendedores:");
      gerenteService.listarVendedoresPorVendas();
      System.out.println("\n✏️  Testando edição de vendedor...");
      gerenteService.editarVendedor("444.444.444-44", "Pedro Lima Junior", (String)null, "(11) 99999-3333");
   }

   private static void testarGestaoProdutos(GerenteServiceSimples gerenteService) {
      System.out.println("\ud83d\udcdd Cadastrando produtos...");
      gerenteService.cadastrarProduto("Smartphone XY", "Smartphone top de linha", 1200.0, 15, 5);
      gerenteService.cadastrarProduto("Tablet AB", "Tablet para trabalho", 800.0, 8, 3);
      gerenteService.cadastrarProduto("Fone Bluetooth", "Fone wireless premium", 250.0, 25, 10);
      gerenteService.cadastrarProduto("Carregador USB-C", "Carregador rápido", 45.0, 2, 5);
      gerenteService.cadastrarProduto("Capa de Celular", "Capa resistente", 35.0, 50, 15);
      System.out.println("\n\ud83d\udcc9 Simulando vendas que reduzem estoque...");
      Produto tablet = gerenteService.buscarProdutoPorId(2);
      if (tablet != null) {
         tablet.removerEstoque(6);
         System.out.println("  ✅ Vendidos 6 tablets, estoque atual: " + tablet.getQuantidadeEstoque());
      }

      System.out.println("\n⚠️  Produtos com estoque baixo:");
      gerenteService.listarProdutosEstoqueBaixo();
      System.out.println("\n✏️  Testando edição de produto...");
      gerenteService.editarProduto(4, (String)null, (String)null, 40.0, 20, (Integer)null);
   }

   private static void testarGestaoClientes(GerenteServiceSimples gerenteService) {
      System.out.println("\ud83d\udcdd Cadastrando clientes...");
      Cliente cliente1 = gerenteService.adicionarCliente("Carlos Oliveira", "555.555.555-55", "carlos@email.com", "(11) 88888-1111", "Rua A, 123");
      Cliente cliente2 = gerenteService.adicionarCliente("Lucia Ferreira", "666.666.666-66", "lucia@email.com", "(11) 88888-2222", "Rua B, 456");
      Cliente cliente3 = gerenteService.adicionarCliente("Roberto Silva", "777.777.777-77", "roberto@email.com", "(11) 88888-3333", "Rua C, 789");
      System.out.println("\n\ud83d\udcb3 Simulando histórico de compras...");
      cliente1.registrarCompra(450.0);
      cliente1.registrarCompra(320.0);
      cliente1.registrarCompra(680.0);
      cliente1.registrarCompra(150.0);
      cliente1.registrarCompra(890.0);
      PrintStream var10000 = System.out;
      int var10001 = cliente1.getTotalCompras();
      var10000.println("  ✅ Carlos: " + var10001 + " compras, Total: R$ " + String.format("%.2f", cliente1.getValorTotalGasto()));
      cliente2.registrarCompra(1200.0);
      cliente2.registrarCompra(300.0);
      cliente2.registrarCompra(550.0);
      var10000 = System.out;
      var10001 = cliente2.getTotalCompras();
      var10000.println("  ✅ Lucia: " + var10001 + " compras, Total: R$ " + String.format("%.2f", cliente2.getValorTotalGasto()));
      cliente3.registrarCompra(250.0);
      var10000 = System.out;
      var10001 = cliente3.getTotalCompras();
      var10000.println("  ✅ Roberto: " + var10001 + " compras, Total: R$ " + String.format("%.2f", cliente3.getValorTotalGasto()));
      System.out.println("\n\ud83c\udfc6 Ranking de clientes:");
      gerenteService.listarClientesRecorrentes();
   }

   private static void testarRelatorios(GerenteServiceSimples gerenteService) {
      System.out.println("\ud83d\udcc8 Calculando estatísticas consolidadas...");
      double totalVendasUnidade = gerenteService.getVendedores().stream().mapToDouble(Vendedor::getTotalVendas).sum();
      int totalPedidosUnidade = gerenteService.getVendedores().stream().mapToInt(Vendedor::getNumeroVendas).sum();
      double ticketMedioUnidade = totalPedidosUnidade > 0 ? totalVendasUnidade / (double)totalPedidosUnidade : 0.0;
      long vendedoresMetaAtingida = gerenteService.getVendedores().stream().filter(Vendedor::atingiuMeta).count();
      PrintStream var10000 = System.out;
      Object[] var10002 = new Object[]{totalVendasUnidade};
      var10000.println("  \ud83d\udcb0 Total de Vendas: R$ " + String.format("%.2f", var10002));
      System.out.println("  \ud83d\udcca Total de Pedidos: " + totalPedidosUnidade);
      var10000 = System.out;
      var10002 = new Object[]{ticketMedioUnidade};
      var10000.println("  \ud83c\udfaf Ticket Médio: R$ " + String.format("%.2f", var10002));
      System.out.println("  \ud83c\udfc6 Vendedores que atingiram meta: " + vendedoresMetaAtingida + "/" + gerenteService.getVendedores().size());
      long produtosAtivos = gerenteService.getProdutos().stream().filter(Produto::isAtivo).count();
      long produtosEstoqueBaixo = gerenteService.getProdutos().stream().filter((p) -> {
         return p.isAtivo() && p.isEstoqueBaixo();
      }).count();
      System.out.println("  \ud83d\udce6 Produtos ativos: " + produtosAtivos + "/" + gerenteService.getProdutos().size());
      System.out.println("  ⚠️  Produtos com estoque baixo: " + produtosEstoqueBaixo);
      long clientesComCompras = gerenteService.getClientes().stream().filter((c) -> {
         return c.getTotalCompras() > 0;
      }).count();
      System.out.println("  \ud83d\udc64 Clientes com compras: " + clientesComCompras + "/" + gerenteService.getClientes().size());
   }
}
