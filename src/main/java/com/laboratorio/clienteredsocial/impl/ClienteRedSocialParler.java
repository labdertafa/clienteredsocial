package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Notificacion;
import com.laboratorio.clienteredsocial.model.NotificationType;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import com.laboratorio.parlerapiinterface.ParlerAccountApi;
import com.laboratorio.parlerapiinterface.ParlerNotificationApi;
import com.laboratorio.parlerapiinterface.ParlerSessionApi;
import com.laboratorio.parlerapiinterface.ParlerStatusApi;
import com.laboratorio.parlerapiinterface.impl.ParlerAccountApiImpl;
import com.laboratorio.parlerapiinterface.impl.ParlerNotificationApiImpl;
import com.laboratorio.parlerapiinterface.impl.ParlerSessionApiImpl;
import com.laboratorio.parlerapiinterface.impl.ParlerStatusApiImpl;
import com.laboratorio.parlerapiinterface.model.ParlerAccount;
import com.laboratorio.parlerapiinterface.model.ParlerAccountList;
import com.laboratorio.parlerapiinterface.model.ParlerNotificationList;
import com.laboratorio.parlerapiinterface.model.ParlerSession;
import com.laboratorio.parlerapiinterface.model.ParlerStatus;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.6
 * @created 12/10/2024
 * @updated 09/01/2025
 */
public class ClienteRedSocialParler implements ClienteRedSocial {
    private final String accessToken;
    private final String password;
    private final ParlerAccountApi accountApi;
    private final ParlerStatusApi statusApi;

    public ClienteRedSocialParler(String accessToken, String password) {
        this.accessToken = accessToken;
        this.password = password;
        this.accountApi = new ParlerAccountApiImpl(this.accessToken);
        this.statusApi = new ParlerStatusApiImpl(this.accessToken);
    }
    
    /* ***********************************
       Operaciones sobre la entidad Sesion
       *********************************** */
    @Override
    public Session refreshSession(String email, String refreshToken) throws Exception {
        ParlerSessionApi sessionApi  = new ParlerSessionApiImpl(email, this.password);
        ParlerSession session = session = sessionApi.authenticateUser();
        return new Session(session);
    }

    /* ************************************
       Operaciones sobre la entidad Account
       ************************************ */    
    @Override
    public Account getAccountById(String userId) throws Exception {
        ParlerAccount account = this.accountApi.getAccountsById(List.of(userId)).get(0);
        return new Account(account);
    }
    
    @Override
    public Account getAccountByUsername(String username) throws Exception {
        ParlerAccount account = this.accountApi.getAccountByUsername(username);
        return new Account(account);
    }
    
    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        ParlerAccount account = this.accountApi.getAccountsById(List.of(userId)).get(0);
        
        return new Relationship(userId, account.getProfileEngagement().isFollowingYou(), account.getProfileEngagement().isFollowing());
    }
    
    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        List<ParlerAccount> accounts = this.accountApi.getAccountsById(List.of(userId));
        return this.accountApi.getFollowersIds(accounts.get(0).getUsername());
    }
    
    @Override
    public List<Account> getFollowers(String userId, int limit) throws Exception {
        List<ParlerAccount> accounts = this.accountApi.getAccountsById(List.of(userId));
        ParlerAccountList response = this.accountApi.getFollowers(accounts.get(0).getUsername());
        return response.getAccounts().stream()
                .map(account -> new Account(account))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getFollowingsIds(String userId, int limit) throws Exception {
        List<ParlerAccount> accounts = this.accountApi.getAccountsById(List.of(userId));
        return this.accountApi.getFollowingsIds(accounts.get(0).getUsername());
    }

    @Override
    public List<Account> getFollowings(String userId, int limit) throws Exception {
        List<ParlerAccount> accounts = this.accountApi.getAccountsById(List.of(userId));
        ParlerAccountList response = this.accountApi.getFollowings(accounts.get(0).getUsername());
        return response.getAccounts().stream()
                .map(account -> new Account(account))
                .collect(Collectors.toList());
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