package com.franquias.view;

import com.franquias.model.Dono;
import com.franquias.model.Gerente;
import com.franquias.service.DonoService;

public class SistemaFranquiasApp {
   public SistemaFranquiasApp() {
   }

   public static void main(String[] args) {
      System.out.println("=================================================");
      System.out.println("   SISTEMA DE GERENCIAMENTO DE FRANQUIAS");
      System.out.println("=================================================\n");
      Dono dono = new Dono("João Silva", "123.456.789-00", "joao@empresa.com", "senha123");
      DonoService donoService = new DonoService();
      System.out.println("\ud83d\udd11 Fazendo login...");
      boolean loginSucesso = donoService.fazerLogin(dono, "joao@empresa.com", "senha123");
      if (!loginSucesso) {
         System.out.println("❌ Falha no login!");
      } else {
         System.out.println("✅ Login realizado com sucesso!");
         System.out.println("Bem-vindo, " + dono.getNome() + "!\n");
         demonstrarGestaoGerentes(donoService);
         demonstrarGestaoFranquias(donoService);
         demonstrarNotificacoes(donoService);
         System.out.println("=================================================");
         System.out.println("   DEMONSTRAÇÃO CONCLUÍDA");
         System.out.println("=================================================");
      }
   }

   private static void demonstrarGestaoGerentes(DonoService donoService) {
      System.out.println("\ud83d\udc65 === GESTÃO DE GERENTES ===");
      donoService.cadastrarGerente("Maria Santos", "111.222.333-44", "maria@empresa.com", "senha456");
      donoService.cadastrarGerente("Carlos Oliveira", "555.666.777-88", "carlos@empresa.com", "senha789");
      System.out.println();
      donoService.listarGerentes();
      System.out.println();
   }

   private static void demonstrarGestaoFranquias(DonoService donoService) {
      System.out.println("\ud83c\udfe2 === GESTÃO DE FRANQUIAS ===");
      Gerente gerente1 = donoService.buscarGerentePorCpf("111.222.333-44");
      Gerente gerente2 = donoService.buscarGerentePorCpf("555.666.777-88");
      donoService.cadastrarFranquia("Franquia Centro", "Rua das Flores, 123 - Centro", gerente1);
      donoService.cadastrarFranquia("Franquia Norte", "Av. Principal, 456 - Zona Norte", gerente2);
      donoService.cadastrarFranquia("Franquia Sul", "Rua do Comércio, 789 - Zona Sul", (Gerente)null);
      System.out.println();
      donoService.listarFranquias();
      System.out.println();
   }

   private static void demonstrarNotificacoes(DonoService donoService) {
      System.out.println("� === SISTEMA DE NOTIFICAÇÕES ===");
      System.out.println("Simulando novo login para verificar notificações...\n");
      Dono dono = donoService.getDonoLogado();
      donoService.fazerLogin(dono, dono.getEmail(), dono.getSenha());
      System.out.println("Sistema funcionando corretamente!");
      System.out.println();
   }
}
