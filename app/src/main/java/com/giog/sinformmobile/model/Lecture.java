package com.giog.sinformmobile.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Giovanne on 09/02/2015.
 */
public class Lecture extends Event implements Serializable {

    public Lecture(JSONObject jsonObject) throws ParseException, JSONException {
        super(jsonObject);
    }
}
