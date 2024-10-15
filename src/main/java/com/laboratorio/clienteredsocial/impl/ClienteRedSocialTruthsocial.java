package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.truthsocialapiinterface.TruthsocialAccountApi;
import com.laboratorio.truthsocialapiinterface.TruthsocialStatusApi;
import com.laboratorio.truthsocialapiinterface.impl.TruthsocialAccountApiImpl;
import com.laboratorio.truthsocialapiinterface.impl.TruthsocialStatusApiImpl;
import com.laboratorio.truthsocialapiinterface.model.TruthsocialAccount;
import com.laboratorio.truthsocialapiinterface.model.TruthsocialRelationship;
import com.laboratorio.truthsocialapiinterface.model.TruthsocialStatus;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 13/10/2024
 * @updated 13/10/2024
 */
public class ClienteRedSocialTruthsocial implements ClienteRedSocial {
    private final String accessToken;

    public ClienteRedSocialTruthsocial(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        TruthsocialStatusApi statusApi = new TruthsocialStatusApiImpl(this.accessToken);
        List<TruthsocialStatus> statuses = statusApi.getGlobalTimeline(quantity);
        
        return statuses.stream()
                .map(s -> new Status(s.getId(), s.getContent(), s.getLanguage(), s.getAccount().getId(), ZonedDateTime.parse(s.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME), null))
                .collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(String userId) throws Exception {
        TruthsocialAccountApi accountApi = new TruthsocialAccountApiImpl(this.accessToken);
        TruthsocialAccount account = accountApi.getAccountById(userId);
        
        return new Account(account.getId(), ZonedDateTime.parse(account.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME), account.getUsername(), account.getDisplay_name(), null, account.getFollowers_count(), account.getFollowing_count(), account.getStatuses_count());
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        TruthsocialAccountApi accountApi = new TruthsocialAccountApiImpl(this.accessToken);
        TruthsocialRelationship relationship = accountApi.checkrelationships(List.of(userId)).get(0);
        
        return new Relationship(userId, relationship.isFollowed_by(), relationship.isFollowing());
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        TruthsocialAccountApi accountApi = new TruthsocialAccountApiImpl(this.accessToken);
        return accountApi.followAccount(userId);
    }
}