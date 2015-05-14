package com.giog.sinformmobile.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.giog.sinformmobile.MainActivity;
import com.giog.sinformmobile.R;
import com.giog.sinformmobile.model.User;
import com.giog.sinformmobile.webservice.SinformREST;

import java.util.HashMap;

/**
 * Created by Giovanne on 25/04/2015.
 */
public class LoginDialog extends DialogFragment {

    private EditText etUsername, etPassword;
    private TextView tvMessage;
    private SinformREST sinformREST;
    private LoginTask loginTask;
    private ProgressBar progressBar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_login, null);

        sinformREST = new SinformREST();
        loginTask = new LoginTask();

        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etUsername = (EditText) view.findViewById(R.id.etEmail);
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                HashMap<String, String> args = new HashMap<String, String>();
                if (etUsername.getText().toString().trim().isEmpty() || etPassword.getText().toString().trim().isEmpty()) {
                    tvMessage.setText("Os campos são obrigatórios!");
                } else {
                    args.put("username", etUsername.getText().toString());
                    args.put("password", etPassword.getText().toString());

                    loginTask.execute(args);
                }
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }

    private class LoginTask extends AsyncTask<HashMap<String,String>, Void, User> {

        protected String message;

        @Override
        protected User doInBackground(HashMap<String,String>...params) {

            HashMap<String,String> args = params[0];

            message = "";
            if (!isOnline(getActivity().getApplicationContext())) {
                message = "Sem conexão com Internet";
                return null;
            }

            try {
                return sinformREST.doLogin(args.get("username"),args.get("password"));
            } catch (Exception e) {
                message = e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            MainActivity.SESSION_USER = user;

            progressBar.setVisibility(View.GONE);
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }

        return false;
    }
}
