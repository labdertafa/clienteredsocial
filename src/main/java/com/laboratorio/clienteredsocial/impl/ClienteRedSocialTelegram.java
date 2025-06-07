package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.exception.ClienteRedSocialException;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.SessionRequest;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import com.laboratorio.telegramapiinterface.TelegramStatusApi;
import com.laboratorio.telegramapiinterface.impl.TelegramStatusApiImpl;
import java.util.List;

/**
 *
 * @author Rafael
 * @version 1.3
 * @created 17/10/2024
 * @updated 07/06/2025
 */
public class ClienteRedSocialTelegram implements ClienteRedSocial {
    private final String accessToken;
    private final String chatId;
    private final TelegramStatusApi statusApi;

    public ClienteRedSocialTelegram(String accessToken, String chatId) {
        this.accessToken = accessToken;
        this.chatId = chatId;
        this.statusApi = new TelegramStatusApiImpl(this.accessToken, this.chatId);
    }
    
    @Override
    public Session createSession(SessionRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Session refreshSession(String email, String refreshToken) throws Exception {
        return null;
    }

    @Override
    public Account getAccountById(String userId) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Telegram");
    }
    
    @Override
    public Account getAccountByUsername(String username) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Telegram");
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Telegram");
    }
    
    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Telegram");
    }
    
    @Override
    public List<Account> getFollowers(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Telegram");
    }
    
    @Override
    public List<String> getFollowingsIds(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Telegram");
    }

    @Override
    public List<Account> getFollowings(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Telegram");
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Telegram");
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Telegram");
    }

    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Telegram");
    }
    
    @Override
    public boolean canPostTextStatus() {
        return true;
    }

    @Override
    public Status postStatus(String text) throws Exception {
        return new Status(this.statusApi.postStatus(text), chatId);
    }
    
    @Override
    public boolean canPostImageStatus() {
        return true;
    }

    @Override
    public Status postStatus(String text, String filePath) throws Exception {
        return new Status(this.statusApi.postStatus(text, filePath), chatId);
    }
    
    @Override
    public boolean canDeleteStatus() {
        return true;
    }

    @Override
    public boolean deleteStatus(String statusId) throws Exception {
        return this.statusApi.deleteStatus(Integer.parseInt(statusId));
    }   

    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Telegram");
    }
}