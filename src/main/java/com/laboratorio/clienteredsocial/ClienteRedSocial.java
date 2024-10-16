package com.laboratorio.clienteredsocial;

import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Status;
import java.util.List;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 12/10/2024
 * @updated 16/10/2024
 */
public interface ClienteRedSocial {
    List<Status> getGlobalTimeline(int quantity) throws Exception;
    
    Account getAccountById(String userId) throws Exception;
    
    Relationship checkrelationship(String userId) throws Exception;
    
    boolean followAccount(String userId) throws Exception;
    
    boolean unfollowAccount(String userId) throws Exception;
}