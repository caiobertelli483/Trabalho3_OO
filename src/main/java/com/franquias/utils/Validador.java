package utils;

/**
 * Classe utilitária para validações de dados
 */
public class Validador {
    
    /**
     * Valida se um CPF é válido usando algoritmo de dígitos verificadores
     * @param cpf CPF a ser validado (com ou sem formatação)
     * @return true se CPF é válido
     */
    public static boolean validarCPF(String cpf) {
        if (cpf == null) {
            return false;
        }
        
        // Remove formatação (pontos e hífen)
        cpf = cpf.replaceAll("[^0-9]", "");
        
        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais (CPF inválido)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        try {
            // Calcula primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int resto = soma % 11;
            int primeiroDigito = resto < 2 ? 0 : 11 - resto;
            
            // Verifica primeiro dígito
            if (Character.getNumericValue(cpf.charAt(9)) != primeiroDigito) {
                return false;
            }
            
            // Calcula segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            resto = soma % 11;
            int segundoDigito = resto < 2 ? 0 : 11 - resto;
            
            // Verifica segundo dígito
            return Character.getNumericValue(cpf.charAt(10)) == segundoDigito;
            
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Valida se um nome é válido
     * @param nome Nome a ser validado
     * @return true se nome é válido
     */
    public static boolean validarNome(String nome) {
        return nome != null && nome.trim().length() > 1;
    }
    
    /**
     * Valida se um email tem formato básico válido
     * @param email Email a ser validado
     * @return true se email é válido
     */
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Validação básica de email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
    
    /**
     * Valida se um campo obrigatório não é nulo ou vazio
     * @param campo Campo a ser validado
     * @return true se campo é válido
     */
    public static boolean validarCampoObrigatorio(String campo) {
        return campo != null && !campo.trim().isEmpty();
    }
    
    /**
     * Valida se um telefone tem formato válido
     * @param telefone Telefone a ser validado
     * @return true se telefone é válido
     */
    public static boolean validarTelefone(String telefone) {
        if (telefone == null) {
            return false;
        }
        
        // Remove formatação
        String telefoneNumerico = telefone.replaceAll("[^0-9]", "");
        
        // Verifica se tem 10 ou 11 dígitos (com DDD)
        return telefoneNumerico.length() == 10 || telefoneNumerico.length() == 11;
    }
    
    /**
     * Valida se um valor monetário é positivo
     * @param valor Valor a ser validado
     * @return true se valor é válido (positivo)
     */
    public static boolean validarValorPositivo(double valor) {
        return valor > 0;
    }
    
    /**
     * Valida todos os dados de um usuário
     * @param nome Nome do usuário
     * @param cpf CPF do usuário
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @return true se todos os dados são válidos
     */
    public static boolean validarDadosUsuario(String nome, String cpf, String email, String senha) {
        return validarNome(nome) && 
               validarCPF(cpf) && 
               validarEmail(email) && 
               validarCampoObrigatorio(senha);
    }
    
    /**
     * Formata um CPF com pontos e hífen
     * @param cpf CPF a ser formatado
     * @return CPF formatado
     */
    public static String formatarCPF(String cpf) {
        if (cpf == null) {
            return "";
        }
        
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        
        if (cpfNumerico.length() == 11) {
            return cpfNumerico.substring(0, 3) + "." + 
                   cpfNumerico.substring(3, 6) + "." + 
                   cpfNumerico.substring(6, 9) + "-" + 
                   cpfNumerico.substring(9, 11);
        }
        
        return cpf;
    }
}
