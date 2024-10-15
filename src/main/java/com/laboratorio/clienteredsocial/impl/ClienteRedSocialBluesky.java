package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.blueskyapiinterface.BlueskyAccountApi;
import com.laboratorio.blueskyapiinterface.BlueskyStatusApi;
import com.laboratorio.blueskyapiinterface.impl.BlueskyAccountApiImpl;
import com.laboratorio.blueskyapiinterface.impl.BlueskyStatusApiImpl;
import com.laboratorio.blueskyapiinterface.model.BlueskyAccount;
import com.laboratorio.blueskyapiinterface.model.BlueskyRelationship;
import com.laboratorio.blueskyapiinterface.model.BlueskyStatus;
import com.laboratorio.blueskyapiinterface.model.response.BlueskyRelationshipsResponse;
import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 15/10/2024
 * @updated 15/10/2024
 */
public class ClienteRedSocialBluesky implements ClienteRedSocial {
    private final String accountId;
    private final String accessToken;

    public ClienteRedSocialBluesky(String accountId, String accessToken) {
        this.accountId = accountId;
        this.accessToken = accessToken;
    }

    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        BlueskyStatusApi statusApi = new BlueskyStatusApiImpl(this.accessToken);
        List<BlueskyStatus> statuses = statusApi.getGlobalTimeline(quantity);
        
        return statuses.stream()
                .map(s -> new Status(s.getCid(), s.getRecord().getText(), (s.getRecord().getLangs() != null) ? s.getRecord().getLangs()[0] : null, s.getAuthor().getDid(), ZonedDateTime.parse(s.getRecord().getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME), ZonedDateTime.parse(s.getRecord().getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME)))
                .collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(String userId) throws Exception {
        BlueskyAccountApi accountApi = new BlueskyAccountApiImpl(this.accessToken);
        BlueskyAccount account = accountApi.getAccountById(userId);
        
        return new Account(account.getDid(), ZonedDateTime.parse(account.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME), account.getHandle(), account.getDisplayName(), null, account.getFollowersCount(), account.getFollowsCount(), account.getPostsCount());
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        BlueskyAccountApi accountApi = new BlueskyAccountApiImpl(this.accessToken);
        BlueskyRelationshipsResponse relationshipsResponse = accountApi.checkrelationships(this.accountId, List.of(userId));
        BlueskyRelationship relationship = relationshipsResponse.getRelationships().get(0);
        
        return new Relationship(userId, relationship.isFollowedBy(), relationship.isFollowing());
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        BlueskyAccountApi accountApi = new BlueskyAccountApiImpl(this.accessToken);
        return accountApi.followAccount(this.accountId, userId);
    }
}