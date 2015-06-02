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
import com.giog.sinformmobile.activities.LectureDetailsActivity;
import com.giog.sinformmobile.adapters.LectureExpandableListAdapter;
import com.giog.sinformmobile.model.Lecture;
import com.giog.sinformmobile.webservice.SinformREST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link LectureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LectureFragment extends Fragment implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    private ExpandableListView expandableListView;
    private LectureExpandableListAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvEmptyText;
    private List<String> days;

    private SinformREST sinformREST = new SinformREST();
    private GetData getData;

    public static LectureFragment newInstance(int sectionNumber) {
        LectureFragment fragment = new LectureFragment();
        Bundle args = new Bundle();
        args.putInt("section_number", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public LectureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_lecture, container, false);
        adapter = new LectureExpandableListAdapter(getActivity(),expandableListView);

        this.progressBar = (ProgressBar) viewRoot.findViewById(R.id.progressBar);
        this.tvEmptyText = (TextView) viewRoot.findViewById(R.id.tvEmptyText);
        this.expandableListView = (ExpandableListView) viewRoot.findViewById(R.id.elvLectures);
        this.expandableListView.setOnChildClickListener(this);
        this.expandableListView.setOnGroupClickListener(this);

        this.getData = new GetData();
        getData.execute();

        return viewRoot;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }

        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(!getData.isCancelled()){
            getData.cancel(true);
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        startActivity(new Intent(getActivity(),LectureDetailsActivity.class).putExtra("lecture",((Lecture) adapter.getChild(groupPosition,childPosition))));
        return true;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
    }

    private class GetData extends AsyncTask<Void, Void, HashMap <String,List<Lecture>>> {

        protected String message;

        @Override
        protected HashMap <String,List<Lecture>> doInBackground(Void... params) {

            message = "";
            if (!isOnline(getActivity().getApplicationContext())) {
                message = "Sem conexão com Internet";
                return null;
            }

            try {
                return sinformREST.getLectureByDate();
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
        protected void onPostExecute(HashMap <String,List<Lecture>> lectureByDate) {
            super.onPostExecute(lectureByDate);

            if (lectureByDate != null && !isCancelled()) {
                days = new ArrayList<String>(lectureByDate.keySet());
                adapter.setListDates(days);
                adapter.setListLecturesByDate(lectureByDate);
                expandableListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                for(int i=0; i < adapter.getGroupCount(); i++)
                    expandableListView.expandGroup(i);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }

            progressBar.setVisibility(View.GONE);
            if (adapter != null && adapter.isEmpty()) {
                tvEmptyText.setVisibility(View.VISIBLE);
            }
        }
    }
}
