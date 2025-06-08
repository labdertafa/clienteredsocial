package com.laboratorio.clienteredsocial;

import com.laboratorio.clienteredsocial.impl.ClienteRedSocialInstagram;
import com.laboratorio.clienteredsocial.model.Account;
import com.laboratorio.clienteredsocial.model.NombreRedSocial;
import com.laboratorio.clienteredsocial.model.Relationship;
import com.laboratorio.clienteredsocial.model.Session;
import com.laboratorio.clienteredsocial.model.SessionRequest;
import com.laboratorio.clienteredsocial.model.Status;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 12/10/2024
 * @updated 08/06/2025
 */
public class ClienteRedSocialTest {
    protected static final Logger log = LogManager.getLogger(ClienteRedSocialTest.class);

    private void verificarPosiblesSeguidores(NombreRedSocial nombreRedSocial, String accountId, String accessToken, String urlBase,
            String username, String password, String appClientId, String appClientSecret) throws Exception {
        int quantity = 50;
        Set<Account> accounts = new HashSet<>();
        ClienteRedSocial cliente = ClienteRedSocialManager.getRedSocialInstance(nombreRedSocial, accountId, accessToken, urlBase,
                username, password, appClientId, appClientSecret);

        List<Status> statuses = cliente.getGlobalTimeline(quantity);

        assertEquals(quantity, statuses.size());

        for (Status status : statuses) {
            Account account = cliente.getAccountById(status.getOwnerId());
            if (!accounts.contains(account)) {
                if (account.isSeguidorPotencial()) {
                    log.info("He encontrado un seguidor potencial: " + account.toString());
                    Relationship relationship = cliente.checkrelationship(status.getOwnerId());
                    if (!relationship.isFollowing()) {
                        log.info("Aún no sigo al seguidor potencial. Voy a seguirlo");
                        accounts.add(account);
                    }
                }
            } else {
                log.info("Se descarta el análisis de un usuario ya analizado!");
            }
        }

        log.info("Seguidores potenciales encontrados: " + accounts.size());
        assertTrue(accounts.size() >= 0);
    }
    
    /* @Test
    public void verificarPosiblesSeguidoresMastodon() throws Exception {
        String accountId = "";
        String accessToken = "2GevJ32dJQdHzcqgTEYTI01BVrkmjnO9K512yp59pek";
        String urlBase = "https://mastodon.social";
        String username = "";
        String password = "";
        this.verificarPosiblesSeguidores(NombreRedSocial.MASTODON, accountId, accessToken, urlBase, username, password);
    } */
    
    /* @Test
    public void verificarPosiblesSeguidoresBluesky() throws Exception {
        String accountId = "did:plc:vli2z522aj2bancr24qugm7n";
        String accessToken = "eyJ0eXAiOiJhdCtqd3QiLCJhbGciOiJFUzI1NksifQ.eyJzY29wZSI6ImNvbS5hdHByb3RvLmFjY2VzcyIsInN1YiI6ImRpZDpwbGM6dmxpMno1MjJhajJiYW5jcjI0cXVnbTduIiwiaWF0IjoxNzI4OTc1NjE0LCJleHAiOjE3Mjg5ODI4MTQsImF1ZCI6ImRpZDp3ZWI6cnVzc3VsYS51cy13ZXN0Lmhvc3QuYnNreS5uZXR3b3JrIn0.jIEh4XbVVh182rsPppdwaBOqfMaernm-vvkAo9xuk2cOy6r6K_Mtm3WAlf-1bXpLJ722wCq0xzEQm2rMPKPGsQ";
        String urlBase = "";
        String username = "";
        String password = "";
        this.verificarPosiblesSeguidores(NombreRedSocial.BLUESKY, accountId, accessToken, urlBase, username, password);
    } */
    
    /* @Test
    public void verificarPosiblesSeguidoresTruthsocial() throws Exception {
        String accountId = "";
        String accessToken = "HalY4TqDoTUnLB32fMaOTuzHOLzSLEXfVJcawOpROoA";
        String urlBase = "";
        String username = "";
        String password = "";
        this.verificarPosiblesSeguidores(NombreRedSocial.TRUTHSOCIAL, accountId, accessToken, urlBase, username, password);
    } */
    
    /* @Test
    public void verificarPosiblesSeguidoresGettr() throws Exception {
        String accountId = "labrafa";
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJsYWJyYWZhIiwidXNlcm5hbWUiOiJsYWJyYWZhIiwibGV2ZWwiOjAsImd2ZXIiOiIyRTY1OTE4RiIsImN2ZXIiOiJTUjhQMkQiLCJpYXQiOjE3MjU0NjI3ODIsImV4cCI6MjA0MDgyMjc4Mn0.7C_WwyYRFsC30xwQ5i6st988EqaTm9UmpHPC4_YNres";
        String urlBase = "";
        String username = "";
        String password = "";
        this.verificarPosiblesSeguidores(NombreRedSocial.GETTR, accountId, accessToken, urlBase, username, password);
    } */
    
    /* @Test
    public void verificarPosiblesSeguidoresGab() throws Exception {
        String accountId = "";
        String accessToken = "-eE3O3xex0sDW4ywgH3_ohvpXmpFmkWDoMAPciWCzBs";
        String urlBase = "";
        String username = "";
        String password = "";
        this.verificarPosiblesSeguidores(NombreRedSocial.GAB, accountId, accessToken, urlBase, username, password);
    } */
    
    /* @Test
    public void verificarPosiblesSeguidoresMinds() throws Exception {
        String accountId = "";
        String accessToken = "";
        String urlBase = "";
        String username = "";
        String password = "";
        this.verificarPosiblesSeguidores(NombreRedSocial.MINDS, accountId, accessToken, urlBase, username, password);
    } */
    
    /* @Test
    public void verificarPosiblesSeguidoresParler() throws Exception {
        String accountId = "";
        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiODFlNGY1Y2E1ZGFkNDM2OTEwYTAxZmVkNjYyM2JjMGE3MTllNzY3YmNlNGM5Njg4NWM5NDVlZWQ4N2ZkNmQyNWMwZjBmZWJkNTlhNmY2YTEiLCJpYXQiOjE3MjgyMTAyMTQuMzcxMTg5LCJuYmYiOjE3MjgyMTAyMTQuMzcxMTkxLCJleHAiOjE3Mjk0MTk4MTQuMzY1NTg2LCJzdWIiOiIyMTU4MTM2NyIsInNjb3BlcyI6WyIqIl19.CjJ1ldoOmMPzgJ4hhLpyb5SLtY1dpsDXLFsBWpvZP7eNWGxdH6Stpyi8_lQbvC9lv3jPZbnnUzVxS_8qLvKxfujJqQBjnJk_1vJNziA0R9lyhdbuqm4I0sVM1Z3irOnJP5Nmd5r3LUyBYOSJ1fsV7zzjsmPVZECD1fmXEZAAV3oQJcC59HNTWf6-_d4Hj8HUFiCJAfcj-l5BsNZVjiWUt42eJ95jBYCcuo6HBzGsQYHTgHRH8GxVh7xIENxR0JG_251r33yedzIfa5q_g-ZZ6dbzWe4P-4u39DUuz4NAqPxXPX5zPybn_5uZl6IIuVDjofaxTrx3wLRSTd_p9VLbVQkbfPWnHW6z_TRKeD8dEDztK3l0XHtJn-sRhYmTr8HceYiUxOgMsIcO8IRL73Bkb3XMu4EqAnim5U3gf95bj24EQq30kylR5ipKWRuirfXakD_WQq1FMONA7Dn_Jvu476mwXUNigYOfM0Pl3B40c6GsCTasJQduCyG99aQy08Kybk7VNdOEmdsNuSMlJK784YrxGF3h7tSZAq6Zg8zpsHcEvAHeMwWmvCs0OH0MAl7b9me6S8TSDJFjKDwOGfhNCHjo1EAtcgfzpjjIUWLwiXjZ3FmGbEQ--YDKlRvDicVJUWAV7iHHvc1KVpZyxj_u-reheGo5z-AFRyT0u9jVuHY";
        String urlBase = "";
        String username = "";
        String password = "";
        this.verificarPosiblesSeguidores(NombreRedSocial.PARLER, accountId, accessToken, urlBase, username, password);
    } */
    
/*    @Test
    public void postearContenidoInstagram() throws Exception {
        String username = "laboratoriorafa";
        String password = "vielma81";
        String texto = "Texto de la publicación de prueba";
        String imagen = "C:\\Users\\rafa\\Pictures\\Formula_1\\Spa_1950.jpg";
        
        ClienteRedSocial cliente = new ClienteRedSocialInstagram(username, password);
        
        Status status = cliente.postStatus(texto, imagen);
        
        assertTrue(status != null);
    } */
    
    @Test
    public void postearContenidoInstagramSinImagen() throws Exception {
        String username = "laboratoriorafa";
        String password = "vielma81";
        String texto = "Texto de la publicación de prueba";
        
        ClienteRedSocial cliente = new ClienteRedSocialInstagram(username, password);
        
        Status status = cliente.postStatus(texto, null);
        
        assertTrue(status == null);
    }
    
    /* @Test
    public void createSessionInstagram() throws Exception {
        String username = "humordespierto";
        String password = "vIelma03";
        
        SessionRequest request = new SessionRequest(username, "", password);
        ClienteRedSocial cliente = new ClienteRedSocialInstagram(username, password);
        Session session = cliente.createSession(request);
        
        assertNotNull(session);
    } */
    
    /* @Test
    public void recuperarTimeLineTruthsocial() throws Exception {
        String accountId = "114093707828642110";
        String accessToken = "6qvW7OYC_z9wGHmU6nkxCaa9YNYsydHV20sEMItOos4";
        String urlBase = "";
        String username = "humordespierto";
        String password = "Vielma03";
        this.verificarPosiblesSeguidores(NombreRedSocial.TRUTHSOCIAL, accountId, accessToken, urlBase, username, password);
    } */
    
    /* @Test
    public void recuperarTimeLineParler() throws Exception {
        String accountId = "01jnkj5dyjkk5mecn304y72jxw";
        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiYTlkOTZmNmM1MGJmM2ExNTBiOTUzNDkwODg1ZGM1NjNmOTU5MTE1YjgwMjFhZDIwNDNmNTM0MDZiNGU0YTU4NTI4YTZkZmFjMzRjM2ZhM2MiLCJpYXQiOjE3NDE0NTQzODEuNDcyNTIxLCJuYmYiOjE3NDE0NTQzODEuNDcyNTI5LCJleHAiOjE3NDI2NjM5ODEuNDUyMTE3LCJzdWIiOiIyMTYzMjE0MiIsInNjb3BlcyI6WyIqIl0sInVzZXJfaWQiOiIwMWpua2o1ZHlqa2s1bWVjbjMwNHk3Mmp4dyJ9.h78EIHSUPc3MSclLceF3KWHzYrzTjoJ0XNax1RYzQE4__OzXhuNRbQnGQ2HTg60uNMHoPAWk-RHFjSvgA4p4ubgYBLoiZNzGJdbI7oVtAsHgjqbSm1ealfxI_6kXiIbCjBsMqYiPnabJMoR4d5zu7p5ga7CJrh6BTZCXKXxGUutk-iYXlvzWLSGNVVLel08cVmBfZXx1UiSyJO8nGqY7xxBI7VAEzyVWKT_4rbXDipAe3z-ay0qGrl4mH8muI5SeM5_GobEFv6BEBUp4GzGigV9q4TigC_erFyp5WJGhQlhBSZWxkHUeVQtV_gxEYvPhz2HDFceRc4s5HIF0WqxWgofQ9AMbto4AwJP_DfW7ytWr4Q-WbviOfe64XnK4GsyZvdrd6WcMIdsG6gABpwQsA3Gq_U1qfZX9NGuQTJVXEV72jXO0hA52VolbMBEa1LtqjT7P2bQr6rqqtSl9UingsqAGRmQXZD3YB--VZluomn-eMwHYsBg59yb2JGv4RGMQDwH1Te_dDLTifRCmUEhisq1YNBuIDDrwGC0vOqypCnr9DFGC0bt5FQT8jUg1YwVrySBHikAME8-l1f3WGBcyVEYpjtbDipfbM2_yCtjnQHFTN_H-yvUXxhFCACwYG1rHZi-llxRgoArdaeSU436GmDA2evgO8pRSie3F8_azJzk";
        String urlBase = "";
        String username = "awakehumor";
        String password = "fukwlpdbwyunzrvc";
        this.verificarPosiblesSeguidores(NombreRedSocial.PARLER, accountId, accessToken, urlBase, username, password);
    } */
}