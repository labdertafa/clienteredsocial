package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Notificacion;
import com.laboratorio.clienteredsocial.model.NotificationType;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import com.laboratorio.mindsapiinterface.MindsAccountApi;
import com.laboratorio.mindsapiinterface.MindsNotificationApi;
import com.laboratorio.mindsapiinterface.MindsSessionApi;
import com.laboratorio.mindsapiinterface.MindsStatusApi;
import com.laboratorio.mindsapiinterface.impl.MindsAccountApiImpl;
import com.laboratorio.mindsapiinterface.impl.MindsNotificationApiImpl;
import com.laboratorio.mindsapiinterface.impl.MindsSessionApiImpl;
import com.laboratorio.mindsapiinterface.impl.MindsStatusApiImpl;
import com.laboratorio.mindsapiinterface.model.MindsAccount;
import com.laboratorio.mindsapiinterface.model.MindsSession;
import com.laboratorio.mindsapiinterface.model.MindsStatus;
import com.laboratorio.mindsapiinterface.model.response.MindsAccountListResponse;
import com.laboratorio.mindsapiinterface.model.response.MindsNotificationsResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.6
 * @created 12/10/2024
 * @updated 23/02/2025
 */
public class ClienteRedSocialMinds implements ClienteRedSocial {
    private final String username;
    private final String password;

    public ClienteRedSocialMinds(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    private String getUserIdFromURN(String urn) throws Exception {
        String[] partes = urn.split(":");
        if (partes.length != 3) {
            throw new Exception("Formato de URN no válido");
        }
        
        return partes[2];
    }
    
    private String getURNFromUserId(String userId) {
        return "urn:user:" + userId;
    }
    
    /* ***********************************
       Operaciones sobre la entidad Sesion
       *********************************** */
    @Override
    public Session createSession(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Session refreshSession(String email, String refreshToken) throws Exception {
        MindsSessionApi sessionApi = new MindsSessionApiImpl();
        MindsSession session = sessionApi.authenticateUser(this.username, this.password);
        return new Session(session);
    }

    /* ************************************
       Operaciones sobre la entidad Account
       ************************************ */    
    @Override
    public Account getAccountById(String userId) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        String userURN = this.getURNFromUserId(userId);
        MindsAccount account = accountApi.getAccountsById(List.of(userURN)).get(0);
        
        return new Account(account);
    }
    
    @Override
    public Account getAccountByUsername(String username) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        MindsAccount account = accountApi.getAccountByUsername(username);
        return new Account(account);
    }

    // Minds usa varios identificadores. Acá usamos el identificador URN.
    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        String userURN = this.getURNFromUserId(userId);
        MindsAccount account = accountApi.getAccountsById(List.of(userURN)).get(0);
        
        return new Relationship(userId, account.isSubscriber(), account.isSubscribed());
    }
    
    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        return accountApi.getFollowersIds(userId, limit);
    }
    
    @Override
    public List<Account> getFollowers(String userId, int limit) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        MindsAccountListResponse response = accountApi.getFollowers(userId, limit);
        return response.getUsers().stream()
                .map(account -> new Account(account))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getFollowingsIds(String userId, int limit) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        return accountApi.getFollowingsIds(userId, limit);
    }

    @Override
    public List<Account> getFollowings(String userId, int limit) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        MindsAccountListResponse response = accountApi.getFollowings(userId, limit);
        return response.getUsers().stream()
                .map(account -> new Account(account))
                .collect(Collectors.toList());
    }

    // Minds usa varios identificadores. Acá usamos el identificador URN: urn:user:userId.
    @Override
    public boolean followAccount(String userId) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        return accountApi.followAccount(userId);
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        return accountApi.unfollowAccount(userId);
    }
    
    /* ***********************************
       Operaciones sobre la entidad Status
       *********************************** */
    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        MindsStatusApi statusApi = new MindsStatusApiImpl();
        List<MindsStatus> statuses = statusApi.getGlobalTimeline(quantity);
        
        return statuses.stream()
                .map(s -> new Status(s.getGuid(), s.getMessage(), null, s.getOwner_guid(), ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(s.getTime_created())), ZoneId.systemDefault()), ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(s.getTime_updated())), ZoneId.systemDefault())))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean canPostTextStatus() {
        return true;
    }

    @Override
    public Status postStatus(String text) throws Exception {
        MindsStatusApi statusApi = new MindsStatusApiImpl();
        return new Status(statusApi.postStatus(text));
    }
    
    @Override
    public boolean canPostImageStatus() {
        return true;
    }

    @Override
    public Status postStatus(String text, String filePath) throws Exception {
        MindsStatusApi statusApi = new MindsStatusApiImpl();
        return new Status(statusApi.postStatus(text, filePath));
    }

    @Override
    public boolean deleteStatus(String statusId) throws Exception {
        MindsStatusApi statusApi = new MindsStatusApiImpl();
        return statusApi.deleteStatus(statusId);
    }
    
    @Override
    public boolean canDeleteStatus() {
        return true;
    }

    /* *****************************************
       Operaciones sobre la entidad Notificacion
       ****************************************** */
    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        MindsNotificationApi notificationApi = new MindsNotificationApiImpl();
        MindsNotificationsResponse response = notificationApi.getAllNotifications(0, posicionInicial);
        List<Notificacion> notificaciones = response.getNotifications().stream()
                .filter(notif -> notif.getType().equalsIgnoreCase("subscribe"))
                .map(notif -> new Notificacion(notif, NotificationType.FOLLOW))
                .collect(Collectors.toList());
        
        return new NotificationListResponse(response.getLoadNext(), notificaciones);
    }
}