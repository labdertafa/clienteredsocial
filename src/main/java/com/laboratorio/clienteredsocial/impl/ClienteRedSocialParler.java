package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Notificacion;
import com.laboratorio.clienteredsocial.model.NotificationType;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import com.laboratorio.parlerapiinterface.ParlerAccountApi;
import com.laboratorio.parlerapiinterface.ParlerNotificationApi;
import com.laboratorio.parlerapiinterface.ParlerStatusApi;
import com.laboratorio.parlerapiinterface.impl.ParlerAccountApiImpl;
import com.laboratorio.parlerapiinterface.impl.ParlerNotificationApiImpl;
import com.laboratorio.parlerapiinterface.impl.ParlerStatusApiImpl;
import com.laboratorio.parlerapiinterface.model.ParlerAccount;
import com.laboratorio.parlerapiinterface.model.ParlerNotificationList;
import com.laboratorio.parlerapiinterface.model.ParlerStatus;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.3
 * @created 12/10/2024
 * @updated 21/10/2024
 */
public class ClienteRedSocialParler implements ClienteRedSocial {
    private final String accessToken;
    private final ParlerAccountApi accountApi;
    private final ParlerStatusApi statusApi;

    public ClienteRedSocialParler(String accessToken) {
        this.accessToken = accessToken;
        this.accountApi = new ParlerAccountApiImpl(this.accessToken);
        this.statusApi = new ParlerStatusApiImpl(this.accessToken);
    }

    /* ************************************
       Operaciones sobre la entidad Account
       ************************************ */    
    @Override
    public Account getAccountById(String userId) throws Exception {
        ParlerAccount account = this.accountApi.getAccountsById(List.of(userId)).get(0);
        
        return new Account(account.getUlid(), null, account.getUsername(), account.getName(), null, account.getFollowers(), account.getFollowing(), account.getPostCount());
    }
    
    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        ParlerAccount account = this.accountApi.getAccountsById(List.of(userId)).get(0);
        
        return new Relationship(userId, account.getProfileEngagement().isFollowingYou(), account.getProfileEngagement().isFollowing());
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        List<ParlerAccount> accounts = this.accountApi.getAccountsById(List.of(userId));
        return this.accountApi.followAccount(accounts.get(0).getUsername());
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        List<ParlerAccount> accounts = this.accountApi.getAccountsById(List.of(userId));
        return this.accountApi.unfollowAccount(accounts.get(0).getUsername());
    }

    /* ***********************************
       Operaciones sobre la entidad Status
       *********************************** */
    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        List<ParlerStatus> statuses = this.statusApi.getGlobalTimeline(quantity);
        
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
        return this.statusApi.deleteStatus(statusId);
    }

    /* *****************************************
       Operaciones sobre la entidad Notificacion
       ****************************************** */
    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        ParlerNotificationApi notificationApi = new ParlerNotificationApiImpl(this.accessToken);
        ParlerNotificationList response = notificationApi.getAllNotifications(posicionInicial);
        List<Notificacion> notificaciones = response.getNotifications().stream()
                .filter(notif -> notif.getNotificationType().equalsIgnoreCase("follow"))
                .map(notif -> new Notificacion(notif, NotificationType.FOLLOW))
                .collect(Collectors.toList());
        
        return new NotificationListResponse(response.getCursor(), notificaciones);
    }
}