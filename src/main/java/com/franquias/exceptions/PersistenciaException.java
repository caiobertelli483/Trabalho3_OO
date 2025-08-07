package com.franquias.exceptions;

/**
 * Exception personalizada para operações de persistência de dados
 */
public class PersistenciaException extends Exception {
    
    public PersistenciaException(String message) {
        super(message);
    }
    
    public PersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }
}

