package com.giog.sinformmobile.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.adapters.CourseListAdapter;
import com.giog.sinformmobile.model.Course;
import com.giog.sinformmobile.webservice.SinformREST;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {

    private ListView lvCourse;
    private CourseListAdapter listAdapter;
    private ProgressBar progressBar;
    private TextView tvEmptyText;
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

        listAdapter = new CourseListAdapter(null,getActivity());
        this.progressBar = (ProgressBar) viewRoot.findViewById(R.id.progressBar);
        this.tvEmptyText = (TextView) viewRoot.findViewById(R.id.tvEmptyText);
        this.lvCourse = (ListView) viewRoot.findViewById(R.id.lvCourse);

        this.lvCourse.setAdapter(listAdapter);

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

    private class GetData extends AsyncTask<Void, Void, List<Course>> {

        protected String message;

        @Override
        protected List<Course> doInBackground(Void... params) {

            message = "";
            if (!isOnline(getActivity().getApplicationContext())) {
                message = "Sem conexão com Internet";
                return null;
            }

            try {
                return sinformREST.getCourse();
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
        protected void onPostExecute(List<Course> list) {
            super.onPostExecute(list);

            if (list != null && !isCancelled()) {
                listAdapter.setList(list);
                listAdapter.notifyDataSetChanged();
            }

            progressBar.setVisibility(View.GONE);
            if (listAdapter.isEmpty()) {
                tvEmptyText.setVisibility(View.VISIBLE);
            }
        }
    }
}
