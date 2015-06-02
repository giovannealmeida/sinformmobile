package com.giog.sinformmobile.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
 * Subcalsse de {@link android.support.v4.app.Fragment}.
 * <br/>
 */
public class LoginFragment extends Fragment {

    private EditText etEmail, etPassword;
    private Button btEnter;
    private TextView tvMessage;
    private SinformREST sinformREST;
    private LoginTask loginTask;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_login, container, false);
        sinformREST = new SinformREST();
        loginTask = new LoginTask();

        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        btEnter = (Button) view.findViewById(R.id.btEnter);
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> args = new HashMap<String, String>();
                if (etEmail.getText().toString().trim().isEmpty() || etPassword.getText().toString().trim().isEmpty()) {
                    tvMessage.setText("Os campos são obrigatórios!");
                } else {
                    args.put("email", etEmail.getText().toString());
                    args.put("password", etPassword.getText().toString());

                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);

                    loginTask.execute(args);
                }
            }
        });

        return view;
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
                return sinformREST.doLogin(args.get("email"),args.get("password"));
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
            if(user != null) {
                super.onPostExecute(user);

                MainActivity.SESSION_USER = user;

//                Menu menu = NavigationDrawerFragment.menu;
                Menu menu = MainActivity.menu;
                if (menu != null)
                    menu.findItem(R.id.action_login).setTitle(user.getName());
            } else {
                Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
            }

            progressBar.setVisibility(View.GONE);
            getActivity().onBackPressed();
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
