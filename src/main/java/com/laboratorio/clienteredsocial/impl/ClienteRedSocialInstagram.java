package com.laboratorio.clienteredsocial.impl;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.laboratorio.clienteredsocial.ClienteRedSocial;
import com.laboratorio.clienteredsocial.exception.ClienteRedSocialException;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.Status;
import com.laboratorio.clienteredsocial.response.NotificationListResponse;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.3
 * @created 17/10/2024
 * @updated 23/02/2025
 */
public class ClienteRedSocialInstagram implements ClienteRedSocial {
    private static final Logger log = LogManager.getLogger(ClienteRedSocialInstagram.class);
    private final String username;
    private final String password;

    public ClienteRedSocialInstagram(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    private void registrarError(String mensaje, Exception e) {
        log.error(mensaje);
        log.error("Error: " + e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: " + e.getCause().getMessage());
        }
    }
    
    @Override
    public Session createSession(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Session refreshSession(String email, String refreshToken) throws Exception {
        return null;
    }

    @Override
    public Account getAccountById(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }
    
    @Override
    public Account getAccountByUsername(String username) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }

    @Override
    public Relationship checkrelationship(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }
    
    @Override
    public List<String> getFollowersIds(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }
    
    @Override
    public List<Account> getFollowers(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }
    
    @Override
    public List<String> getFollowingsIds(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }

    @Override
    public List<Account> getFollowings(String userId, int limit) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }

    @Override
    public boolean followAccount(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }

    @Override
    public boolean unfollowAccount(String userId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }

    @Override
    public List<Status> getGlobalTimeline(int quantity) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }
    
    @Override
    public boolean canPostTextStatus() {
        return false;
    }

    @Override
    public Status postStatus(String text) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }
    
    @Override
    public boolean canPostImageStatus() {
        return true;
    }

    @Override
    public Status postStatus(String text, String filePath) throws Exception {
        // Si la publicación no tiene imagen, no se puede publicar en Instagram
        if (filePath ==  null) {
            log.info("En Instagram, no se pueden hacer publicaciones que no tengan una imagen.");
            return null;
        }
        
        // Si la publicación hace mención de la página del laboratorio de rafa
        if (text.contains("laboratoriorafa.mooo.com")) {
            log.info("En Instagram, no se puede mencionar la página del Laboratorio. Se descarta la publicación.");
            return null;
        }
        
        IGClient client;
        List<String> lista = new ArrayList<>();
        
        try {
            client = IGClient.builder()
                .username(this.username)
                .password(this.password)
                .login();
        } catch (IGLoginException e) {
            this.registrarError("No se ha logrado establecer la conexión con Instagram para el usuario " + this.username, e);
            return null;
        }
        
        String textoAPublicar = text.replaceAll("\\r\\n", " ");
        
        try {
            client.actions()
                    .timeline()
                    .uploadPhoto(new File(filePath), textoAPublicar)
                    .thenAccept(response -> {
                        lista.add(response.getStatus());
                        log.info("Se ha publicado correctamente en Instagram para el usuario " + this.username);
                    })
                    .join();
        } catch (Exception e) {
            this.registrarError("Se ha producido un error mientras se publicaba en Instagram con el usuario " + this.username, e);
            return null;
        }
        
        if (lista.isEmpty()) {
            log.info("No hubo respuesta de Instagram cuando se intentaba publicar con el usuario " + this.username);
            return null;
        }
        
        return new Status("ID", textoAPublicar, null, this.username, ZonedDateTime.now(), ZonedDateTime.now());
    }
    
    @Override
    public boolean canDeleteStatus() {
        return false;
    }

    @Override
    public boolean deleteStatus(String statusId) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }

    @Override
    public NotificationListResponse getFollowNotifications(String posicionInicial) throws Exception {
        throw new ClienteRedSocialException(ClienteRedSocialInstagram.class.getName(), "Error, función no implementada para la red social Instagram");
    }
}