package com.example.admin.clientvisit.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class OwnerEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int oid;

    private String ownerName;

    public OwnerEntity(int oid, String ownerName) {
        this.oid = oid;
        this.ownerName = ownerName;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        ownerName = ownerName;
    }
}
