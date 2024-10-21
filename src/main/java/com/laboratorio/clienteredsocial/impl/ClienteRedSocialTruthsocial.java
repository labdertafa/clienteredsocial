package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Notificacion;
import com.laboratorio.clienteredsocial.model.NotificationType;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import com.laboratorio.truthsocialapiinterface.TruthsocialAccountApi;
import com.laboratorio.truthsocialapiinterface.TruthsocialNotificationApi;
import com.laboratorio.truthsocialapiinterface.TruthsocialStatusApi;
import com.laboratorio.truthsocialapiinterface.impl.TruthsocialAccountApiImpl;
import com.laboratorio.truthsocialapiinterface.impl.TruthsocialNotificationApiImpl;
import com.laboratorio.truthsocialapiinterface.impl.TruthsocialStatusApiImpl;
import com.laboratorio.truthsocialapiinterface.model.TruthsocialAccount;
import com.laboratorio.truthsocialapiinterface.model.TruthsocialNotificationType;
import com.laboratorio.truthsocialapiinterface.model.TruthsocialRelationship;
import com.laboratorio.truthsocialapiinterface.model.TruthsocialStatus;
import com.laboratorio.truthsocialapiinterface.model.response.TruthsocialNotificationListResponse;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.3
 * @created 13/10/2024
 * @updated 21/10/2024
 */
public class ClienteRedSocialTruthsocial implements ClienteRedSocial {
    private final String accessToken;
    private final TruthsocialAccountApi accountApi;
    private final TruthsocialStatusApi statusApi;

    public ClienteRedSocialTruthsocial(String accessToken) {
        this.accessToken = accessToken;
        this.accountApi = new TruthsocialAccountApiImpl(this.accessToken);
        this.statusApi = new TruthsocialStatusApiImpl(this.accessToken);
    }

    /* ************************************
       Operaciones sobre la entidad Account
       ************************************ */
    @Override
    public Account getAccountById(String userId) throws Exception {
        TruthsocialAccount account = this.accountApi.getAccountById(userId);
        
        return new Account(account.getId(), ZonedDateTime.parse(account.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME), account.getUsername(), account.getDisplay_name(), null, account.getFollowers_count(), account.getFollowing_count(), account.getStatuses_count());
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        TruthsocialRelationship relationship = this.accountApi.checkrelationships(List.of(userId)).get(0);
        
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
        List<TruthsocialStatus> statuses = this.statusApi.getGlobalTimeline(quantity);
        
        return statuses.stream()
                .map(status -> new Status(status))
                .collect(Collectors.toList());
    }

    @Override
    public Status postStatus(String text) throws Exception {
        return new Status(this.statusApi.postStatus(text));
    }

    @Override
    public Status postStatus(String text, String filePath) throws Exception {
        return new Status(this.statusApi.postStatus(text, filePath));
    }

    @Override
    public boolean deleteStatus(String statusId) throws Exception {
        TruthsocialStatus status = this.statusApi.deleteStatus(statusId);
        return (status != null);
    }

    /* *****************************************
       Operaciones sobre la entidad Notificacion
       ****************************************** */
    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        TruthsocialNotificationApi notificationApi = new TruthsocialNotificationApiImpl(this.accessToken);
        TruthsocialNotificationListResponse response = notificationApi.getAllNotifications(0, 0, posicionInicial);
        List<Notificacion> notificaciones = response.getNotifications().stream()
                .filter(notif -> notif.getType().toUpperCase().equals(TruthsocialNotificationType.FOLLOW.name()))
                .map(notif -> new Notificacion(notif, NotificationType.FOLLOW))
                .collect(Collectors.toList());
        
        return new NotificationListResponse(response.getMinId(), notificaciones);
    }
}