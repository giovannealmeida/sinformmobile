package com.giog.sinformmobile.model;

import org.json.JSONObject;

/**
 * Created by Giovanne on 09/02/2015.
 */
public class User {

    private int id;
    private String name;
    private String email;
    private String password;

    public User(JSONObject jsonObject){
        if(jsonObject != null) {
            this.id = jsonObject.optInt("id");
            this.name = jsonObject.optString("name");
            this.email = jsonObject.optString("email");
            this.password = jsonObject.optString("password");
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

    public String getPassword(){return password;}
}
