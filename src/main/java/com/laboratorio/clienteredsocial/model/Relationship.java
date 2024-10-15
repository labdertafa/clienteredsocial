package com.laboratorio.clienteredsocial.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 12/10/2024
 * @updated 12/10/2024
 */

@Getter @Setter @AllArgsConstructor
public class Relationship {
    String userId;
    boolean followedBy;
    boolean following;
}