package com.example.admin.clientvisit.model;

import java.io.Serializable;

public class FeedbackData implements Serializable{

    private String date,time,latitude,longitude,clientId,feedback;

    public FeedbackData(String date, String time, String latitude, String longitude, String clientId, String feedback) {
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.clientId = clientId;
        this.feedback = feedback;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
