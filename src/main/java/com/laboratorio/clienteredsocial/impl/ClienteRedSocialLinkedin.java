package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.linkedinapiinterface.LinkedInStatusApi;
import com.laboratorio.linkedinapiinterface.impl.LinkedInStatusApiImpl;
import com.laboratorio.linkedinapiinterface.model.response.LinkedInPostMessageResponse;
import java.time.ZonedDateTime;
import java.util.List;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 17/10/2024
 * @updated 17/10/2024
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
    public Account getAccountById(String userId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
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

    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
}