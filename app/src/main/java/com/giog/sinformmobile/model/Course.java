package com.giog.sinformmobile.model;

import android.util.Log;

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
public class Course implements Serializable {

    private int id;
    private String title;
    private int guest;
    private Calendar date;
    private String local;
    private String about;

    public Course(JSONObject jsonObject) throws ParseException {
        if(jsonObject != null) {
            this.id = jsonObject.optInt("id");
            this.title = jsonObject.optString("title");
            this.guest = jsonObject.optInt("guest");
            this.date = getFormattedDate(jsonObject.optString("date")); //2015-09-22 08:00:00
            this.local = jsonObject.optString("local");
            this.about = jsonObject.optString("about");

        }
    }

    public static Calendar getFormattedDate(String strDate) {
        if (strDate == null || strDate.equals(""))
            return null;

        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(strDate));
        } catch (ParseException e) {
            Log.e("Course parsing date",e.getMessage());
        }
        return cal;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getGuest() {
        return guest;
    }

    public Calendar getDate() {
        return date;
    }

    public String getFormattedDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
        return dateFormat.format(getDate().getTime());
    }

    public String getFormattedTime(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(getDate().getTime());
    }

    public String getDayOfWeek(){
        switch (date.get(Calendar.DAY_OF_WEEK)){
            case Calendar.MONDAY:
                return "Segunda";
            case Calendar.TUESDAY:
                return "Terça";
            case Calendar.THURSDAY:
                return "Quarta";
            case Calendar.WEDNESDAY:
                return "Quinta";
            case Calendar.FRIDAY:
                return "Sexta";
            case Calendar.SATURDAY:
                return "Sábado";
            case Calendar.SUNDAY:
                return "Domingo";
            default:
                return "????";
        }
    }

    public String getLocal() {
        return local;
    }

    public String getAbout() {
        return about;
    }
}
