package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.parlerapiinterface.ParlerAccountApi;
import com.laboratorio.parlerapiinterface.ParlerStatusApi;
import com.laboratorio.parlerapiinterface.impl.ParlerAccountApiImpl;
import com.laboratorio.parlerapiinterface.impl.ParlerStatusApiImpl;
import com.laboratorio.parlerapiinterface.model.ParlerAccount;
import com.laboratorio.parlerapiinterface.model.ParlerStatus;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 12/10/2024
 * @updated 16/10/2024
 */
public class ClienteRedSocialParler implements ClienteRedSocial {
    private final String accessToken;

    public ClienteRedSocialParler(String accessToken) {
        this.accessToken = accessToken;
    }
    
    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        ParlerStatusApi statusApi = new ParlerStatusApiImpl(this.accessToken);
        List<ParlerStatus> statuses = statusApi.getGlobalTimeline(quantity);
        
        return statuses.stream()
                .map(s -> new Status(s.getId(), s.getBody(), s.getDetectedLanguage(), s.getUser().getUserId(), ZonedDateTime.parse(s.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME), ZonedDateTime.parse(s.getUpdatedAt(), DateTimeFormatter.ISO_DATE_TIME)))
                .collect(Collectors.toList());
    }
    
    @Override
    public Account getAccountById(String userId) throws Exception {
        ParlerAccountApi accountApi = new ParlerAccountApiImpl(accessToken);
        ParlerAccount account = accountApi.getAccountsById(List.of(userId)).get(0);
        
        return new Account(account.getUlid(), null, account.getUsername(), account.getName(), null, account.getFollowers(), account.getFollowing(), account.getPostCount());
    }
    
    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        ParlerAccountApi accountApi = new ParlerAccountApiImpl(accessToken);
        ParlerAccount account = accountApi.getAccountsById(List.of(userId)).get(0);
        
        return new Relationship(userId, account.getProfileEngagement().isFollowingYou(), account.getProfileEngagement().isFollowing());
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        ParlerAccountApi accountApi = new ParlerAccountApiImpl(accessToken);
        List<ParlerAccount> accounts = accountApi.getAccountsById(List.of(userId));
        return accountApi.followAccount(accounts.get(0).getUsername());
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        ParlerAccountApi accountApi = new ParlerAccountApiImpl(accessToken);
        List<ParlerAccount> accounts = accountApi.getAccountsById(List.of(userId));
        return accountApi.unfollowAccount(accounts.get(0).getUsername());
    }
}