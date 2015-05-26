package com.giog.sinformmobile.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.webservice.SinformREST;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Subcalsse de {@link Fragment}.
 * <br/>
 * Use o método {@link HomeFragment#newInstance(int)} para
 * criar uma nova instância desse fragmento.
 * <br/>
 * Exemplo:
 * Fragment newFragment = HomeFragment.newInstance(sectionNumber);
 */
public class HomeFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView tvEmptyText;
    private TextView tvDescription;
    private TextView tvCountDown;
    private Calendar theGreatDay;

    private GetData getData;

    private SinformREST sinformREST;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Número da posição do item selecionado no Drawer.
     * @return A new instance of fragment HomeFragment.
     */

    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("section_number", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sinformREST = new SinformREST();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        this.tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        this.progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        this.tvEmptyText = (TextView) rootView.findViewById(R.id.tvEmptyText);
        this.tvCountDown = (TextView) rootView.findViewById(R.id.tvCountDown);

        Calendar today = Calendar.getInstance();// Hoje

        theGreatDay = Calendar.getInstance(); //Dia do evento

        //Setando dia para 21/09 09:00:00
        theGreatDay.set(Calendar.DAY_OF_MONTH,21);
        theGreatDay.set(Calendar.MONTH,Calendar.SEPTEMBER);
        theGreatDay.set(Calendar.HOUR_OF_DAY,9);
        theGreatDay.set(Calendar.MINUTE,0);
        theGreatDay.set(Calendar.SECOND,0);

        long remaining = theGreatDay.getTime().getTime() - today.getTime().getTime();

        new CountDownTimer(remaining,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                Calendar remaining = Calendar.getInstance(TimeZone.getTimeZone("GTM-03:00")); //Hoje
                remaining.setTimeInMillis(millisUntilFinished);

                tvCountDown.setText("Faltam "+
                        String.valueOf(remaining.get(Calendar.DAY_OF_YEAR)-1+" dias ")+
                        String.valueOf(remaining.get(Calendar.HOUR_OF_DAY)+" horas ")+
                        String.valueOf(remaining.get(Calendar.MINUTE)+" minutos ")+
                        String.valueOf(remaining.get(Calendar.SECOND)+" segundos"));
            }

            @Override
            public void onFinish() {
                tvCountDown.setText("");
                tvCountDown.setVisibility(View.GONE);
            }
        }.start();


        this.getData = new GetData();
        getData.execute();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }

        return false;
    }

    private class GetData extends AsyncTask<Void, Void, String> {

        protected String message;

        @Override
        protected String doInBackground(Void... params) {

            message = "";
            if (!isOnline(getActivity().getApplicationContext())) {
                message = "Sem conexão com Internet";
                return null;
            }

            try {
                return sinformREST.getAbout();
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
        protected void onPostExecute(String user) {
            super.onPostExecute(user);

            String users = "";

            if (user != null && !isCancelled()) {
                tvDescription.setText(user);
                tvDescription.setVisibility(View.VISIBLE);

            } else {
                Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
                tvEmptyText.setVisibility(View.VISIBLE);
            }

            progressBar.setVisibility(View.GONE);
        }
    }
}
