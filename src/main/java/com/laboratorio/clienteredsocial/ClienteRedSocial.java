package com.laboratorio.clienteredsocial;

import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.SessionRequest;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import java.util.List;

/**
 *
 * @author Rafael
 * @version 1.8
 * @created 12/10/2024
 * @updated 04/03/2025
 */
public interface ClienteRedSocial {
    // Operaciones sobre la sesión
    Session createSession(SessionRequest request);
    // Devuelve los datos de la nueva sesión en caso de que se hayan refrescado o nulo en otro caso
    Session refreshSession(String email, String refreshToken) throws Exception;
    
    // Operaciones sobre la entidad Account
    Account getAccountById(String userId) throws Exception;
    Account getAccountByUsername(String username) throws Exception;
    
    Relationship checkrelationship(String userId) throws Exception;
    
    List<String> getFollowersIds(String userId, int limit) throws Exception;
    List<Account> getFollowers(String userId, int limit) throws Exception;
    
    List<String> getFollowingsIds(String userId, int limit) throws Exception;
    List<Account> getFollowings(String userId, int limit) throws Exception;
    
    boolean followAccount(String userId) throws Exception;
    
    boolean unfollowAccount(String userId) throws Exception;
    
    // Operaciones sobre la entidad Status
    List<Status> getGlobalTimeline(int quantity) throws Exception;
    
    boolean canPostTextStatus();
    Status postStatus(String text) throws Exception;
    
    boolean canPostImageStatus();
    Status postStatus(String text, String filePath) throws Exception;
    
    boolean canDeleteStatus();
    boolean deleteStatus(String statusId) throws Exception;
    
    // Operaciones sobre la entidad Notificacion
    NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception;
}