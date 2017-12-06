package com.bytemecollege.mytube;

/**
 * Getting the fields which the app needs from YouTube.
 *
 * Created by jiajunwu on 12/05/2017.
 */

public class Youtube {
    private String videoId;
    private String publishedAt;
    private String title;
    private String description;

    public String getVideoId() {
        return videoId.split(":")[1].replaceAll("\\\"", "").trim();
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPublishedAt() {
        return publishedAt.split(":")[1].trim().split("T")[0]
                .replaceAll("\\\"", "")
                ;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title.split("\":")[1]
                .replaceAll("\\\\\"", "\"")
                .replaceAll("\\\"", "")
                .trim()
                ;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description.split("\":")[1]
                .replaceAll("\\\\\"", "\"")
                .replaceAll("\\\"", "")
                .trim()
                ;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
