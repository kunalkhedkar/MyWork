package com.example.kunal.databasewithroom;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(
        primaryKeys = { "userId", "contactId" },
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId"),
                @ForeignKey(entity = Contact.class,
                        parentColumns = "id",
                        childColumns = "contactId")
        })
public class User_Contact {

    private  int userId;
    private  int contactId;

    public User_Contact(int userId, int contactId) {
        this.userId = userId;
        this.contactId = contactId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
