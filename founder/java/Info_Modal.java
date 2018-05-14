package com.example.silicon.founder_name;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Info_Modal implements Serializable {
    String Lang_Name;
    String Founder_Name;


public Info_Modal(String lang_Name,String founder_Name){
    this.Lang_Name = lang_Name;
    this.Founder_Name = founder_Name;
}


    public String getLang_Name() {
        return Lang_Name;
    }

    public void setLang_Name(String lang_Name) {
        Lang_Name = lang_Name;
    }

    public String getFounder_Name() {
        return Founder_Name;
    }

    public void setFounder_Name(String founder_Name) {
        Founder_Name = founder_Name;
    }



}
