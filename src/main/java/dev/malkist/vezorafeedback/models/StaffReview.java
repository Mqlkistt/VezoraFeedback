package dev.malkist.vezorafeedback.models;

import java.util.UUID;

public class StaffReview {

    private final int id;
    private final UUID reviewerUUID;
    private final String reviewer;
    private final String staff;
    private final int rating;
    private final String comment;
    private final String date;

    public StaffReview(int id,
                       UUID reviewerUUID,
                       String reviewer,
                       String staff,
                       int rating,
                       String comment,
                       String date) {

        this.id = id;
        this.reviewerUUID = reviewerUUID;
        this.reviewer = reviewer;
        this.staff = staff;
        this.rating = rating;
        this.comment = comment;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public UUID getReviewerUUID() {
        return reviewerUUID;
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getStaff() {
        return staff;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

}