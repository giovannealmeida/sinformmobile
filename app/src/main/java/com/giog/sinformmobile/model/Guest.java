package com.giog.sinformmobile.model;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by Giovanne on 12/02/2015.
 */
public class Guest {
    private int id;
    private String name;
    private String description;
//    private File photo;


    public Guest(JSONObject jsonObject) {
        if(jsonObject != null) {
            this.id = jsonObject.optInt("id");
            this.name = jsonObject.optString("name");
            this.description = jsonObject.optString("description");
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
