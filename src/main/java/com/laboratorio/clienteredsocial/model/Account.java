package com.laboratorio.clienteredsocial.model;

import com.laboratorio.blueskyapiinterface.model.BlueskyAccount;
import com.laboratorio.gabapiinterface.model.GabAccount;
import com.laboratorio.getrapiinterface.modelo.GettrAccount;
import com.laboratorio.mastodonapiinterface.model.MastodonAccount;
import com.laboratorio.mindsapiinterface.model.MindsAccount;
import com.laboratorio.parlerapiinterface.model.ParlerAccount;
import com.laboratorio.truthsocialapiinterface.model.TruthsocialAccount;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 12/10/2024
 * @updated 23/10/2024
 */

@Getter @Setter @AllArgsConstructor
public class Account {
    private String id;
    private ZonedDateTime createdAt;
    private String username;
    private String name;
    private String language;
    private int followersCount;
    private int followingsCount;
    private Integer postCount;
    
    public Account(MastodonAccount account) {
        this.id = account.getId();
        this.createdAt = ZonedDateTime.parse(account.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.username = account.getUsername();
        this.name = account.getDisplay_name();
        this.language = null;
        this.followersCount = account.getFollowers_count();
        this.followingsCount = account.getFollowing_count();
        this.postCount = account.getStatuses_count();
    }
    
    public Account(BlueskyAccount account) {
        this.id =  account.getDid();
        this.createdAt = ZonedDateTime.parse(account.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME);
        this.username = account.getHandle();
        this.name = account.getDisplayName();
        this.language = null;
        this.followersCount = account.getFollowersCount();
        this.followingsCount = account.getFollowsCount();
        this.postCount = account.getPostsCount();
    }
    
    public Account(TruthsocialAccount account) {
        this.id = account.getId();
        this.createdAt = ZonedDateTime.parse(account.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.username = account.getUsername();
        this.name = account.getDisplay_name();
        this.language = null;
        this.followersCount = account.getFollowers_count();
        this.followingsCount = account.getFollowing_count();
        this.postCount = account.getStatuses_count();
    }
    
    public Account(GettrAccount account) {
        this.id =  account.get_id();
        this.createdAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(account.getCdate()), ZoneId.systemDefault());
        this.username = account.getUsername();
        this.name = account.getNickname();
        this.language = account.getLang();
        this.followersCount = account.getFlg();
        this.followingsCount = account.getFlw();
        this.postCount = null;
    }
    
    public Account(GabAccount account) {
        this.id = account.getId();
        this.createdAt = ZonedDateTime.parse(account.getCreated_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.username = account.getUsername();
        this.name = account.getDisplay_name();
        this.language = null;
        this.followersCount = account.getFollowers_count();
        this.followingsCount = account.getFollowing_count();
        this.postCount = account.getStatuses_count();
    }
    
    public Account(MindsAccount account) {
        this.id = account.getGuid();
        this.createdAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(account.getTime_created())), ZoneId.systemDefault());
        this.username = account.getUsername();
        this.name = account.getName();
        this.language = account.getLanguage();
        this.followersCount = account.getSubscribers_count();
        this.followingsCount = account.getSubscriptions_count();
        this.postCount = null;
    }
    
    public Account(ParlerAccount account) {
        this.id = account.getUlid();
        this.createdAt = null;
        this.username = account.getUsername();
        this.name = account.getName();
        this.language = null;
        this.followersCount = account.getFollowers();
        this.followingsCount = account.getFollowing();
        this.postCount = account.getPostCount();
    }
    
    public boolean isSeguidorPotencial() {
        if (postCount != null) {
            if (postCount == 0) {
                return false;
            }
        }
        
        if (this.followingsCount < 2) {
            return false;
        }

        return 2 * this.followingsCount >= this.followersCount;
    }
    
    public boolean isFuenteSeguidores(int umbral) {
        return this.followersCount >= umbral;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.username, other.username);
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", createdAt=" + createdAt + ", username=" + username + ", language=" + language + ", followersCount=" + followersCount + ", followingsCount=" + followingsCount + '}';
    }
}