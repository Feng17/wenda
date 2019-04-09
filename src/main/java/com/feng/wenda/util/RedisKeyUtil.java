package com.feng.wenda.util;


public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String LIKE = "like";
    private static String DISLIKE = "dislike";

    public static String getLikeKey(int entityType, int entityId) {
        return LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityType, int entityId) {
        return DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

}
