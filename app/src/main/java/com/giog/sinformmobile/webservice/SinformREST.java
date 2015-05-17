package com.giog.sinformmobile.webservice;

import android.util.Base64;
import android.util.Log;

import com.giog.sinformmobile.model.Course;
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
import java.util.List;

/**
 * Created by Giovanne on 09/02/2015.
 */

public class SinformREST {

    //Domínio
//    public static final String DOMINIO = "http://nbcgib.uesc.br/sinform"; //Oficial remoto
//    public static final String DOMINIO = "http://192.168.1.104"; //Casa local
//    public static final String DOMINIO = "http://192.168.32.52"; //UESC local
//    public static final String DOMINIO = "http://192.168.0.104"; //Casa Amanda local
    public static final String DOMINIO = "http://sinformapp.comlu.com"; //000WebHost remoto
//    public static final String DOMINIO = "http://sinformapp.890m.com"; //Hostinger remoto


    //Webservice PATHs
//    public static final String GET_USER = DOMINIO + "/example/users/format/json";
    public static final String GET_USER = DOMINIO + "/REST/user/login.php";
    public static final String GET_ABOUT = DOMINIO + "/REST/about/getAbout.php";
    public static final String GET_COURSE = DOMINIO + "/REST/course/getCourse.php";

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
        } catch (IOException e) {
            throw new Exception("Falha ao receber arquivo");
        }
    }

    private JSONObject getJsonResult(final String url, final List<NameValuePair> args) throws IOException, JSONException {

        HttpResponse httpResponse = null;

        try {
//            httpResponse = HttpUtil.httpGet(url, args, null, "application/json");
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
            throw new IOException("Falha de conectividade: " + httpResponse.getStatusLine().getStatusCode());
        }

    }
}
