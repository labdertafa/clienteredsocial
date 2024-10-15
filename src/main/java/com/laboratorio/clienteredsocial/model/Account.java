package com.laboratorio.clienteredsocial.model;

import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 12/10/2024
 * @updated 12/10/2024
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