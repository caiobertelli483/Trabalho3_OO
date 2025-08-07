package com.franquias.exceptions;

/**
 * Exception personalizada para operações de validação
 */
public class ValidacaoException extends Exception {
    
    public ValidacaoException(String message) {
        super(message);
    }
    
    public ValidacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}

