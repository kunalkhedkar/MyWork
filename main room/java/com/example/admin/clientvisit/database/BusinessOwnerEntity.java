package com.example.admin.clientvisit.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(
        primaryKeys = { "businessId", "ownerId" },
        foreignKeys = {
                @ForeignKey(entity = BusinessEntity.class,
                        parentColumns = "bid",
                        childColumns = "businessId"),
                @ForeignKey(entity = OwnerEntity.class,
                        parentColumns = "oid",
                        childColumns = "ownerId")
        })
public class BusinessOwnerEntity {

    private int businessId,ownerId;

    public BusinessOwnerEntity(int businessId, int ownerId) {
        this.businessId = businessId;
        this.ownerId = ownerId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
