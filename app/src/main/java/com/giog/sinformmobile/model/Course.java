package com.giog.sinformmobile.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Giovanne on 09/02/2015.
 */
public class Course extends Event {

    private int group;

    public Course(JSONObject jsonObject) throws ParseException, JSONException {
        super(jsonObject);
        if(jsonObject != null) {
            this.group = jsonObject.getInt("group");
        }
    }

    public int getGroup() {
        return group;
    }

}
