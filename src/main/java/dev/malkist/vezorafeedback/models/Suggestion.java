package dev.malkist.vezorafeedback.models;

import dev.malkist.vezorafeedback.enums.SuggestionStatus;

import java.util.UUID;

public class Suggestion {

    private final int id;
    private final UUID uuid;
    private final String player;
    private final String message;
    private final String date;
    private SuggestionStatus status;

    public Suggestion(int id,
                      UUID uuid,
                      String player,
                      String message,
                      String date,
                      SuggestionStatus status) {

        this.id = id;
        this.uuid = uuid;
        this.player = player;
        this.message = message;
        this.date = date;
        this.status = status;

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

    public SuggestionStatus getStatus() {
        return status;
    }

    public void setStatus(SuggestionStatus status) {
        this.status = status;
    }

}