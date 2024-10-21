package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Notificacion;
import com.laboratorio.clienteredsocial.model.NotificationType;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import com.laboratorio.gabapiinterface.GabAccountApi;
import com.laboratorio.gabapiinterface.GabNotificationApi;
import com.laboratorio.gabapiinterface.GabStatusApi;
import com.laboratorio.gabapiinterface.impl.GabAccountApiImpl;
import com.laboratorio.gabapiinterface.impl.GabNotificationApiImpl;
import com.laboratorio.gabapiinterface.impl.GabStatusApiImpl;
import com.laboratorio.gabapiinterface.model.GabAccount;
import com.laboratorio.gabapiinterface.model.GabRelationship;
import com.laboratorio.gabapiinterface.model.GabStatus;
import com.laboratorio.gabapiinterface.model.response.GabNotificationListResponse;
import com.laboratorio.mastodonapiinterface.model.MastodonNotificationType;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.3
 * @created 13/10/2024
 * @updated 21/10/2024
 */
public class ClienteRedSocialGab implements ClienteRedSocial {
    private static final Logger log = LogManager.getLogger(ClienteRedSocialGab.class);
    private final String accessToken;
    private final GabAccountApi accountApi;
    private final GabStatusApi statusApi;

    public ClienteRedSocialGab(String accessToken) {
        this.accessToken = accessToken;
        this.accountApi = new GabAccountApiImpl(this.accessToken);
        this.statusApi = new GabStatusApiImpl(this.accessToken);
    }

    /* ************************************
       Operaciones sobre la entidad Account
       ************************************ */
    @Override
    public Account getAccountById(String userId) throws Exception {
        GabAccount account = this.accountApi.getAccountById(userId);
        
        return new Account(account.getId(), ZonedDateTime.parse(account.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME), account.getUsername(), account.getDisplay_name(), null, account.getFollowers_count(), account.getFollowing_count(), account.getStatuses_count());
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        GabRelationship relationship = this.accountApi.checkrelationships(List.of(userId)).get(0);
        
        return new Relationship(userId, relationship.isFollowed_by(), relationship.isFollowing());
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        return this.accountApi.followAccount(userId);
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        return this.accountApi.unfollowAccount(userId);
    }

    /* ***********************************
       Operaciones sobre la entidad Status
       *********************************** */    
    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        List<GabStatus> statuses = this.statusApi.getGlobalTimeline(quantity);
        
        return statuses.stream()
                .map(status -> new Status(status))
                .collect(Collectors.toList());
    }

    @Override
    public Status postStatus(String text) throws Exception {
        // Si la publicación hace mención de la página del laboratorio de rafa
        if (text.contains("laboratoriorafa.mooo.com")) {
            log.info("En Instagram, no se puede mencionar la página del Laboratorio. Se descarta la publicación.");
            return null;
        }
        
        return new Status(this.statusApi.postStatus(text));
    }

    @Override
    public Status postStatus(String text, String filePath) throws Exception {
        // Si la publicación hace mención de la página del laboratorio de rafa
        if (text.contains("laboratoriorafa.mooo.com")) {
            log.info("En Instagram, no se puede mencionar la página del Laboratorio. Se descarta la publicación.");
            return null;
        }
        
        return new Status(this.statusApi.postStatus(text, filePath));
    }

    @Override
    public boolean deleteStatus(String statusId) throws Exception {
        return this.statusApi.deleteStatus(statusId);
    }

    /* *****************************************
       Operaciones sobre la entidad Notificacion
       ****************************************** */
    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        GabNotificationApi notificationApi = new GabNotificationApiImpl(this.accessToken);
        GabNotificationListResponse response = notificationApi.getAllNotifications(0, 0, posicionInicial);
        List<Notificacion> notificaciones = response.getNotifications().stream()
                .filter(notif -> notif.getType().toUpperCase().equals(MastodonNotificationType.FOLLOW.name()))
                .map(notif -> new Notificacion(notif, NotificationType.FOLLOW))
                .collect(Collectors.toList());
        
        return new NotificationListResponse(response.getMinId(), notificaciones);
    }
}