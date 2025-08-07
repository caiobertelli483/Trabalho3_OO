package utils;

import model.*;
import java.io.*;
import java.util.*;

/**
 * Classe responsável pela persistência de dados em arquivos de texto
 * Gerencia leitura e escrita de todos os dados do sistema
 */
public class DataManager {
    
    private static final String DATA_DIR = "data/";
    private static final String USUARIOS_FILE = DATA_DIR + "usuarios.txt";
    private static final String FRANQUIAS_FILE = DATA_DIR + "franquias.txt";
    private static final String PRODUTOS_FILE = DATA_DIR + "produtos.txt";
    private static final String CLIENTES_FILE = DATA_DIR + "clientes.txt";
    
    // Inicialização - cria diretório se não existir
    static {
        try {
            File dataDir = new File(DATA_DIR);
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar diretório de dados: " + e.getMessage());
        }
    }
    
    // ==================== USUÁRIOS ====================
    
    /**
     * Salva lista de usuários em arquivo
     */
    public static void salvarUsuarios(List<Usuario> usuarios) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USUARIOS_FILE))) {
            for (Usuario usuario : usuarios) {
                // Formato: TIPO|NOME|CPF|EMAIL|SENHA|EXTRAS
                if (usuario instanceof Dono) {
                    writer.println("DONO|" + usuario.getNome() + "|" + usuario.getCpf() + 
                                 "|" + usuario.getEmail() + "|" + usuario.getSenha());
                } else if (usuario instanceof Gerente) {
                    Gerente gerente = (Gerente) usuario;
                    writer.println("GERENTE|" + usuario.getNome() + "|" + usuario.getCpf() + 
                                 "|" + usuario.getEmail() + "|" + usuario.getSenha() + 
                                 "|" + gerente.isAtivo());
                } else if (usuario instanceof Vendedor) {
                    Vendedor vendedor = (Vendedor) usuario;
                    writer.println("VENDEDOR|" + usuario.getNome() + "|" + usuario.getCpf() + 
                                 "|" + usuario.getEmail() + "|" + usuario.getSenha() + 
                                 "|" + vendedor.getTelefone() + "|" + vendedor.isAtivo() +
                                 "|" + vendedor.getTotalVendas() + "|" + vendedor.getNumeroVendas() +
                                 "|" + vendedor.getMetaMensal());
                }
            }
            System.out.println("✅ Usuários salvos em: " + USUARIOS_FILE);
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar usuários: " + e.getMessage());
        }
    }
    
    /**
     * Carrega lista de usuários do arquivo
     */
    public static List<Usuario> carregarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                try {
                    String[] dados = linha.split("\\|");
                    if (dados.length >= 5) {
                        String tipo = dados[0];
                        String nome = dados[1];
                        String cpf = dados[2];
                        String email = dados[3];
                        String senha = dados[4];
                        
                        switch (tipo) {
                            case "DONO":
                                usuarios.add(new Dono(nome, cpf, email, senha));
                                break;
                            case "GERENTE":
                                Gerente gerente = new Gerente(nome, cpf, email, senha);
                                if (dados.length > 5) {
                                    gerente.setAtivo(Boolean.parseBoolean(dados[5]));
                                }
                                usuarios.add(gerente);
                                break;
                            case "VENDEDOR":
                                Vendedor vendedor = new Vendedor(nome, cpf, email, senha);
                                if (dados.length > 5) vendedor.setTelefone(dados[5]);
                                if (dados.length > 6) vendedor.setAtivo(Boolean.parseBoolean(dados[6]));
                                if (dados.length > 7) {
                                    double totalVendas = Double.parseDouble(dados[7]);
                                    int numeroVendas = Integer.parseInt(dados[8]);
                                    for (int i = 0; i < numeroVendas; i++) {
                                        vendedor.adicionarVenda(totalVendas / numeroVendas);
                                    }
                                }
                                if (dados.length > 9) vendedor.setMetaMensal(Double.parseDouble(dados[9]));
                                usuarios.add(vendedor);
                                break;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("❌ Erro ao processar linha: " + linha + " - " + e.getMessage());
                }
            }
            System.out.println("✅ Usuários carregados: " + usuarios.size());
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ Arquivo de usuários não encontrado, criando nova lista");
        } catch (IOException e) {
            System.err.println("❌ Erro ao carregar usuários: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    // ==================== PRODUTOS ====================
    
    /**
     * Salva lista de produtos
     */
    public static void salvarProdutos(List<Produto> produtos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PRODUTOS_FILE))) {
            for (Produto produto : produtos) {
                // Formato: ID|NOME|DESCRICAO|PRECO|ESTOQUE|ESTOQUE_MIN|ATIVO
                writer.println(produto.getId() + "|" + produto.getNome() + "|" + 
                             produto.getDescricao() + "|" + produto.getPreco() + "|" + 
                             produto.getQuantidadeEstoque() + "|" + produto.getEstoqueMinimo() + 
                             "|" + produto.isAtivo());
            }
            System.out.println("✅ Produtos salvos em: " + PRODUTOS_FILE);
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar produtos: " + e.getMessage());
        }
    }
    
    /**
     * Carrega lista de produtos
     */
    public static List<Produto> carregarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUTOS_FILE))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                try {
                    String[] dados = linha.split("\\|");
                    if (dados.length >= 7) {
                        int id = Integer.parseInt(dados[0]);
                        String nome = dados[1];
                        String descricao = dados[2];
                        double preco = Double.parseDouble(dados[3]);
                        int estoque = Integer.parseInt(dados[4]);
                        int estoqueMin = Integer.parseInt(dados[5]);
                        boolean ativo = Boolean.parseBoolean(dados[6]);
                        
                        Produto produto = new Produto(nome, descricao, preco, estoque, estoqueMin);
                        produto.setAtivo(ativo);
                        produtos.add(produto);
                    }
                } catch (Exception e) {
                    System.err.println("❌ Erro ao processar produto: " + linha + " - " + e.getMessage());
                }
            }
            System.out.println("✅ Produtos carregados: " + produtos.size());
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ Arquivo de produtos não encontrado, criando nova lista");
        } catch (IOException e) {
            System.err.println("❌ Erro ao carregar produtos: " + e.getMessage());
        }
        
        return produtos;
    }
    
    // ==================== CLIENTES ====================
    
    /**
     * Salva lista de clientes
     */
    public static void salvarClientes(List<Cliente> clientes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CLIENTES_FILE))) {
            for (Cliente cliente : clientes) {
                // Formato: NOME|CPF|EMAIL|TELEFONE|ENDERECO|TOTAL_COMPRAS|VALOR_TOTAL
                writer.println(cliente.getNome() + "|" + cliente.getCpf() + "|" + 
                             cliente.getEmail() + "|" + cliente.getTelefone() + "|" + 
                             cliente.getEndereco() + "|" + cliente.getTotalCompras() + 
                             "|" + cliente.getValorTotalGasto());
            }
            System.out.println("✅ Clientes salvos em: " + CLIENTES_FILE);
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar clientes: " + e.getMessage());
        }
    }
    
    /**
     * Carrega lista de clientes
     */
    public static List<Cliente> carregarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CLIENTES_FILE))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                try {
                    String[] dados = linha.split("\\|");
                    if (dados.length >= 7) {
                        String nome = dados[0];
                        String cpf = dados[1];
                        String email = dados[2];
                        String telefone = dados[3];
                        String endereco = dados[4];
                        int totalCompras = Integer.parseInt(dados[5]);
                        double valorTotal = Double.parseDouble(dados[6]);
                        
                        Cliente cliente = new Cliente(nome, cpf, email, telefone, endereco);
                        // Simula as compras registradas
                        for (int i = 0; i < totalCompras; i++) {
                            cliente.registrarCompra(valorTotal / totalCompras);
                        }
                        clientes.add(cliente);
                    }
                } catch (Exception e) {
                    System.err.println("❌ Erro ao processar cliente: " + linha + " - " + e.getMessage());
                }
            }
            System.out.println("✅ Clientes carregados: " + clientes.size());
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ Arquivo de clientes não encontrado, criando nova lista");
        } catch (IOException e) {
            System.err.println("❌ Erro ao carregar clientes: " + e.getMessage());
        }
        
        return clientes;
    }
    
    // ==================== BACKUP E UTILITÁRIOS ====================
    
    /**
     * Cria backup completo do sistema
     */
    public static void criarBackup() {
        try {
            String backupDir = DATA_DIR + "backup_" + System.currentTimeMillis() + "/";
            File dir = new File(backupDir);
            dir.mkdirs();
            
            // Copia todos os arquivos de dados para backup
            copiarArquivo(USUARIOS_FILE, backupDir + "usuarios.txt");
            copiarArquivo(PRODUTOS_FILE, backupDir + "produtos.txt");
            copiarArquivo(CLIENTES_FILE, backupDir + "clientes.txt");
            
            System.out.println("✅ Backup criado em: " + backupDir);
        } catch (Exception e) {
            System.err.println("❌ Erro ao criar backup: " + e.getMessage());
        }
    }
    
    /**
     * Copia um arquivo
     */
    private static void copiarArquivo(String origem, String destino) {
        try {
            File origemFile = new File(origem);
            if (origemFile.exists()) {
                File destinoFile = new File(destino);
                destinoFile.getParentFile().mkdirs();
                
                try (BufferedReader reader = new BufferedReader(new FileReader(origemFile));
                     PrintWriter writer = new PrintWriter(new FileWriter(destinoFile))) {
                    
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        writer.println(linha);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Erro ao copiar arquivo " + origem + ": " + e.getMessage());
        }
    }
    
    /**
     * Limpa todos os dados (reset do sistema)
     */
    public static void limparTodosDados() {
        try {
            File dataDir = new File(DATA_DIR);
            if (dataDir.exists()) {
                File[] arquivos = dataDir.listFiles();
                if (arquivos != null) {
                    for (File arquivo : arquivos) {
                        if (arquivo.isFile() && arquivo.getName().endsWith(".txt")) {
                            arquivo.delete();
                        }
                    }
                }
            }
            System.out.println("✅ Todos os dados foram limpos");
        } catch (Exception e) {
            System.err.println("❌ Erro ao limpar dados: " + e.getMessage());
        }
    }
    
    /**
     * Verifica integridade dos arquivos de dados
     */
    public static boolean verificarIntegridade() {
        try {
            // Tenta carregar todos os arquivos
            carregarUsuarios();
            carregarProdutos();
            carregarClientes();
            
            System.out.println("✅ Integridade dos dados verificada");
            return true;
        } catch (Exception e) {
            System.err.println("❌ Erro de integridade: " + e.getMessage());
            return false;
        }
    }
}
