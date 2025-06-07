package com.laboratorio.clienteredsocial.exception;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 27/07/2024
 * @updated 07/06/2025
 */
public class ClienteRedSocialException extends RuntimeException {
     private Throwable causaOriginal = null;
    
    public ClienteRedSocialException(String message) {
        super(message);
    }
    
    public ClienteRedSocialException(String message, Throwable causaOriginal) {
        super(message);
        this.causaOriginal = causaOriginal;
    }
    
    @Override
    public String getMessage() {
        if (this.causaOriginal != null) {
            return super.getMessage() + " | Causa original: " + this.causaOriginal.getMessage();
        }
        
        return super.getMessage();
    }
}