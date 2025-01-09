package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.exception.ClienteRedSocialException;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import com.laboratorio.linkedinapiinterface.LinkedInStatusApi;
import com.laboratorio.linkedinapiinterface.impl.LinkedInStatusApiImpl;
import com.laboratorio.linkedinapiinterface.model.response.LinkedInPostMessageResponse;
import java.time.ZonedDateTime;
import java.util.List;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 17/10/2024
 * @updated 09/01/2025
 */
public class ClienteRedSocialLinkedin implements ClienteRedSocial {
    private final String accessToken;
    private final String userId;
    private final LinkedInStatusApi statusApi;

    public ClienteRedSocialLinkedin(String accessToken, String userId) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.statusApi = new LinkedInStatusApiImpl(this.accessToken, this.userId);
    }
    
    @Override
    public Session refreshSession(String email, String refreshToken) throws Exception {
        return null;
    }
    
    @Override
    public Account getAccountById(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialLinkedin.class.getName(), "Error, función no implementada para la red social LinkedIn");
    }
    
    @Override
    public Account getAccountByUsername(String username) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialLinkedin.class.getName(), "Error, función no implementada para la red social LinkedIn");
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialLinkedin.class.getName(), "Error, función no implementada para la red social LinkedIn");
    }
    
    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialLinkedin.class.getName(), "Error, función no implementada para la red social LinkedIn");
    }
    
    @Override
    public List<Account> getFollowers(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialLinkedin.class.getName(), "Error, función no implementada para la red social LinkedIn");
    }
    
    @Override
    public List<String> getFollowingsIds(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialLinkedin.class.getName(), "Error, función no implementada para la red social LinkedIn");
    }

    @Override
    public List<Account> getFollowings(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialLinkedin.class.getName(), "Error, función no implementada para la red social LinkedIn");
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialLinkedin.class.getName(), "Error, función no implementada para la red social LinkedIn");
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialLinkedin.class.getName(), "Error, función no implementada para la red social LinkedIn");
    }

    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialLinkedin.class.getName(), "Error, función no implementada para la red social LinkedIn");
    }

    @Override
    public Status postStatus(String text) throws Exception {
        LinkedInPostMessageResponse response = this.statusApi.postStatus(text);
        return new Status(response.getId(), text, null, this.userId, ZonedDateTime.now(), ZonedDateTime.now());
    }

    @Override
    public Status postStatus(String text, String filePath) throws Exception {
        LinkedInPostMessageResponse response = this.statusApi.postStatus(text, filePath);
        return new Status(response.getId(), text, null, this.userId, ZonedDateTime.now(), ZonedDateTime.now());
    }

    @Override
    public boolean deleteStatus(String statusId) throws Exception {
        return this.statusApi.deleteStatus(statusId);
    }

    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialLinkedin.class.getName(), "Error, función no implementada para la red social LinkedIn");
    }
}