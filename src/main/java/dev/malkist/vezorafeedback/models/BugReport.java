package dev.malkist.vezorafeedback.models;

import java.util.UUID;

public class BugReport {

    private final int id;
    private final UUID uuid;
    private final String player;
    private final String message;
    private final String date;

    public BugReport(int id,
                     UUID uuid,
                     String player,
                     String message,
                     String date) {

        this.id = id;
        this.uuid = uuid;
        this.player = player;
        this.message = message;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

}