package com.giog.sinformmobile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.giog.sinformmobile.adapters.ExpandableListAdapter;
import com.giog.sinformmobile.R;
import com.giog.sinformmobile.model.Event;

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
    private HashMap<String,List<Event>> listCourses;

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
        listCourses = new HashMap<String,List<Event>>();

        expandableListView = (ExpandableListView) rootView.findViewById(R.id.elvEvents);
        expandableListView.setOnChildClickListener(this);



        /*Populando lista*/
        Event c1, c2, c3, c4, c5, c6, l1, l2, l3, l4, l5, l6, l7, l8, l9;

        c1 = new Event(1, "MC1", "08:30:00", "MC1 ordinário",1);
        c2 = new Event(2, "MC2", "10:30:00", "MC2 ordinário",1);
        c3 = new Event(3, "MC3", "13:30:00", "MC3 ordinário",1);
        c4 = new Event(4, "MC4", "08:30:00", "MC4 ordinário",1);
        c5 = new Event(5, "MC5", "10:30:00", "MC5 ordinário",1);
        c6 = new Event(6, "MC6", "08:30:00", "MC6 ordinário",1);
        l1 = new Event(7, "LC1", "08:30:00", "LC1 ordinária",2);
        l2 = new Event(7, "LC2", "10:30:00", "LC2 ordinária",2);
        l3 = new Event(7, "LC3", "13:30:00", "LC3 ordinária",2);
        l4 = new Event(7, "LC4", "08:30:00", "LC4 ordinária",2);
        l5 = new Event(7, "LC5", "10:30:00", "LC5 ordinária",2);
        l6 = new Event(7, "LC6", "13:30:00", "LC6 ordinária",2);
        l7 = new Event(7, "LC7", "08:30:00", "LC7 ordinária",2);
        l8 = new Event(7, "LC8", "10:30:00", "LC8 ordinária",2);
        l9 = new Event(7, "LC9", "13:30:00", "LC9 ordinária",2);

        List<Event> segundaE = new ArrayList<Event>();
        List<Event> tercaE = new ArrayList<Event>();
        List<Event> quartaE = new ArrayList<Event>();

        segundaE.add(c1);
        segundaE.add(c2);
        segundaE.add(c3);
        segundaE.add(l1);
        segundaE.add(l2);
        segundaE.add(l3);

        tercaE.add(c4);
        tercaE.add(c5);
        tercaE.add(l4);
        tercaE.add(l5);
        tercaE.add(l6);

        quartaE.add(c6);
        quartaE.add(l7);
        quartaE.add(l8);
        quartaE.add(l9);

        listDays = new ArrayList<String>();
        listCourses = new HashMap<String,List<Event>>();

        listDays.add("Segunda-feira 16/02");
        listDays.add("Terça-feira 17/02");
        listDays.add("Quarta-feira 18/02");

        listCourses.put(listDays.get(0),segundaE);
        listCourses.put(listDays.get(1),tercaE);
        listCourses.put(listDays.get(2),quartaE);

        adapter = new ExpandableListAdapter(listDays,listCourses,expandableListView,getActivity());
        expandableListView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        Event course = (Event) adapter.getChild(groupPosition,childPosition);
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
