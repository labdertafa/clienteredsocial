package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.exception.ClienteRedSocialException;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.SessionRequest;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import com.laboratorio.tumblr.TumblrSessionApi;
import com.laboratorio.tumblr.TumblrStatusApi;
import com.laboratorio.tumblr.impl.TumblrSessionApiImpl;
import com.laboratorio.tumblr.impl.TumblrStatusApiImpl;
import com.laboratorio.tumblr.model.TumblrSessionResponse;
import java.util.List;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 07/06/2025
 * @updated 08/06/2025
 */
public class ClienteRedSocialTumblr implements ClienteRedSocial {
    private final String accessToken;
    private final String appClientId;
    private final String appClientSecret;
    private final TumblrStatusApi statusApi;

    public ClienteRedSocialTumblr(String accessToken, String blog, String appClientId, String appClientSecret) {
        this.accessToken = accessToken;
        this.appClientId = appClientId;
        this.appClientSecret = appClientSecret;
        this.statusApi = new TumblrStatusApiImpl(this.accessToken, blog);
    }
    
    @Override
    public Session createSession(SessionRequest request) {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
    }

    @Override
    public Session refreshSession(String email, String refreshToken) throws Exception {
        TumblrSessionApi sessionApi = new TumblrSessionApiImpl(this.accessToken);
        TumblrSessionResponse session = sessionApi.refreshSession(this.appClientId, this.appClientSecret, refreshToken);
        return new Session(session);
    }

    @Override
    public Account getAccountById(String userId) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
    }

    @Override
    public Account getAccountByUsername(String username) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
    }

    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
    }

    @Override
    public List<Account> getFollowers(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
    }

    @Override
    public List<String> getFollowingsIds(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
    }

    @Override
    public List<Account> getFollowings(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
    }

    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
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
        return this.statusApi.deleteStatus(statusId);
    }

    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        throw new ClienteRedSocialException("Error, función no implementada para la red social Tumblr");
    }
}