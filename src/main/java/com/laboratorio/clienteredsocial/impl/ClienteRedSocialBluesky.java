package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.blueskyapiinterface.BlueskyAccountApi;
import com.laboratorio.blueskyapiinterface.BlueskyStatusApi;
import com.laboratorio.blueskyapiinterface.impl.BlueskyAccountApiImpl;
import com.laboratorio.blueskyapiinterface.impl.BlueskyStatusApiImpl;
import com.laboratorio.blueskyapiinterface.model.BlueskyAccount;
import com.laboratorio.blueskyapiinterface.model.BlueskyRelationship;
import com.laboratorio.blueskyapiinterface.model.BlueskyStatus;
import com.laboratorio.blueskyapiinterface.model.response.BlueskyRelationshipsResponse;
import com.laboratorio.clientapilibrary.utils.ImageMetadata;
import com.laboratorio.clientapilibrary.utils.PostUtils;
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
 * @version 1.2
 * @created 15/10/2024
 * @updated 17/10/2024
 */
public class ClienteRedSocialBluesky implements ClienteRedSocial {
    private final String accountId;
    private final String accessToken;
    private final BlueskyAccountApi accountApi;
    private final BlueskyStatusApi statusApi;

    public ClienteRedSocialBluesky(String accountId, String accessToken) {
        this.accountId = accountId;
        this.accessToken = accessToken;
        this.accountApi = new BlueskyAccountApiImpl(this.accessToken);
        this.statusApi = new BlueskyStatusApiImpl(this.accessToken);
    }

    @Override
    public Account getAccountById(String userId) throws Exception {
        BlueskyAccount account = this.accountApi.getAccountById(userId);
        
        return new Account(account.getDid(), ZonedDateTime.parse(account.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME), account.getHandle(), account.getDisplayName(), null, account.getFollowersCount(), account.getFollowsCount(), account.getPostsCount());
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        BlueskyRelationshipsResponse relationshipsResponse = this.accountApi.checkrelationships(this.accountId, List.of(userId));
        BlueskyRelationship relationship = relationshipsResponse.getRelationships().get(0);
        
        return new Relationship(userId, relationship.isFollowedBy(), relationship.isFollowing());
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        return this.accountApi.followAccount(this.accountId, userId);
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        return this.accountApi.unfollowAccount(this.accountId, userId);
    }
    
    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        List<BlueskyStatus> statuses = this.statusApi.getGlobalTimeline(quantity);
        
        return statuses.stream()
                .map(status -> new Status(status))
                .collect(Collectors.toList());
    }

    // Nota: en Bluesky, un Post es un Record y se busca usando su uri
    @Override
    public Status postStatus(String text) throws Exception {
        return new Status(this.statusApi.postStatus(this.accountId, text));
    }

    // Nota: en Bluesky, un Post es un Record y se busca usando su uri
    @Override
    public Status postStatus(String text, String filePath) throws Exception {
        ImageMetadata metadata = PostUtils.extractImageMetadata(filePath);
        return new Status(this.statusApi.postStatus(this.accountId, text, filePath, metadata.getMimeType()));
    }

    // Nota: en Bluesky, un Post es un Record y se busca usando su uri
    @Override
    public boolean deleteStatus(String uri) throws Exception {
        return this.statusApi.deleteStatus(this.accountId, uri);
    }
}