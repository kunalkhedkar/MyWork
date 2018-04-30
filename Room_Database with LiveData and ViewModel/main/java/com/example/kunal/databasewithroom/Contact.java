package com.example.kunal.databasewithroom;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private  int id;

    private String mobile;

    private String landLine;

    private String parentMobile;

    public Contact(int id, String mobile, String landLine, String parentMobile) {
        this.id = id;
        this.mobile = mobile;
        this.landLine = landLine;
        this.parentMobile = parentMobile;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandLine() {
        return landLine;
    }

    public void setLandLine(String landLine) {
        this.landLine = landLine;
    }

    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }
}
