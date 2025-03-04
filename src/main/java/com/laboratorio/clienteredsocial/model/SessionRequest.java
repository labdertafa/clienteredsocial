package com.laboratorio.clienteredsocial.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 04/03/2025
 * @updated 04/03/2025
 */

@Getter @Setter @AllArgsConstructor
public class SessionRequest {
    private String username;
    private String email;
    private String password;
}