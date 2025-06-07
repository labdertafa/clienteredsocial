package com.laboratorio.clienteredsocial.model;

import com.laboratorio.blueskyapiinterface.model.BlueskySession;
import com.laboratorio.mindsapiinterface.model.MindsSession;
import com.laboratorio.parlerapiinterface.model.ParlerSession;
import com.laboratorio.threadsapiinterface.model.ThreadsSessionResponse;
import com.laboratorio.tumblr.model.TumblrSessionResponse;
import com.laboratorio.twitterapiinterface.model.TwitterSession;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 24/10/2024
 * @updated 07/06/2025
 */

@Getter @Setter @AllArgsConstructor
public class Session {
    private String accessToken;
    private ZonedDateTime validoHasta;
    private String refreshToken;
    private ZonedDateTime ultimoRefresco;
    
    public Session(BlueskySession session) {
        // Se calcula la fecha de expiración
        ZonedDateTime fechaExpiracionTemp = ZonedDateTime.now().plusMinutes(115L);
        
        this.accessToken = session.getAccessJwt();
        this.validoHasta = fechaExpiracionTemp;
        this.refreshToken = session.getRefreshJwt();
        this.ultimoRefresco = ZonedDateTime.now();
    }
    
    public Session(ThreadsSessionResponse session) {
        // Se calcula la fecha de expiración
        ZonedDateTime fechaExpiracionTemp = ZonedDateTime.now().plusSeconds(session.getExpires_in());
        ZonedDateTime fechaExpiracion = fechaExpiracionTemp.minusDays(3);
            
        this.accessToken = session.getAccess_token();
        this.validoHasta = fechaExpiracion;
        this.refreshToken = null;
        this.ultimoRefresco = ZonedDateTime.now();
    }
    
    public Session(MindsSession session) {
        this.accessToken = session.getXToken();
        this.validoHasta = null;
        this.refreshToken = null;
        this.ultimoRefresco = ZonedDateTime.now();
    }
    
    public Session(ParlerSession session) {
        // Se calcula la fecha de expiración
        ZonedDateTime fechaExpiracionTemp = ZonedDateTime.now().plusSeconds(session.getExpires_in());
        ZonedDateTime fechaExpiracion = fechaExpiracionTemp.minusDays(1L);
            
        this.accessToken = session.getAccess_token();
        this.validoHasta = fechaExpiracion;
        this.refreshToken = session.getRefresh_token();
        this.ultimoRefresco = ZonedDateTime.now();
    }
    
    public Session(TwitterSession session) {
        // Se calcula la fecha de expiración
        ZonedDateTime fechaExpiracionTemp = ZonedDateTime.now().plusSeconds(session.getExpires_in());
        ZonedDateTime fechaExpiracion = fechaExpiracionTemp.minusMinutes(5L);
        
        this.accessToken = session.getAccess_token();
        this.validoHasta = fechaExpiracion;
        this.refreshToken = session.getRefresh_token();
        this.ultimoRefresco = ZonedDateTime.now();
    }
    
    public Session(TumblrSessionResponse session) {
        // Se calcula la fecha de expiración
        ZonedDateTime fechaExpiracionTemp = ZonedDateTime.now().plusSeconds(session.getExpires_in());
        ZonedDateTime fechaExpiracion = fechaExpiracionTemp.minusMinutes(5L);
        
        this.accessToken = session.getAccess_token();
        this.validoHasta = fechaExpiracion;
        this.refreshToken = session.getRefresh_token();
        this.ultimoRefresco = ZonedDateTime.now();
    }
}