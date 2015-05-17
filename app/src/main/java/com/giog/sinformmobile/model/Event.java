package com.giog.sinformmobile.model;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Giovanne on 12/02/2015.
 */
public class Event {

    private int id;
    private String name;
//    private long time;
    private Date time;
    private String description;
    /* 1- Minicurso
       2- Palestra*/
    private int type;

//    public Event(JSONObject jsonObject) {
//        if(jsonObject != null) {
//            this.id = jsonObject.optInt("id");
//            this.name = jsonObject.optString("name");
//            this.time = jsonObject.optLong("time");
//            this.description = jsonObject.optString("description");
//        }
//    }


    public Event(int id, String name, String time, String description, int type) {
        this.id = id;
        this.name = name;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            this.time = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.description = description;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() { return description; }

    public Date getTime() { return time; }

    public int getType() {
        return type;
    }
}
