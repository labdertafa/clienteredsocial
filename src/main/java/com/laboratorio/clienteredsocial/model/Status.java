package com.laboratorio.clienteredsocial.model;

import com.laboratorio.blueskyapiinterface.model.BlueskyStatus;
import com.laboratorio.gabapiinterface.model.GabStatus;
import com.laboratorio.getrapiinterface.modelo.GettrStatus;
import com.laboratorio.mastodonapiinterface.model.MastodonStatus;
import com.laboratorio.mindsapiinterface.model.MindsStatus;
import com.laboratorio.parlerapiinterface.model.ParlerStatus;
import com.laboratorio.telegramapiinterface.model.TelegramStatus;
import com.laboratorio.threadsapiinterface.model.ThreadsStatus;
import com.laboratorio.truthsocialapiinterface.model.TruthsocialStatus;
import com.laboratorio.tumblr.model.TumblrStatusResponse;
import com.laboratorio.twitterapiinterface.model.TwitterStatus;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 12/10/2024
 * @updated 19/12/2024
 */

@Getter @Setter @AllArgsConstructor
public class Status {
    private String id;
    private String body;
    private String language;
    private String ownerId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    
    public Status(MastodonStatus status) {
        this.id = status.getId();
        this.body = status.getContent();
        this.language = status.getLanguage();
        this.ownerId = status.getAccount().getId();
        this.createdAt = ZonedDateTime.parse(status.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.updatedAt = ZonedDateTime.parse(status.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
    }
    
    public Status(BlueskyStatus status) {
        this.id = status.getCid();
        this.body = status.getRecord().getText();
        this.language = (status.getRecord().getLangs() != null) ? status.getRecord().getLangs()[0] : null;
        this.ownerId = status.getAuthor().getDid();
        this.createdAt = ZonedDateTime.parse(status.getRecord().getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME);
        this.updatedAt = ZonedDateTime.parse(status.getRecord().getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME);
    }
    
    public Status(TruthsocialStatus status) {
        this.id = status.getId();
        this.body = status.getContent();
        this.language = status.getLanguage();
        this.ownerId = status.getAccount().getId();
        this.createdAt = ZonedDateTime.parse(status.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.updatedAt = ZonedDateTime.parse(status.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
    }
    
    public Status(GettrStatus status) {
        this.id = status.get_id();
        this.body = status.getTxt();
        this.language = status.getTxt_lang();
        this.ownerId = status.getUid();
        this.createdAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(status.getCdate()), ZoneId.systemDefault());
        this.updatedAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(status.getUpdate()), ZoneId.systemDefault());
    }
    
    public Status(GabStatus status) {
        this.id = status.getId();
        this.body = status.getContent();
        this.language = status.getLanguage();
        this.ownerId = status.getAccount().getId();
        this.createdAt = ZonedDateTime.parse(status.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.updatedAt = ZonedDateTime.parse(status.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
    }
    
    public Status(MindsStatus status) {
        this.id = status.getGuid();
        this.body = status.getMessage();
        this.language = null;
        this.ownerId = status.getOwner_guid();
        this.createdAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(status.getTime_created())), ZoneId.systemDefault());
        this.updatedAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(status.getTime_updated())), ZoneId.systemDefault());
    }
    
    public Status(ParlerStatus status) {
        this.id = status.getId();
        this.body = status.getBody();
        this.language = status.getDetectedLanguage();
        this.ownerId = status.getUser().getUserId();
        this.createdAt = ZonedDateTime.parse(status.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME);
        this.updatedAt = ZonedDateTime.parse(status.getUpdatedAt(), DateTimeFormatter.ISO_DATE_TIME);
    }
    
    public Status(TelegramStatus status, String chatId) {
        this.id = Integer.toString(status.getMessage_id());
        this.body = status.getText();
        this.language = null;
        this.ownerId = chatId;
        this.createdAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(status.getDate()), ZoneId.systemDefault());
        this.updatedAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(status.getDate()), ZoneId.systemDefault());
    }
    
    public Status(ThreadsStatus status) {
        this.id = status.getId();
        this.body = status.getText();
        this.language = null;
        this.ownerId = status.getOwner().getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        ZonedDateTime creationDate = ZonedDateTime.parse(status.getTimestamp(), formatter);
        this.createdAt = creationDate;
        this.updatedAt = creationDate;
    }
    
    public Status(TwitterStatus status) {
        this.id = status.getData().getId();
        this.body = status.getData().getText();
        this.language = null;
        this.ownerId = null;
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }
    
    public Status(TumblrStatusResponse status) {
        this.id = status.getResponse().getId_string();
        this.body = status.getResponse().getDisplay_text();
        this.language = null;
        this.ownerId = null;
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Status{" + "id=" + id + ", body=" + body + ", language=" + language + ", ownerId=" + ownerId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}