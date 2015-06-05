package com.giog.sinformmobile.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.activities.CourseDetailsActivity;
import com.giog.sinformmobile.activities.LectureDetailsActivity;
import com.giog.sinformmobile.adapters.ProgrammingExpandableListAdapter;
import com.giog.sinformmobile.model.Course;
import com.giog.sinformmobile.model.Event;
import com.giog.sinformmobile.model.Lecture;
import com.giog.sinformmobile.webservice.SinformREST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Subcalsse de {@link android.support.v4.app.Fragment}.
 * <br/>
 * Use o método {@link com.giog.sinformmobile.fragments.ProgrammingFragment#newInstance(int)} para
 * criar uma nova instância desse fragmento.
 * <br/>
 * Exemplo:
 * Fragment newFragment = HomeFragment.newInstance(sectionNumber);
 */
public class ProgrammingFragment extends Fragment implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    private ExpandableListView expandableListView;
    private ProgrammingExpandableListAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvEmptyText;
    private GetData getData;

    private SinformREST sinformREST;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Número da posição do item selecionado no Drawer.
     * @return A new instance of fragment ProgrammingFragment.
     */

    public static ProgrammingFragment newInstance(int sectionNumber) {
        ProgrammingFragment fragment = new ProgrammingFragment();
        Bundle args = new Bundle();
        args.putInt("section_number", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ProgrammingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_programming, container, false);

        adapter = new ProgrammingExpandableListAdapter(getActivity());

        expandableListView = (ExpandableListView) rootView.findViewById(R.id.elvEvents);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        tvEmptyText = (TextView) rootView.findViewById(R.id.tvEmptyText);

        expandableListView.setAdapter(adapter);

        sinformREST = new SinformREST();
        getData = new GetData();
        getData.execute();
        return rootView;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        Event event = (Event) adapter.getChild(groupPosition,childPosition);

        if(event instanceof Course){
            startActivity(new Intent(getActivity(),CourseDetailsActivity.class).putExtra("course",event));
        } else if (event instanceof Lecture) {
            startActivity(new Intent(getActivity(),LectureDetailsActivity.class).putExtra("lecture",event));
        }

        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(!getData.isCancelled()){
            getData.cancel(true);
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

    private class GetData extends AsyncTask<Void, Void, HashMap <String,List<Event>>> {

        protected String message;

        @Override
        protected HashMap <String,List<Event>> doInBackground(Void... params) {

            message = "";
            if (!isOnline(getActivity().getApplicationContext())) {
                message = "Sem conexão com Internet";
                return null;
            }

            try {
                return sinformREST.getEventsByDate();
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
        protected void onPostExecute(HashMap <String,List<Event>> eventsByDate) {
            super.onPostExecute(eventsByDate);

            if (eventsByDate != null && !isCancelled()) {
                List<String> days = new ArrayList<>(eventsByDate.keySet());
                adapter.setListDays(days);
                adapter.setListEvents(eventsByDate);
                expandableListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                for(int i=0; i < adapter.getGroupCount(); i++)
                    expandableListView.expandGroup(i);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }

            progressBar.setVisibility(View.GONE);
            if (adapter.isEmpty()) {
                tvEmptyText.setVisibility(View.VISIBLE);
            }
        }
    }

}
