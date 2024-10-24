package com.laboratorio.clienteredsocial;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 27/07/2024
 * @updated 24/10/2024
 */
public class ClienteRedSocialException extends RuntimeException {
    private static final Logger log = LogManager.getLogger(ClienteRedSocialException.class);
    
    public ClienteRedSocialException(String className, String message) {
        super(message);
        log.error(String.format("Error %s: %s", className, message));
    }
    
    public ClienteRedSocialException(String message, Exception e) {
        super(message);
        log.error(String.format("Error: %s", message));
        log.error(String.format("Error: %s", e.getMessage()));
        if (e.getCause() != null) {
            log.error(String.format("Causa: %s", e.getCause().getMessage()));
        }
    }
}