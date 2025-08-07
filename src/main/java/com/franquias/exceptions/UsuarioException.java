package com.franquias.exceptions;

/**
 * Exception personalizada para operações de usuário
 */
public class UsuarioException extends Exception {
    
    public UsuarioException(String message) {
        super(message);
    }
    
    public UsuarioException(String message, Throwable cause) {
        super(message, cause);
    }
}
