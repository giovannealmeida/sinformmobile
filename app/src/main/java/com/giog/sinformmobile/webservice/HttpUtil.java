package com.giog.sinformmobile.webservice;

/**
 * Created by Giovanne on 09/02/2015.
 */

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

public class HttpUtil {

    /**
     * Controla o que fica aguradando os dados após a conexão ser estabelecida.
     */
    public static final Integer WAIT_DATA_TIMOUT = Integer.valueOf(7000);// 7 segundos
    /**
     * Controla o tempo que a conexão ficará aguardando para ser estabelecida.
     */
    public static final Integer CONNECTION_TIMOUT = Integer.valueOf(5000);// 5 segundos

    private static final String TAG = HttpUtil.class.getSimpleName();

    public static final HttpResponse httpPost(String url, List<NameValuePair> args, String auth, String accept) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();

        // set http params
        HttpParams params = httpClient.getParams();
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMOUT);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, WAIT_DATA_TIMOUT);
        httpClient.setParams(params);

        HttpPost httpPost = new HttpPost(url);
        if(args != null)
            httpPost.setEntity(new UrlEncodedFormEntity(args));
//        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");

        if (auth != null) {
            httpPost.setHeader("Authorization", auth);
        }

        if (accept != null) {
            httpPost.setHeader("Accept", accept);
        }

        return httpClient.execute(httpPost);

    }

    public static final HttpResponse httpGet(String url, List<NameValuePair> args, String auth, String accept) throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());

        // set http params
        HttpParams params = httpClient.getParams();
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMOUT);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, WAIT_DATA_TIMOUT);
        httpClient.setParams(params);

        HttpGet httpGet = new HttpGet(url + "?" + getValidArgs(args));
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        if (auth != null) {
            httpGet.setHeader("Authorization", auth);
        }

        if (accept != null) {
            httpGet.setHeader("Accept", accept);
        }

        return httpClient.execute(httpGet);
    }

    public static String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = sb.toString();
        }

        return str;
    }

    public static String getValidArgs(List<NameValuePair> argsList) {

        if (argsList == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        Iterator<NameValuePair> iterator = argsList.iterator();
        NameValuePair arg;
        boolean first = true;
        while (iterator.hasNext()) {
            arg = iterator.next();

            if (isValidArg(arg)) {

                if (!first) {
                    sb.append("&");
                }
                try {
                    sb.append(arg.getName() + "=" + URLEncoder.encode(arg.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    if (sb.charAt(sb.length() - 1) == '&') {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    Log.w(TAG, e);
                }

                first = false;
            }

        }

        return sb.toString();
    }

    public static boolean isValidArg(NameValuePair arg) {
        if (arg.getValue() != null && arg.getValue().length() > 0) {
            return true;
        }
        return false;
    }
}
