package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Notificacion;
import com.laboratorio.clienteredsocial.model.NotificationType;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.SessionRequest;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import com.laboratorio.mastodonapiinterface.MastodonAccountApi;
import com.laboratorio.mastodonapiinterface.MastodonNotificationApi;
import com.laboratorio.mastodonapiinterface.MastodonStatusApi;
import com.laboratorio.mastodonapiinterface.impl.MastodonAccountApiImpl;
import com.laboratorio.mastodonapiinterface.impl.MastodonNotificationApiImpl;
import com.laboratorio.mastodonapiinterface.impl.MastodonStatusApiImpl;
import com.laboratorio.mastodonapiinterface.model.MastodonAccount;
import com.laboratorio.mastodonapiinterface.model.MastodonNotificationType;
import com.laboratorio.mastodonapiinterface.model.MastodonRelationship;
import com.laboratorio.mastodonapiinterface.model.MastodonStatus;
import com.laboratorio.mastodonapiinterface.model.response.MastodonAccountListResponse;
import com.laboratorio.mastodonapiinterface.model.response.MastodonNotificationListResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.6
 * @created 13/10/2024
 * @updated 04/03/2024
 */
public class ClienteRedSocialMastodon implements ClienteRedSocial {
    private final String urlBase;
    private final String accessToken;
    private final MastodonAccountApi accountApi;
    private final MastodonStatusApi statusApi;

    public ClienteRedSocialMastodon(String urlBase, String accessToken) {
        this.urlBase = urlBase;
        this.accessToken = accessToken;
        this.accountApi = new MastodonAccountApiImpl(this.urlBase, this.accessToken);
        this.statusApi = new MastodonStatusApiImpl(this.urlBase, this.accessToken);
    }
    
    /* ***********************************
       Operaciones sobre la entidad Sesion
       *********************************** */
    @Override
    public Session createSession(SessionRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Session refreshSession(String email, String refreshToken) throws Exception {
        return null;
    }

    /* ************************************
       Operaciones sobre la entidad Account
       ************************************ */
    @Override
    public Account getAccountById(String userId) throws Exception {
        MastodonAccount account = this.accountApi.getAccountById(userId);
        return new Account(account);
    }
    
    @Override
    public Account getAccountByUsername(String username) throws Exception {
        MastodonAccount account = this.accountApi.getAccountByUsername(username);
        return new Account(account);
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        MastodonRelationship relationship = this.accountApi.checkrelationships(List.of(userId)).get(0);
        return new Relationship(userId, relationship.isFollowed_by(), relationship.isFollowing());
    }
    
    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        return this.accountApi.getFollowersIds(userId, limit);
    }
    
    @Override
    public List<Account> getFollowers(String userId, int limit) throws Exception {
        MastodonAccountListResponse response = this.accountApi.getFollowers(userId, limit);
        return response.getAccounts().stream()
                .map(account -> new Account(account))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getFollowingsIds(String userId, int limit) throws Exception {
        return this.accountApi.getFollowingsIds(userId, limit);
    }

    @Override
    public List<Account> getFollowings(String userId, int limit) throws Exception {
        MastodonAccountListResponse response = this.accountApi.getFollowings(userId, limit);
        return response.getAccounts().stream()
                .map(account -> new Account(account))
                .collect(Collectors.toList());
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
        List<MastodonStatus> statuses = this.statusApi.getGlobalTimeline(quantity);
        
        return statuses.stream()
                .map(status -> new Status(status))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean canPostTextStatus() {
        return true;
    }

    @Override
    public Status postStatus(String text) throws Exception {
        return new Status(this.statusApi.postStatus(text));
    }
    
    @Override
    public boolean canPostImageStatus() {
        return true;
    }

    @Override
    public Status postStatus(String text, String filePath) throws Exception {
        return new Status(this.statusApi.postStatus(text, filePath));
    }
    
    @Override
    public boolean canDeleteStatus() {
        return true;
    }
    
    @Override
    public boolean deleteStatus(String statusId) throws Exception {
        MastodonStatus status = this.statusApi.deleteStatus(statusId);
        return (status != null);
    }
    
    /* *****************************************
       Operaciones sobre la entidad Notificacion
       ****************************************** */
    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        MastodonNotificationApi notificationApi = new MastodonNotificationApiImpl(this.urlBase, this.accessToken);
        MastodonNotificationListResponse response = notificationApi.getAllNotifications(0, 0, posicionInicial);
        List<Notificacion> notificaciones = response.getNotifications().stream()
                .filter(notif -> notif.getType().toUpperCase().equals(MastodonNotificationType.FOLLOW.name()))
                .map(notif -> new Notificacion(notif, NotificationType.FOLLOW))
                .collect(Collectors.toList());
        
        return new NotificationListResponse(response.getMinId(), notificaciones);
    }
}