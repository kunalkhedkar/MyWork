package com.example.kunal.databasewithroom;

import java.lang.ref.PhantomReference;

public class ModelUserContact {

    int uid;
    String name,phone;

    int cid;
    String mobile,landLine,parentMobile;

    public ModelUserContact(int uid, String name, String phone, int cid, String mobile, String landLine, String parentMobile) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.cid = cid;
        this.mobile = mobile;
        this.landLine = landLine;
        this.parentMobile = parentMobile;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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
