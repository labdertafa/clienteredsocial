package com.laboratorio.clienteredsocial.model;

import java.time.ZonedDateTime;
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
public class Status {
    private String id;
    private String body;
    private String language;
    private String ownerId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @Override
    public String toString() {
        return "Status{" + "id=" + id + ", body=" + body + ", language=" + language + ", ownerId=" + ownerId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}