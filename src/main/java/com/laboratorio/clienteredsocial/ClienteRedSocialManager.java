package com.laboratorio.clienteredsocial;

import com.laboratorio.clienteredsocial.impl.ClienteRedSocialBluesky;
import com.laboratorio.clienteredsocial.impl.ClienteRedSocialGab;
import com.laboratorio.clienteredsocial.impl.ClienteRedSocialGettr;
import com.laboratorio.clienteredsocial.impl.ClienteRedSocialInstagram;
import com.laboratorio.clienteredsocial.impl.ClienteRedSocialLinkedin;
import com.laboratorio.clienteredsocial.impl.ClienteRedSocialMastodon;
import com.laboratorio.clienteredsocial.impl.ClienteRedSocialParler;
import com.laboratorio.clienteredsocial.impl.ClienteRedSocialMinds;
import com.laboratorio.clienteredsocial.impl.ClienteRedSocialTelegram;
import com.laboratorio.clienteredsocial.impl.ClienteRedSocialThreads;
import com.laboratorio.clienteredsocial.impl.ClienteRedSocialTruthsocial;
import com.laboratorio.clienteredsocial.model.NombreRedSocial;
import static com.laboratorio.clienteredsocial.model.NombreRedSocial.MINDS;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 12/10/2024
 * @updated 17/10/2024
 */
public class ClienteRedSocialManager {
    private ClienteRedSocialManager() {
    }
    
    public static ClienteRedSocial getRedSocialInstance(NombreRedSocial redSocial, String userId, String accessToken, String urlBase, String username, String password, String chatId) {
        switch (redSocial) {
            case MASTODON -> {
                return new ClienteRedSocialMastodon(urlBase, accessToken);
            }
            case BLUESKY -> {
                return new ClienteRedSocialBluesky(userId, accessToken);
            }
            case INSTAGRAM -> {
                return new ClienteRedSocialInstagram(username, password);
            }
            case TELEGRAM -> {
                return new ClienteRedSocialTelegram(accessToken, chatId);
            }
            case LINKEDIN -> {
                return new ClienteRedSocialLinkedin(accessToken, userId);
            }
            case TRUTHSOCIAL -> {
                return new ClienteRedSocialTruthsocial(accessToken);
            }
            case THREADS -> {
                return new ClienteRedSocialThreads(accessToken);
            }
            case GETTR -> {
                return new ClienteRedSocialGettr(userId, accessToken);
            }
            case GAB -> {
                return new ClienteRedSocialGab(accessToken);
            }
            case MINDS -> {
                return new ClienteRedSocialMinds();
            }
            case PARLER -> {
                return new ClienteRedSocialParler(accessToken);
            }
            default -> {
                return null;
            }
        }
    }
}