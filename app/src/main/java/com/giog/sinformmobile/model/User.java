package com.giog.sinformmobile.model;

import org.json.JSONObject;

/**
 * Created by Giovanne on 09/02/2015.
 */
public class User {

    private int id;
    private String name;
    private String email;

    public User(JSONObject jsonObject){
        if(jsonObject != null) {
            this.id = jsonObject.optInt("id");
            this.name = jsonObject.optString("name");
            this.email = jsonObject.optString("email");
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
}
