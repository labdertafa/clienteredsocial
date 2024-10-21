package com.laboratorio.clienteredsocial.response;

import com.laboratorio.clienteredsocial.model.Notificacion;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 21/10/2024
 * @updated 21/10/2024
 */

@Getter @Setter @AllArgsConstructor
public class NotificationListResponse {
    private String cursor;
    private List<Notificacion> notifications;
}