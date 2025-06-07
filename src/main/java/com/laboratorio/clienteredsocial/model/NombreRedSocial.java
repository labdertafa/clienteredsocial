package com.laboratorio.clienteredsocial.model;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 26/07/2024
 * @updated 07/06/2025
 */
public enum NombreRedSocial {
    MASTODON(1),
    BLUESKY(2),
    INSTAGRAM(3),
    TELEGRAM(4),
    LINKEDIN(5),
    TRUTHSOCIAL(6),
    THREADS(7),
    GETTR(8),
    COUNTER(9),
    GAB(10),
    MINDS(11),
    PARLER(12),
    TWITTER(13),
    TUMBLR(14);
    
    private final int value;
    
    NombreRedSocial(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static NombreRedSocial fromValue(int value) {
        for (NombreRedSocial redSocial : NombreRedSocial.values()) {
            if (redSocial.value == value) {
                return redSocial;
            }
        }
        throw new IllegalArgumentException("NombreRedSocial: No enum constant with value " + value);
    }
}