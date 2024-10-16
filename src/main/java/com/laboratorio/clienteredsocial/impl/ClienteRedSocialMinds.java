package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.mindsapiinterface.MindsAccountApi;
import com.laboratorio.mindsapiinterface.MindsStatusApi;
import com.laboratorio.mindsapiinterface.impl.MindsAccountApiImpl;
import com.laboratorio.mindsapiinterface.impl.MindsStatusApiImpl;
import com.laboratorio.mindsapiinterface.model.MindsAccount;
import com.laboratorio.mindsapiinterface.model.MindsStatus;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 12/10/2024
 * @updated 16/10/2024
 */
public class ClienteRedSocialMinds implements ClienteRedSocial {
    private String getUserIdFromURN(String urn) throws Exception {
        String[] partes = urn.split(":");
        if (partes.length != 3) {
            throw new Exception("Formato de URN no válido");
        }
        
        return partes[2];
    }
    
    private String getURNFromUserId(String userId) {
        return "urn:user:" + userId;
    }
    
    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        MindsStatusApi statusApi = new MindsStatusApiImpl();
        List<MindsStatus> statuses = statusApi.getGlobalTimeline(quantity);
        
        return statuses.stream()
                .map(s -> new Status(s.getGuid(), s.getMessage(), null, s.getOwner_guid(), ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(s.getTime_created())), ZoneId.systemDefault()), ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(s.getTime_updated())), ZoneId.systemDefault())))
                .collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(String userId) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        String userURN = this.getURNFromUserId(userId);
        MindsAccount account = accountApi.getAccountsById(List.of(userURN)).get(0);
        
        return new Account(account.getUrn(), ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(account.getTime_created())), ZoneId.systemDefault()), account.getUsername(), account.getName(), account.getLanguage(), account.getSubscribers_count(), account.getSubscriptions_count(), null);
    }

    // Minds usa varios identificadores. Acá usamos el identificador URN.
    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        String userURN = this.getURNFromUserId(userId);
        MindsAccount account = accountApi.getAccountsById(List.of(userURN)).get(0);
        
        return new Relationship(userId, account.isSubscriber(), account.isSubscribed());
    }

    // Minds usa varios identificadores. Acá usamos el identificador URN: urn:user:userId.
    @Override
    public boolean followAccount(String userId) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        return accountApi.followAccount(userId);
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        MindsAccountApi accountApi = new MindsAccountApiImpl();
        return accountApi.unfollowAccount(userId);
    }
}