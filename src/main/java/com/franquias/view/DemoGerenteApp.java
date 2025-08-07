package com.franquias.view;
import com.franquias.model.Cliente;
import com.franquias.model.Franquia;
import com.franquias.model.Gerente;
import com.franquias.model.Produto;
import com.franquias.model.Vendedor;
import com.franquias.service.GerenteServiceSimples;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class DemoGerenteApp {
   private static Scanner scanner;
   private static GerenteServiceSimples gerenteService;
   private static Gerente gerente;

   static {
      scanner = new Scanner(System.in);
   }

   public DemoGerenteApp() {
   }

   public static void main(String[] args) {
      System.out.println("\ud83c\udfaf Sistema de Gerenciamento de Franquias - DEMO GERENTE");
      System.out.println("=========================================================");
      inicializarSistema();
      executarDemo();
      System.out.println("\n\ud83c\udf89 Demo concluída com sucesso!");
      scanner.close();
   }

   private static void inicializarSistema() {
      System.out.println("\n\ud83d\udccb Inicializando sistema...");
      Franquia franquia = new Franquia("Franquia Centro", "Centro da Cidade");
      gerente = new Gerente("Maria Silva", "111.111.111-11", "maria@franquia.com", "senha123");
      franquia.setGerente(gerente);
      gerenteService = new GerenteServiceSimples(franquia);
      gerenteService.fazerLogin(gerente, "maria@franquia.com", "senha123");
      System.out.println("✅ Sistema inicializado!");
   }

   private static void executarDemo() {
      int opcao;
      do {
         exibirMenu();
         opcao = lerOpcao();
         switch (opcao) {
            case 0:
               System.out.println("\n\ud83d\udc4b Saindo do sistema...");
               break;
            case 1:
               demonstrarGestaoVendedores();
               break;
            case 2:
               demonstrarGestaoProdutos();
               break;
            case 3:
               demonstrarGestaoClientes();
               break;
            case 4:
               demonstrarRelatorios();
               break;
            case 5:
               gerenteService.gerarRelatorioGeral();
               break;
            default:
               System.out.println("❌ Opção inválida!");
         }

         if (opcao != 0) {
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
         }
      } while(opcao != 0);

   }

   private static void exibirMenu() {
      System.out.println("\n" + "=".repeat(50));
      System.out.println("\ud83c\udfaf DEMO - FUNCIONALIDADES DO GERENTE");
      System.out.println("=".repeat(50));
      System.out.println("1️⃣  Demonstrar Gestão de Vendedores");
      System.out.println("2️⃣  Demonstrar Gestão de Produtos");
      System.out.println("3️⃣  Demonstrar Gestão de Clientes");
      System.out.println("4️⃣  Demonstrar Relatórios Específicos");
      System.out.println("5️⃣  Relatório Geral da Unidade");
      System.out.println("0️⃣  Sair");
      System.out.println("=".repeat(50));
      System.out.print("Escolha uma opção: ");
   }

   private static int lerOpcao() {
      try {
         int opcao = Integer.parseInt(scanner.nextLine());
         return opcao;
      } catch (NumberFormatException var1) {
         return -1;
      }
   }

   private static void demonstrarGestaoVendedores() {
      System.out.println("\n\ud83d\udc65 === DEMONSTRAÇÃO: GESTÃO DE VENDEDORES ===");
      System.out.println("\n\ud83d\udcdd Cadastrando vendedores...");
      gerenteService.cadastrarVendedor("João Santos", "222.222.222-22", "joao@franquia.com", "senha123", "(11) 99999-1111");
      gerenteService.cadastrarVendedor("Ana Costa", "333.333.333-33", "ana@franquia.com", "senha123", "(11) 99999-2222");
      gerenteService.cadastrarVendedor("Pedro Lima", "444.444.444-44", "pedro@franquia.com", "senha123", "(11) 99999-3333");
      System.out.println("\n\ud83d\udcb0 Simulando vendas dos vendedores...");
      Vendedor joao = gerenteService.buscarVendedorPorCpf("222.222.222-22");
      Vendedor ana = gerenteService.buscarVendedorPorCpf("333.333.333-33");
      Vendedor pedro = gerenteService.buscarVendedorPorCpf("444.444.444-44");
      joao.setMetaMensal(5000.0);
      joao.adicionarVenda(1200.0);
      joao.adicionarVenda(800.0);
      joao.adicionarVenda(950.0);
      joao.adicionarVenda(1100.0);
      joao.adicionarVenda(1300.0);
      ana.setMetaMensal(4000.0);
      ana.adicionarVenda(600.0);
      ana.adicionarVenda(750.0);
      ana.adicionarVenda(850.0);
      ana.adicionarVenda(400.0);
      pedro.setMetaMensal(3000.0);
      pedro.adicionarVenda(300.0);
      pedro.adicionarVenda(450.0);
      pedro.adicionarVenda(200.0);
      System.out.println("✅ Vendas simuladas com sucesso!");
      System.out.println("\n\ud83d\udcca Listando vendedores por performance:");
      gerenteService.listarVendedoresPorVendas();
      System.out.println("\n✏️  Editando dados do vendedor Pedro...");
      gerenteService.editarVendedor("444.444.444-44", "Pedro Lima Junior", (String)null, "(11) 99999-3333");
      System.out.println("\n\ud83d\udd0d Buscando vendedor por CPF:");
      Vendedor vendedorEncontrado = gerenteService.buscarVendedorPorCpf("333.333.333-33");
      if (vendedorEncontrado != null) {
         System.out.println("Encontrado: " + String.valueOf(vendedorEncontrado));
      }

   }

   private static void demonstrarGestaoProdutos() {
      System.out.println("\n\ud83d\udce6 === DEMONSTRAÇÃO: GESTÃO DE PRODUTOS ===");
      System.out.println("\n\ud83d\udcdd Cadastrando produtos...");
      gerenteService.cadastrarProduto("Smartphone XY", "Smartphone top de linha", 1200.0, 15, 5);
      gerenteService.cadastrarProduto("Tablet AB", "Tablet para trabalho", 800.0, 8, 3);
      gerenteService.cadastrarProduto("Fone Bluetooth", "Fone wireless premium", 250.0, 25, 10);
      gerenteService.cadastrarProduto("Carregador USB-C", "Carregador rápido", 45.0, 2, 5);
      gerenteService.cadastrarProduto("Capa de Celular", "Capa resistente", 35.0, 50, 15);
      System.out.println("\n\ud83d\udcc9 Simulando vendas que reduzem estoque...");
      Produto tablet = gerenteService.buscarProdutoPorId(2);
      if (tablet != null) {
         tablet.removerEstoque(6);
         System.out.println("Vendidos 6 tablets (estoque atual: " + tablet.getQuantidadeEstoque() + ")");
      }

      System.out.println("\n⚠️  Verificando produtos com estoque baixo:");
      gerenteService.listarProdutosEstoqueBaixo();
      System.out.println("\n✏️  Editando produto (ajustando preço e estoque)...");
      gerenteService.editarProduto(4, (String)null, (String)null, 40.0, 20, (Integer)null);
      System.out.println("\n\ud83d\uddd1️  Desativando um produto...");
      gerenteService.removerProduto(5);
   }

   private static void demonstrarGestaoClientes() {
      System.out.println("\n\ud83d\udc64 === DEMONSTRAÇÃO: GESTÃO DE CLIENTES ===");
      System.out.println("\n\ud83d\udcdd Cadastrando clientes...");
      Cliente cliente1 = gerenteService.adicionarCliente("Carlos Oliveira", "555.555.555-55", "carlos@email.com", "(11) 88888-1111", "Rua A, 123");
      Cliente cliente2 = gerenteService.adicionarCliente("Lucia Ferreira", "666.666.666-66", "lucia@email.com", "(11) 88888-2222", "Rua B, 456");
      Cliente cliente3 = gerenteService.adicionarCliente("Roberto Silva", "777.777.777-77", "roberto@email.com", "(11) 88888-3333", "Rua C, 789");
      System.out.println("\n\ud83d\udcb3 Simulando histórico de compras...");
      cliente1.registrarCompra(450.0);
      cliente1.registrarCompra(320.0);
      cliente1.registrarCompra(680.0);
      cliente1.registrarCompra(150.0);
      cliente1.registrarCompra(890.0);
      cliente2.registrarCompra(1200.0);
      cliente2.registrarCompra(300.0);
      cliente2.registrarCompra(550.0);
      cliente3.registrarCompra(250.0);
      System.out.println("✅ Histórico de compras simulado!");
      System.out.println("\n\ud83c\udfc6 Listando clientes mais recorrentes:");
      gerenteService.listarClientesRecorrentes();
   }

   private static void demonstrarRelatorios() {
      System.out.println("\n\ud83d\udcca === DEMONSTRAÇÃO: RELATÓRIOS ESPECÍFICOS ===");
      System.out.println("\n\ud83d\udc65 RELATÓRIO: Vendedores por Performance");
      gerenteService.listarVendedoresPorVendas();
      System.out.println("\n\ud83d\udce6 RELATÓRIO: Produtos com Estoque Baixo");
      gerenteService.listarProdutosEstoqueBaixo();
      System.out.println("\n\ud83d\udc64 RELATÓRIO: Clientes Mais Recorrentes");
      gerenteService.listarClientesRecorrentes();
      System.out.println("\n\ud83d\udcc8 ESTATÍSTICAS ADICIONAIS:");
      List<Vendedor> vendedores = gerenteService.getVendedores();
      double totalVendasUnidade = vendedores.stream().mapToDouble(Vendedor::getTotalVendas).sum();
      int totalPedidosUnidade = vendedores.stream().mapToInt(Vendedor::getNumeroVendas).sum();
      double ticketMedioUnidade = totalPedidosUnidade > 0 ? totalVendasUnidade / (double)totalPedidosUnidade : 0.0;
      PrintStream var10000 = System.out;
      Object[] var10002 = new Object[]{totalVendasUnidade};
      var10000.println("\ud83d\udcb0 Total de Vendas da Unidade: R$ " + String.format("%.2f", var10002));
      System.out.println("\ud83d\udcca Total de Pedidos: " + totalPedidosUnidade);
      var10000 = System.out;
      var10002 = new Object[]{ticketMedioUnidade};
      var10000.println("\ud83c\udfaf Ticket Médio da Unidade: R$ " + String.format("%.2f", var10002));
      long vendedoresMetaAtingida = vendedores.stream().filter(Vendedor::atingiuMeta).count();
      System.out.println("\ud83c\udfc6 Vendedores que atingiram meta: " + vendedoresMetaAtingida + "/" + vendedores.size());
   }
}
