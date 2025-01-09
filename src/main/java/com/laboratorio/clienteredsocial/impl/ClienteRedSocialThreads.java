package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.exception.ClienteRedSocialException;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import com.laboratorio.threadsapiinterface.ThreadsSessionApi;
import com.laboratorio.threadsapiinterface.ThreadsStatusApi;
import com.laboratorio.threadsapiinterface.impl.ThreadsSessionApiImpl;
import com.laboratorio.threadsapiinterface.impl.ThreadsStatusApiImpl;
import com.laboratorio.threadsapiinterface.model.ThreadsSessionResponse;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 17/10/2024
 * @updated 09/01/2025
 */
public class ClienteRedSocialThreads implements ClienteRedSocial {
    private static final Logger log = LogManager.getLogger(ClienteRedSocialThreads.class);
    private final String accessToken;
    private final ThreadsStatusApi statusApi;

    public ClienteRedSocialThreads(String accessToken) {
        this.accessToken = accessToken;
        this.statusApi = new ThreadsStatusApiImpl(this.accessToken);
    }
    
    @Override
    public Session refreshSession(String email, String refreshToken) throws Exception {
        ThreadsSessionApi sessionApi = new ThreadsSessionApiImpl(this.accessToken);
        ThreadsSessionResponse session = sessionApi.refreshSession();
        return new Session(session);
    }

    @Override
    public Account getAccountById(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
    }
    
    @Override
    public Account getAccountByUsername(String username) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
    }
    
    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
    }
    
    @Override
    public List<Account> getFollowers(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
    }
    
    @Override
    public List<String> getFollowingsIds(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
    }

    @Override
    public List<Account> getFollowings(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
    }

    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
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
    public Status postStatus(String text, String filePath) throws Exception {
        // Si la publicación hace mención de la página del laboratorio de rafa
        if (text.contains("laboratoriorafa.mooo.com")) {
            log.info("En Instagram, no se puede mencionar la página del Laboratorio. Se descarta la publicación.");
            return null;
        }
        
        return new Status(this.statusApi.postStatus(text, filePath));
    }

    @Override
    public boolean deleteStatus(String statusId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
    }

    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialThreads.class.getName(), "Error, función no implementada para la red social Threads");
    }
}