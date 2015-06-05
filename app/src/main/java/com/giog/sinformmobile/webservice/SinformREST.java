package com.giog.sinformmobile.webservice;

import android.util.Base64;
import android.util.Log;

import com.giog.sinformmobile.model.Course;
import com.giog.sinformmobile.model.Event;
import com.giog.sinformmobile.model.Guest;
import com.giog.sinformmobile.model.Lecture;
import com.giog.sinformmobile.model.User;
import com.giog.sinformmobile.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Giovanne on 09/02/2015.
 */

public class SinformREST {

    //Domínio
//    public static final String DOMINIO = "http://nbcgib.uesc.br/sinform"; //Oficial remoto
//    public static final String DOMINIO = "http://www.sinformapp.com"; //Casa local
//    public static final String DOMINIO = "http://192.168.32.52"; //UESC local
//    public static final String DOMINIO = "http://192.168.0.104"; //Casa Amanda local
    public static final String DOMINIO = "http://sinformapp.comlu.com"; //000WebHost remoto
//    public static final String DOMINIO = "http://sinformapp.890m.com"; //Hostinger remoto


    //Webservice PATHs
//    public static final String GET_USER = DOMINIO + "/example/users/format/json";
    public static final String GET_USER = DOMINIO + "/REST/user/login.php";
    public static final String GET_ABOUT = DOMINIO + "/REST/about/getAbout.php";
    public static final String GET_COURSE = DOMINIO + "/REST/course/getCourse.php";
//    public static final String GET_COURSE_GROUPS = DOMINIO + "/REST/course/getGroups.php";
    public static final String GET_GUEST = DOMINIO + "/REST/guest/getGuest.php";
    public static final String GET_LECTURE = DOMINIO + "/REST/lecture/getLecture.php";

    public List<User> getUser(int userId) throws Exception {

        try {
            List<NameValuePair> args = new ArrayList<NameValuePair>();

            JSONObject json = getJsonResult(GET_USER, args);
            if (json.optString("message") != "null") {
                throw new Exception(json.optString("message"));
            }

            List<User> list = new ArrayList<User>();
            JSONArray jsonArray = json.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new User(jsonArray.getJSONObject(i)));
            }

            return list;
        } catch (JSONException e) {
            throw new Exception("Falha na conexão");
        } catch (SocketException e) {
            throw new Exception("Falha na conexão");
        } catch (IOException e) {
            throw new Exception("Falha ao receber arquivo");
        }
    }

    public List<Guest> getGuest() throws Exception {

        try {

            JSONObject json = getJsonResult(GET_GUEST, null);
            if (!json.optString("status_message").equals("null")) {
                throw new Exception(json.optString("status_message"));
            }

            List<Guest> list = new ArrayList<Guest>();
            JSONArray jsonArray = json.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Guest(jsonArray.getJSONObject(i)));
            }

            return list;
        } catch (JSONException e) {
            throw new Exception("Falha na conexão");
        } catch (SocketException e) {
            throw new Exception("Falha na conexão");
        } catch (IOException e) {
            throw new Exception("Falha ao receber arquivo");
        }
    }

    public List<Course> getCourse() throws Exception {

        try {

            JSONObject json = getJsonResult(GET_COURSE, null);
            if (!json.optString("status_message").equals("null")) {
                throw new Exception(json.optString("status_message"));
            }

            List<Course> list = new ArrayList<Course>();
            JSONArray jsonArray = json.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Course(jsonArray.getJSONObject(i)));
            }

            return list;
        } catch (JSONException e) {
            throw new Exception("Falha na conexão");
        } catch (SocketException e) {
            throw new Exception("Falha na conexão");
        } catch (IOException e) {
            throw new Exception("Falha ao receber arquivo");
        }
    }

    public HashMap<String,List<Course>> getCourseByGroup() throws Exception {

        try {

            List<Course> courses = getCourse();
            List<Integer> groups = getGroupsFromCourses(courses);

            HashMap<String,List<Course>> courseByGroup = new HashMap<>();
            for(Integer group : groups){
                List<Course> aux = new ArrayList<>();
                for(Course course : courses){
                    if(course.getGroup() == group){
                        aux.add(course);
                    }
                }
                courseByGroup.put("Grupo "+String.valueOf(group),aux);
            }

            return courseByGroup;
        } catch (JSONException e) {
            throw new Exception("Falha na conexão");
        } catch (SocketException e) {
            throw new Exception("Falha na conexão");
        }
    }

    private List<Integer> getGroupsFromCourses(List<Course> list){
        List<Integer> found = new ArrayList<>();
        int lastFound = -1;

        for(Course c : list){
            if(c.getGroup() != lastFound){
                lastFound = c.getGroup();
                found.add(lastFound);
            }
        }

        return found;
    }

    public HashMap<String,List<Event>> getEventsByDate() throws Exception{
        List<Event> eventList = new ArrayList<>();
        HashMap<String,List<Event>> eventByDate = new HashMap<>();
        List<Lecture> lectureList = getLecture();
        List<Course> courseList = getCourse();

        if(courseList != null && lectureList != null){
            eventList.addAll(lectureList);
            eventList.addAll(courseList);

            List<String> dates = getDatesFromEvents(eventList);
            for(String date : dates){
                List<Event> aux = new ArrayList<>();
                for(int i = eventList.size()-1; i>=0;i--){
                    if(eventList.get(i).getDayOfWeek() == date){
                        aux.add(eventList.get(i));
                    }
                }
                eventByDate.put(date, aux);
            }
        }

        return eventByDate;
    }

    public HashMap<String,List<Lecture>> getLectureByDate() throws Exception {

        try {

            List<Lecture> lectures = getLecture();
            List<String> dates = getDatesFromLectures(lectures);

            HashMap<String,List<Lecture>> lectureByDate = new HashMap<>();
            for(String date : dates){
                List<Lecture> aux = new ArrayList<>();
//                for(Lecture lecture : lectures){
                for(int i=lectures.size()-1;i>=0;i--){
                    if(lectures.get(i).getDayOfWeek() == date){
                        aux.add(lectures.get(i));
                    }
                }
                lectureByDate.put(date, aux);
            }

            return lectureByDate;
        } catch (JSONException e) {
            throw new Exception("Falha na conexão");
        } catch (SocketException e) {
            throw new Exception("Falha na conexão");
        }
    }

    private List<String> getDatesFromEvents(List<Event> list){
        List<String> found = new ArrayList<>();

        for(Event e : list){
            if(!found.contains(e.getDayOfWeek())){
                found.add(e.getDayOfWeek());
            }
        }

        return found;
    }

    private List<String> getDatesFromLectures(List<Lecture> list){
        List<String> found = new ArrayList<>();

        for(Lecture l : list){
            if(!found.contains(l.getDayOfWeek())){
                found.add(l.getDayOfWeek());
            }
        }

        return found;
    }

    public List<Lecture> getLecture() throws Exception {

        try {

            JSONObject json = getJsonResult(GET_LECTURE, null);
            if (!json.optString("status_message").equals("null")) {
                throw new Exception(json.optString("status_message"));
            }

            List<Lecture> list = new ArrayList<Lecture>();
            JSONArray jsonArray = json.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Lecture(jsonArray.getJSONObject(i)));
            }

            return list;
        } catch (JSONException e) {
            throw new Exception("Falha na conexão");
        } catch (SocketException e) {
            throw new Exception("Falha na conexão");
        } catch (IOException e) {
            throw new Exception("Falha ao receber arquivo");
        }
    }

    public User doLogin(String email, String password)throws Exception {

        try {
            List<NameValuePair> args = new ArrayList<NameValuePair>();
            args.add(new BasicNameValuePair("email", email));
            args.add(new BasicNameValuePair("password", password));

            JSONObject json = getJsonResult(GET_USER, args);

            if (!json.optString("status_message").equals("null")) {
                throw new Exception(json.optString("status_message"));
            }

            return new User(json.getJSONObject("data"));
        } catch (JSONException e) {
            throw new Exception("Falha na conexão");
        } catch (SocketException e) {
            throw new Exception("Falha na conexão");
        } catch (IOException e) {
            throw new Exception("Falha ao receber arquivo");
        }
    }

    public String getAbout() throws Exception {

        try {
            JSONObject json = getJsonResult(GET_ABOUT, null);

            if (!json.optString("status_message").equals("null")) {
                throw new Exception(json.optString("status_message"));
            }

            return json.getString("data");
        } catch (JSONException e) {
            throw new Exception("Falha na conexão");
        } catch (SocketException e) {
            throw new Exception("Falha na conexão");
        }
    }

    private JSONObject getJsonResult(final String url, final List<NameValuePair> args) throws IOException, JSONException {

        HttpResponse httpResponse = null;

        try {
            httpResponse = HttpUtil.httpPost(url, args, null, "application/json");
        } catch (Exception ex){
            throw new SocketException("Falha na conectividade");
        }

        if (httpResponse == null) {
            throw new IOException("Falha de conectividade: null");
        }

        if (httpResponse.getStatusLine().getStatusCode() / 100 == HttpStatus.SC_OK / 100) {
            String response = HttpUtil.streamToString(httpResponse.getEntity().getContent());
            return new JSONObject(response);

        } else {
            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED){
                throw new IOException("Conexão não autenticada.\nVerifique sua conexão.");
            } else {
                throw new IOException("Falha de conectividade: " + httpResponse.getStatusLine().getStatusCode());
            }
        }

    }
}
