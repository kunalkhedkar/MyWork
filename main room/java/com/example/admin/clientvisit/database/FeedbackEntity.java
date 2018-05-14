package com.example.admin.clientvisit.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = BusinessEntity.class,
        parentColumns = "bid",
        childColumns = "businessId",
        onDelete = CASCADE))
public class FeedbackEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int fid;

    private int businessId;
    private boolean problemSlovedStatus;
    private String date, time, latitude, longitude, reasonBehindVisit, nameOfVisitedPerson, contactOfVisitedPerson, feedback;

    public FeedbackEntity(int fid, int businessId, boolean problemSlovedStatus, String date, String time, String latitude, String longitude, String reasonBehindVisit, String nameOfVisitedPerson, String contactOfVisitedPerson, String feedback) {
        this.fid = fid;
        this.businessId = businessId;
        this.problemSlovedStatus = problemSlovedStatus;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reasonBehindVisit = reasonBehindVisit;
        this.nameOfVisitedPerson = nameOfVisitedPerson;
        this.contactOfVisitedPerson = contactOfVisitedPerson;
        this.feedback = feedback;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public boolean isProblemSlovedStatus() {
        return problemSlovedStatus;
    }

    public void setProblemSlovedStatus(boolean problemSlovedStatus) {
        this.problemSlovedStatus = problemSlovedStatus;
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

    public String getReasonBehindVisit() {
        return reasonBehindVisit;
    }

    public void setReasonBehindVisit(String reasonBehindVisit) {
        this.reasonBehindVisit = reasonBehindVisit;
    }

    public String getNameOfVisitedPerson() {
        return nameOfVisitedPerson;
    }

    public void setNameOfVisitedPerson(String nameOfVisitedPerson) {
        this.nameOfVisitedPerson = nameOfVisitedPerson;
    }

    public String getContactOfVisitedPerson() {
        return contactOfVisitedPerson;
    }

    public void setContactOfVisitedPerson(String contactOfVisitedPerson) {
        this.contactOfVisitedPerson = contactOfVisitedPerson;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}