package com.giog.sinformmobile.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.giog.sinformmobile.adapters.ExpandableListAdapter;
import com.giog.sinformmobile.R;
import com.giog.sinformmobile.model.Course;

import org.json.JSONObject;

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
public class ProgrammingFragment extends Fragment implements ExpandableListView.OnChildClickListener {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;

    private List<String> listDays;
    private HashMap<String,List<Course>> listCourses;

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

        listDays =  new ArrayList<String>();
        listCourses = new HashMap<String,List<Course>>();

        expandableListView = (ExpandableListView) rootView.findViewById(R.id.elvEvents);
        expandableListView.setOnChildClickListener(this);



        /*Populando lista*/
        Course c1, c2, c3, c4, c5, c6;
//        c1 = new Course(1, "MC1", "Mon Feb 16 08:30:00 MYT 2015", "MC1 ordinário");
//        c2 = new Course(2, "MC2", "Mon Feb 16 10:30:00 MYT 2015", "MC2 ordinário");
//        c3 = new Course(3, "MC3", "Mon Feb 16 13:30:00 MYT 2015", "MC3 ordinário");
//        c4 = new Course(4, "MC4", "Tue Feb 17 08:30:00 MYT 2015", "MC4 ordinário");
//        c5 = new Course(5, "MC5", "Tue Feb 17 10:30:00 MYT 2015", "MC5 ordinário");
//        c6 = new Course(6, "MC6", "Wed Feb 18 08:30:00 MYT 2015", "MC6 ordinário");

        c1 = new Course(1, "MC1", "08:30:00", "MC1 ordinário");
        c2 = new Course(2, "MC2", "10:30:00", "MC2 ordinário");
        c3 = new Course(3, "MC3", "13:30:00", "MC3 ordinário");
        c4 = new Course(4, "MC4", "08:30:00", "MC4 ordinário");
        c5 = new Course(5, "MC5", "10:30:00", "MC5 ordinário");
        c6 = new Course(6, "MC6", "08:30:00", "MC6 ordinário");

        List<Course> segundaC = new ArrayList<Course>();
        List<Course> tercaC = new ArrayList<Course>();
        List<Course> quartaC = new ArrayList<Course>();

        segundaC.add(c1);
        segundaC.add(c2);
        segundaC.add(c3);

        tercaC.add(c4);
        tercaC.add(c5);

        quartaC.add(c6);

        listDays = new ArrayList<String>();
        listCourses = new HashMap<String,List<Course>>();

        listDays.add("Segunda-feira 16/02");
        listDays.add("Terça-feira 17/02");
        listDays.add("Quarta-feira 18/02");

        listCourses.put(listDays.get(0),segundaC);
        listCourses.put(listDays.get(1),tercaC);
        listCourses.put(listDays.get(2),quartaC);

        adapter = new ExpandableListAdapter(listDays,listCourses,expandableListView,getActivity());
        expandableListView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        Course course = (Course) adapter.getChild(groupPosition,childPosition);
        Toast.makeText(getActivity(),course.getName(),Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getActivity(), CourseDetailsActivity.class);
//        intent.putExtra("course", (android.os.Parcelable) course);
//        startActivity(intent);

        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
