package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import com.laboratorio.twitterapiinterface.TwitterAccountApi;
import com.laboratorio.twitterapiinterface.TwitterSessionApi;
import com.laboratorio.twitterapiinterface.TwitterStatusApi;
import com.laboratorio.twitterapiinterface.impl.TwitterAccountApiImpl;
import com.laboratorio.twitterapiinterface.impl.TwitterSessionApiImpl;
import com.laboratorio.twitterapiinterface.impl.TwitterStatusApiImpl;
import com.laboratorio.twitterapiinterface.model.TwitterAccount;
import com.laboratorio.twitterapiinterface.model.TwitterSession;
import com.laboratorio.twitterapiinterface.model.TwitterStatus;
import java.util.List;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 19/12/2024
 * @updated 23/02/2025
 */
public class ClienteRedSocialTwitter implements ClienteRedSocial {
    private final String accessToken;
    private final TwitterAccountApi accountApi;
    private final TwitterStatusApi statusApi;

    public ClienteRedSocialTwitter(String accessToken) {
        this.accessToken = accessToken;
        this.accountApi = new TwitterAccountApiImpl(accessToken);
        this.statusApi = new TwitterStatusApiImpl(accessToken);
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
        TwitterSessionApi sessionApi = new TwitterSessionApiImpl(this.accessToken, refreshToken);
        TwitterSession session = sessionApi.refreshSession();
        return new Session(session);
    }

    /* ************************************
       Operaciones sobre la entidad Account
       ************************************ */
    @Override
    public Account getAccountById(String userId) throws Exception {
        TwitterAccount account = this.accountApi.getAccountById(userId);
        return new Account(account);
    }
    
    @Override
    public Account getAccountByUsername(String username) throws Exception {
        TwitterAccount account = this.accountApi.getAccountByUsername(username);
        return new Account(account);
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Account> getFollowers(String userId, int limit) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<String> getFollowingsIds(String userId, int limit) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Account> getFollowings(String userId, int limit) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /* ***********************************
       Operaciones sobre la entidad Status
       *********************************** */
    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public boolean canPostTextStatus() {
        return true;
    }

    @Override
    public Status postStatus(String text) throws Exception {
        TwitterStatus status = this.statusApi.postStatus(text);
        return new Status(status);
    }
    
    @Override
    public boolean canPostImageStatus() {
        return false;
    }

    @Override
    public Status postStatus(String text, String filePath) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public boolean canDeleteStatus() {
        return false;
    }

    @Override
    public boolean deleteStatus(String statusId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /* *****************************************
       Operaciones sobre la entidad Notificacion
       ****************************************** */
    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}