package com.laboratorio.clienteredsocial.model;

import com.laboratorio.blueskyapiinterface.model.BlueskyNotification;
import com.laboratorio.gabapiinterface.model.GabNotification;
import com.laboratorio.getrapiinterface.modelo.GettrNotification;
import com.laboratorio.mastodonapiinterface.model.MastodonNotification;
import com.laboratorio.mindsapiinterface.model.MindsNotification;
import com.laboratorio.parlerapiinterface.model.ParlerNotification;
import com.laboratorio.truthsocialapiinterface.model.TruthsocialNotification;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
public class Notificacion {
    private String id;
    private ZonedDateTime createdAt;
    private NotificationType type;
    private String userId;
    
    public Notificacion(MastodonNotification notification, NotificationType type) {
        this.id = notification.getId();
        this.createdAt = ZonedDateTime.parse(notification.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.type = type;
        this.userId = notification.getAccount().getId();
    }
    
    public Notificacion(BlueskyNotification notification, NotificationType type) {
        this.id = notification.getUri();
        this.createdAt = ZonedDateTime.parse(notification.getIndexedAt(), DateTimeFormatter.ISO_DATE_TIME);
        this.type = type;
        this.userId = notification.getAuthor().getDid();
    }
    
    public Notificacion(TruthsocialNotification notification, NotificationType type) {
        this.id = notification.getId();
        this.createdAt = ZonedDateTime.parse(notification.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.type = type;
        this.userId = notification.getAccount().getId();
    }
    
    public Notificacion(GettrNotification notification, NotificationType type) {
        this.id = Long.toString(notification.getMsgId());
        this.createdAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(notification.getCdate()), ZoneId.systemDefault());
        this.type = type;
        this.userId = notification.getOthr().get(0).getI();
    }
    
    public Notificacion(GabNotification notification, NotificationType type) {
        this.id = notification.getId();
        this.createdAt = ZonedDateTime.parse(notification.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.type = type;
        this.userId = notification.getAccount().getId();
    }
    
    public Notificacion(MindsNotification notification, NotificationType type) {
        this.id = notification.getUuid();
        this.createdAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(notification.getCreated_timestamp()), ZoneId.systemDefault());
        this.type = type;
        this.userId = notification.getFrom_guid();
    }
    
    public Notificacion(ParlerNotification notification, NotificationType type) {
        this.id = notification.getNotificationId();
        this.createdAt = ZonedDateTime.parse(notification.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME);
        this.type = type;
        this.userId = notification.getFromUserId();
    }
}