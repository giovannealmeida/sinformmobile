package com.giog.sinformmobile.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.giog.sinformmobile.model.User;
import com.giog.sinformmobile.webservice.SinformREST;

import java.util.List;

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

    private GetData getData;

    private SinformREST sinformREST;
    private LinearLayout titleLayout;

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

    private class GetData extends AsyncTask<Void, Void, List<User>> {

        protected String message;

        @Override
        protected List<User> doInBackground(Void... params) {

            message = "";
            if (!isOnline(getActivity().getApplicationContext())) {
                message = "Sem conexão com Internet";
                return null;
            }

            try {
                return sinformREST.getUser(0);
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
        protected void onPostExecute(List<User> user) {
            super.onPostExecute(user);

            String users = "";

            if (user != null && !isCancelled()) {
                for (int i = 0; i < user.size(); i++) {
                    users = users + user.get(i).getName() + "\n";
                }
                tvDescription.setText(users);
                tvDescription.setVisibility(View.VISIBLE);

            } else {
                Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
                tvEmptyText.setVisibility(View.VISIBLE);
            }

            progressBar.setVisibility(View.GONE);
        }
    }
}
