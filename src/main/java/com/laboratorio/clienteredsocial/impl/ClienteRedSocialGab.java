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
import com.laboratorio.gabapiinterface.GabAccountApi;
import com.laboratorio.gabapiinterface.GabNotificationApi;
import com.laboratorio.gabapiinterface.GabStatusApi;
import com.laboratorio.gabapiinterface.impl.GabAccountApiImpl;
import com.laboratorio.gabapiinterface.impl.GabNotificationApiImpl;
import com.laboratorio.gabapiinterface.impl.GabStatusApiImpl;
import com.laboratorio.gabapiinterface.model.GabAccount;
import com.laboratorio.gabapiinterface.model.GabRelationship;
import com.laboratorio.gabapiinterface.model.GabStatus;
import com.laboratorio.gabapiinterface.model.response.GabAccountListResponse;
import com.laboratorio.gabapiinterface.model.response.GabNotificationListResponse;
import com.laboratorio.mastodonapiinterface.model.MastodonNotificationType;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.6
 * @created 13/10/2024
 * @updated 04/03/2025
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
        GabAccount account = this.accountApi.getAccountById(userId);
        return new Account(account);
    }
    
    @Override
    public Account getAccountByUsername(String username) throws Exception {
        GabAccount account = this.accountApi.getAccountByUsername(username);
        return new Account(account);
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        GabRelationship relationship = this.accountApi.checkrelationships(List.of(userId)).get(0);
        
        return new Relationship(userId, relationship.isFollowed_by(), relationship.isFollowing());
    }
    
    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        return this.accountApi.getFollowersIds(userId, limit);
    }
    
    @Override
    public List<Account> getFollowers(String userId, int limit) throws Exception {
        GabAccountListResponse response = this.accountApi.getFollowers(userId, limit);
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
        GabAccountListResponse response = this.accountApi.getFollowings(userId, limit);
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
        List<GabStatus> statuses = this.statusApi.getGlobalTimeline(quantity);
        
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
        // Si la publicación hace mención de la página del laboratorio de rafa
        if (text.contains("laboratoriorafa.mooo.com")) {
            log.info("En Instagram, no se puede mencionar la página del Laboratorio. Se descarta la publicación.");
            return null;
        }
        
        return new Status(this.statusApi.postStatus(text));
    }
    
    @Override
    public boolean canPostImageStatus() {
        return true;
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
    public boolean canDeleteStatus() {
        return true;
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