package de.luvoryx.ranksigns;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.google.gson.Gson;

public class UUIDFetcher {
    
    private static final String API_URL = "https://playerdb.co/api/player/minecraft/%s";
    
    private static Map<String, UUID> uuidCache = new HashMap<>();
    private static Map<UUID, String> nameCache = new HashMap<>();
    private static ExecutorService pool = Executors.newCachedThreadPool();
    
    public static void getUUID(String name, Consumer<UUID> action) {
        pool.execute(() -> action.accept(getUUID(name)));
    }

    public static UUID getUUID(String name) {
        name = name.toLowerCase();
        if (uuidCache.containsKey(name)) {
            return uuidCache.get(name);
        }
        
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(String.format(API_URL, name)).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0"); // <-- Das hier hinzufügen!

            connection.setReadTimeout(5000);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Gson gson = new Gson();
            PlayerDBResponse response = gson.fromJson(reader, PlayerDBResponse.class);
            
            if (response.success) {
                UUID uuid = UUID.fromString(response.data.player.id);
                uuidCache.put(name, uuid);
                nameCache.put(uuid, name);
                return uuid;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public static void getName(UUID uuid, Consumer<String> action) {
        pool.execute(() -> action.accept(getName(uuid)));
    }

    public static String getName(UUID uuid) {
        if (nameCache.containsKey(uuid)) {
            return nameCache.get(uuid);
        }
        
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(String.format(API_URL, uuid.toString())).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0"); // <-- Das hier hinzufügen!

            connection.setReadTimeout(5000);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Gson gson = new Gson();
            PlayerDBResponse response = gson.fromJson(reader, PlayerDBResponse.class);
            
            if (response.success) {
                String name = response.data.player.username;
                nameCache.put(uuid, name);
                return name;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public static void clearCache() {
        uuidCache.clear();
        nameCache.clear();
    }

    // JSON-Parsing-Klassen für PlayerDB API
    private static class PlayerDBResponse {
        boolean success;
        PlayerDBData data;
    }

    private static class PlayerDBData {
        PlayerInfo player;
    }

    private static class PlayerInfo {
        String username;
        String id;
    }
}
