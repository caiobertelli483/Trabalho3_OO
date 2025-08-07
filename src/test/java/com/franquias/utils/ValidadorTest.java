package com.franquias.utils;

import com.franquias.utils.Validador;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes JUnit 5 para a classe Validador
 */
@DisplayName("Testes de Validação")
public class ValidadorTest {
    
    @Test
    @DisplayName("Teste de validação de CPF")
    public void testValidarCPF() {
        // Testes com CPF válido
        assertTrue(Validador.validarCPF("11144477735"), "CPF válido deve retornar true");
        assertTrue(Validador.validarCPF("111.444.777-35"), "CPF formatado válido deve retornar true");
        
        // Testes com CPF inválido
        assertFalse(Validador.validarCPF("12345678901"), "CPF inválido deve retornar false");
        assertFalse(Validador.validarCPF("11111111111"), "CPF com dígitos repetidos deve retornar false");
        assertFalse(Validador.validarCPF(null), "CPF nulo deve retornar false");
        assertFalse(Validador.validarCPF(""), "CPF vazio deve retornar false");
        assertFalse(Validador.validarCPF("123456789"), "CPF com menos de 11 dígitos deve retornar false");
    }
    
    @Test
    @DisplayName("Teste de validação de nome")
    public void testValidarNome() {
        assertTrue(Validador.validarNome("João Silva"), "Nome válido deve retornar true");
        assertTrue(Validador.validarNome("Jo"), "Nome com dois caracteres deve retornar true");
        assertFalse(Validador.validarNome("J"), "Nome com um caractere deve retornar false");
        assertFalse(Validador.validarNome(null), "Nome nulo deve retornar false");
        assertFalse(Validador.validarNome(""), "Nome vazio deve retornar false");
    }
    
    @Test
    @DisplayName("Teste de validação de email")
    public void testValidarEmail() {
        assertTrue(Validador.validarEmail("teste@email.com"), "Email válido deve retornar true");
        assertTrue(Validador.validarEmail("usuario.teste@dominio.com.br"), "Email complexo válido deve retornar true");
        assertFalse(Validador.validarEmail("email_sem_arroba.com"), "Email sem @ deve retornar false");
        assertFalse(Validador.validarEmail("@dominio.com"), "Email sem usuário deve retornar false");
        assertFalse(Validador.validarEmail("usuario@"), "Email sem domínio deve retornar false");
        assertFalse(Validador.validarEmail(null), "Email nulo deve retornar false");
        assertFalse(Validador.validarEmail(""), "Email vazio deve retornar false");
    }
    
    @Test
    @DisplayName("Teste de validação de telefone")
    public void testValidarTelefone() {
        assertTrue(Validador.validarTelefone("(11) 99999-9999"), "Telefone válido deve retornar true");
        assertTrue(Validador.validarTelefone("11999999999"), "Telefone sem formatação deve retornar true");
        assertTrue(Validador.validarTelefone("1199999999"), "Telefone com 10 dígitos deve retornar true");
        assertFalse(Validador.validarTelefone("123456789"), "Telefone com menos de 10 dígitos deve retornar false");
        assertFalse(Validador.validarTelefone(null), "Telefone nulo deve retornar false");
        assertFalse(Validador.validarTelefone(""), "Telefone vazio deve retornar false");
    }
    
    @Test
    @DisplayName("Teste de validação de valor positivo")
    public void testValidarValorPositivo() {
        assertTrue(Validador.validarValorPositivo(100.50), "Valor positivo deve retornar true");
        assertTrue(Validador.validarValorPositivo(0.01), "Valor próximo a zero deve retornar true");
        assertFalse(Validador.validarValorPositivo(0.0), "Zero deve retornar false");
        assertFalse(Validador.validarValorPositivo(-10.5), "Valor negativo deve retornar false");
    }
    
    @Test
    @DisplayName("Teste de formatação de CPF")
    public void testFormatarCPF() {
        assertEquals("111.444.777-35", Validador.formatarCPF("11144477735"), "CPF deve ser formatado corretamente");
        assertEquals("123.456.789-01", Validador.formatarCPF("12345678901"), "CPF deve ser formatado corretamente");
        assertEquals("", Validador.formatarCPF(null), "CPF nulo deve retornar string vazia");
        assertEquals("", Validador.formatarCPF(""), "CPF vazio deve retornar string vazia");
        assertEquals("123", Validador.formatarCPF("123"), "CPF inválido deve retornar o mesmo valor");
    }
    
    @Test
    @DisplayName("Teste de validação completa de dados do usuário")
    public void testValidarDadosUsuario() {
        // Teste com dados válidos
        assertTrue(Validador.validarDadosUsuario("João Silva", "11144477735", "joao@email.com", "senha123"), 
                  "Dados válidos devem retornar true");
        
        // Teste com nome inválido
        assertFalse(Validador.validarDadosUsuario("", "11144477735", "joao@email.com", "senha123"),
                   "Nome inválido deve retornar false");
        
        // Teste com CPF inválido
        assertFalse(Validador.validarDadosUsuario("João Silva", "12345678901", "joao@email.com", "senha123"),
                   "CPF inválido deve retornar false");
        
        // Teste com email inválido
        assertFalse(Validador.validarDadosUsuario("João Silva", "11144477735", "email_invalido", "senha123"),
                   "Email inválido deve retornar false");
        
        // Teste com senha inválida
        assertFalse(Validador.validarDadosUsuario("João Silva", "11144477735", "joao@email.com", ""),
                   "Senha inválida deve retornar false");
    }
}

