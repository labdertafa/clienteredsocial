package com.laboratorio.clienteredsocial.impl;

import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.threadsapiinterface.ThreadsStatusApi;
import com.laboratorio.threadsapiinterface.impl.ThreadsStatusApiImpl;
import java.util.List;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 17/10/2024
 * @updated 17/10/2024
 */
public class ClienteRedSocialThreads implements ClienteRedSocial {
    private final String accessToken;
    private final ThreadsStatusApi statusApi;

    public ClienteRedSocialThreads(String accessToken) {
        this.accessToken = accessToken;
        this.statusApi = new ThreadsStatusApiImpl(this.accessToken);
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
        return new Status(this.statusApi.postStatus(text));
    }

    @Override
    public Status postStatus(String text, String filePath) throws Exception {
        return new Status(this.statusApi.postStatus(text, filePath));
    }

    @Override
    public boolean deleteStatus(String statusId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}