package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.getrapiinterface.GettrAccountApi;
import com.laboratorio.getrapiinterface.GettrStatusApi;
import com.laboratorio.getrapiinterface.impl.GettrAccountApiImpl;
import com.laboratorio.getrapiinterface.impl.GettrStatusApiImpl;
import com.laboratorio.getrapiinterface.modelo.GettrAccount;
import com.laboratorio.getrapiinterface.modelo.GettrRelationship;
import com.laboratorio.getrapiinterface.modelo.GettrStatus;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 13/10/2024
 * @updated 16/10/2024
 */
public class ClienteRedSocialGettr implements ClienteRedSocial {
    private final String accountId;
    private final String accessToken;

    public ClienteRedSocialGettr(String accountId, String accessToken) {
        this.accountId = accountId;
        this.accessToken = accessToken;
    }

    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        GettrStatusApi statusApi = new GettrStatusApiImpl(this.accountId, this.accessToken);
        List<GettrStatus> statuses = statusApi.getGlobalTimeline(quantity);
        
        return statuses.stream()
                .map(s -> new Status(s.get_id(), s.getTxt(), s.getTxt_lang(), s.getUid(), ZonedDateTime.ofInstant(Instant.ofEpochMilli(s.getCdate()), ZoneId.systemDefault()), ZonedDateTime.ofInstant(Instant.ofEpochMilli(s.getUpdate()), ZoneId.systemDefault())))
                .collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(String userId) throws Exception {
        GettrAccountApi accountApi = new GettrAccountApiImpl(this.accountId, this.accessToken);
        GettrAccount account = accountApi.getAccountById(userId);
        
        return new Account(account.get_id(), ZonedDateTime.ofInstant(Instant.ofEpochMilli(account.getCdate()), ZoneId.systemDefault()), account.getUsername(), account.getNickname(), account.getLang(), account.getFlg(), account.getFlw(), null);
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        GettrAccountApi accountApi = new GettrAccountApiImpl(this.accountId, this.accessToken);
        GettrRelationship relationship = accountApi.checkrelationship(userId);
        
        return new Relationship(userId, relationship.isFollowedBy(), relationship.isFollowing());
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        GettrAccountApi accountApi = new GettrAccountApiImpl(this.accountId, this.accessToken);
        return accountApi.followAccount(userId);
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        GettrAccountApi accountApi = new GettrAccountApiImpl(this.accountId, this.accessToken);
        return accountApi.unfollowAccount(userId);
    }
}