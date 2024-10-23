package com.laboratorio.clienteredsocial;

import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import java.util.List;

/**
 *
 * @author Rafael
 * @version 1.4
 * @created 12/10/2024
 * @updated 22/10/2024
 */
public interface ClienteRedSocial {
    // Operaciones sobre la entidad Account
    Account getAccountById(String userId) throws Exception;
    
    Relationship checkrelationship(String userId) throws Exception;
    
    List<String> getFollowersIds(String userId, int limit) throws Exception;
    List<Account> getFollowers(String userId, int limit) throws Exception;
    
    List<String> getFollowingsIds(String userId, int limit) throws Exception;
    List<Account> getFollowings(String userId, int limit) throws Exception;
    
    boolean followAccount(String userId) throws Exception;
    
    boolean unfollowAccount(String userId) throws Exception;
    
    // Operaciones sobre la entidad Status
    List<Status> getGlobalTimeline(int quantity) throws Exception;
    
    Status postStatus(String text) throws Exception;
    
    Status postStatus(String text, String filePath) throws Exception;
    
    boolean deleteStatus(String statusId) throws Exception;
    
    // Operaciones sobre la entidad Notificacion
    NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception;
}