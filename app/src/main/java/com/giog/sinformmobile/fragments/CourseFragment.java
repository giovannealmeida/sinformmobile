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

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.activities.CourseDetailsActivity;
import com.giog.sinformmobile.adapters.CourseExpandableListAdapter;
import com.giog.sinformmobile.model.Course;
import com.giog.sinformmobile.webservice.SinformREST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    private ExpandableListView expandableListView;
    private CourseExpandableListAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvEmptyText;
    private List<String> groups;

    private SinformREST sinformREST = new SinformREST();
    private GetData getData;

    public static CourseFragment newInstance(int sectionNumber) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putInt("section_number", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_course, container, false);

        this.progressBar = (ProgressBar) viewRoot.findViewById(R.id.progressBar);
        this.tvEmptyText = (TextView) viewRoot.findViewById(R.id.tvEmptyText);
        this.expandableListView = (ExpandableListView) viewRoot.findViewById(R.id.elvCourses);
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
        startActivity(new Intent(getActivity(),CourseDetailsActivity.class).putExtra("course",((Course) adapter.getChild(groupPosition,childPosition))));
        return true;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
    }

    private class GetData extends AsyncTask<Void, Void, HashMap <String,List<Course>>> {

        protected String message;

        @Override
        protected HashMap <String,List<Course>> doInBackground(Void... params) {

            message = "";
            if (!isOnline(getActivity().getApplicationContext())) {
                message = "Sem conexão com Internet";
                return null;
            }

            try {
//                return sinformREST.getCourse();
                return sinformREST.getCourseByGroup();
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
        protected void onPostExecute(HashMap <String,List<Course>> coursesByGroup) {
            super.onPostExecute(coursesByGroup);

            if (coursesByGroup != null && !isCancelled()) {
                groups = new ArrayList<String>(coursesByGroup.keySet());
                adapter = new CourseExpandableListAdapter(groups,coursesByGroup,expandableListView,getActivity());
                expandableListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                for(int i=0; i < adapter.getGroupCount(); i++)
                    expandableListView.expandGroup(i);
            }

            progressBar.setVisibility(View.GONE);
            if (adapter.isEmpty() && adapter != null) {
                tvEmptyText.setVisibility(View.VISIBLE);
            }
        }
    }
}
