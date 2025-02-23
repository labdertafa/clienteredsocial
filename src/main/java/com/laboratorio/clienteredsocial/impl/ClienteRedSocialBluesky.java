package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.blueskyapiinterface.BlueskyAccountApi;
import com.laboratorio.blueskyapiinterface.BlueskyNotificationApi;
import com.laboratorio.blueskyapiinterface.BlueskySessionApi;
import com.laboratorio.blueskyapiinterface.BlueskyStatusApi;
import com.laboratorio.blueskyapiinterface.impl.BlueskyAccountApiImpl;
import com.laboratorio.blueskyapiinterface.impl.BlueskyNotificationApiImpl;
import com.laboratorio.blueskyapiinterface.impl.BlueskySessionApiImpl;
import com.laboratorio.blueskyapiinterface.impl.BlueskyStatusApiImpl;
import com.laboratorio.blueskyapiinterface.model.BlueskyAccount;
import com.laboratorio.blueskyapiinterface.model.BlueskyNotificationType;
import com.laboratorio.blueskyapiinterface.model.BlueskyRelationship;
import com.laboratorio.blueskyapiinterface.model.BlueskySession;
import com.laboratorio.blueskyapiinterface.model.BlueskyStatus;
import com.laboratorio.blueskyapiinterface.model.response.BlueskyFollowListResponse;
import com.laboratorio.blueskyapiinterface.model.response.BlueskyNotificationListResponse;
import com.laboratorio.blueskyapiinterface.model.response.BlueskyRelationshipsResponse;
import com.laboratorio.clientapilibrary.utils.ImageMetadata;
import com.laboratorio.clientapilibrary.utils.PostUtils;
import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Notificacion;
import com.laboratorio.clienteredsocial.model.NotificationType;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.8
 * @created 15/10/2024
 * @updated 23/02/2025
 */
public class ClienteRedSocialBluesky implements ClienteRedSocial {
    private final String accountId;
    private final String accessToken;
    private final BlueskyAccountApi accountApi;
    private final BlueskyStatusApi statusApi;

    public ClienteRedSocialBluesky(String accountId, String accessToken) {
        this.accountId = accountId;
        this.accessToken = accessToken;
        this.accountApi = new BlueskyAccountApiImpl(this.accessToken);
        this.statusApi = new BlueskyStatusApiImpl(this.accessToken);
    }

    /* ***********************************
       Operaciones sobre la entidad Sesion
       *********************************** */
    @Override
    public Session createSession(String username, String password) {
        BlueskySessionApi sessionApi = new BlueskySessionApiImpl(this.accessToken, "");
        BlueskySession session = sessionApi.createSession(username, password);
        return new Session(session);
    }
    
    @Override
    public Session refreshSession(String email, String refreshToken) throws Exception {
        BlueskySessionApi sessionApi = new BlueskySessionApiImpl(this.accessToken, refreshToken);
        BlueskySession session = sessionApi.refreshSession();
        return new Session(session);
    }
    
    /* ************************************
       Operaciones sobre la entidad Account
       ************************************ */
    @Override
    public Account getAccountById(String userId) throws Exception {
        BlueskyAccount account = this.accountApi.getAccountById(userId);
        return new Account(account);
    }
    
    @Override
    public Account getAccountByUsername(String username) throws Exception {
        BlueskyAccount account = this.accountApi.getAccountByUsername(username);
        return new Account(account);
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        BlueskyRelationshipsResponse relationshipsResponse = this.accountApi.checkrelationships(this.accountId, List.of(userId));
        BlueskyRelationship relationship = relationshipsResponse.getRelationships().get(0);
        
        return new Relationship(userId, relationship.isFollowedBy(), relationship.isFollowing());
    }
    
    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        return this.accountApi.getFollowersIds(userId, limit);
    }
    
    @Override
    public List<Account> getFollowers(String userId, int limit) throws Exception {
        BlueskyFollowListResponse response = this.accountApi.getFollowers(userId, limit);
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
        BlueskyFollowListResponse response = this.accountApi.getFollowings(userId, limit);
        return response.getAccounts().stream()
                .map(account -> new Account(account))
                .collect(Collectors.toList());
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        return this.accountApi.followAccount(this.accountId, userId);
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        BlueskyRelationshipsResponse response = this.accountApi.checkrelationships(this.accountId, List.of(userId));
        String uri = response.getRelationships().get(0).getFollowing();
        if (uri == null) {
            return false;
        }
        return this.accountApi.unfollowAccount(this.accountId, uri);
    }

    /* ***********************************
       Operaciones sobre la entidad Status
       *********************************** */    
    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        List<BlueskyStatus> statuses = this.statusApi.getGlobalTimeline(quantity);
        
        return statuses.stream()
                .map(status -> new Status(status))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean canPostTextStatus() {
        return true;
    }

    // Nota: en Bluesky, un Post es un Record y se busca usando su uri
    @Override
    public Status postStatus(String text) throws Exception {
        return new Status(this.statusApi.postStatus(this.accountId, text));
    }

    @Override
    public boolean canPostImageStatus() {
        return true;
    }
    
    // Nota: en Bluesky, un Post es un Record y se busca usando su uri
    @Override
    public Status postStatus(String text, String filePath) throws Exception {
        ImageMetadata metadata = PostUtils.extractImageMetadata(filePath);
        return new Status(this.statusApi.postStatus(this.accountId, text, filePath, metadata.getMimeType()));
    }
    
    @Override
    public boolean canDeleteStatus() {
        return true;
    }

    // Nota: en Bluesky, un Post es un Record y se busca usando su uri
    @Override
    public boolean deleteStatus(String uri) throws Exception {
        return this.statusApi.deleteStatus(this.accountId, uri);
    }

    /* *****************************************
       Operaciones sobre la entidad Notificacion
       ****************************************** */
    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        BlueskyNotificationApi notificationApi = new BlueskyNotificationApiImpl(this.accessToken);
        BlueskyNotificationListResponse response = notificationApi.getAllNotifications(0, posicionInicial);
        List<Notificacion> notificaciones = response.getNotifications().stream()
                .filter(notif -> notif.getReason().toUpperCase().equals(BlueskyNotificationType.FOLLOW.name()))
                .map(notif -> new Notificacion(notif, NotificationType.FOLLOW))
                .collect(Collectors.toList());
        
        return new NotificationListResponse(response.getSeenAt(), notificaciones);
    }
}