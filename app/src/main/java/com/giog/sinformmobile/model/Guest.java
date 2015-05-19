package com.giog.sinformmobile.model;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Giovanne on 12/02/2015.
 */
public class Guest implements Serializable{
    private int id;
    private String name;
    private String email;
    private String about;
//    private File photo;


    public Guest(JSONObject jsonObject) {
        if(jsonObject != null) {
            this.id = jsonObject.optInt("id");
            this.name = jsonObject.optString("name");
            this.email = jsonObject.optString("email");
            this.about = jsonObject.optString("about");
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAbout() {
        return about;
    }
}
