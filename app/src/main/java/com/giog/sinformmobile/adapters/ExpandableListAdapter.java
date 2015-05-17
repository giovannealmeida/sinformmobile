package com.giog.sinformmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.fragments.ProgrammingFragment;
import com.giog.sinformmobile.model.Event;
import com.giog.sinformmobile.webservice.SinformREST;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Giovanne on 12/02/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private List<String> listDays;
	private HashMap<String,List<Event>> listCourses;
	private Context context;

	private int lastExpandedGroupPosition = -1;
	private ExpandableListView expandableListView;

	private static final String TAG = ProgrammingFragment.class.getSimpleName();

	private SinformREST sinformRest;

	public ExpandableListAdapter(List<String> listDays, HashMap<String, List<Event>> listCourses, ExpandableListView expandableListView, Context context) {
		this.listDays = listDays;
		this.context = context;
		this.expandableListView = expandableListView;
		this.listCourses = listCourses;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return listCourses.get(listDays.get(groupPosition)).size();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return listCourses.get(listDays.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final Event event = (Event) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.item_event, null);
		}

		TextView txtTime = (TextView) convertView
				.findViewById(R.id.tvTime);

		TextView txtName = (TextView) convertView
				.findViewById(R.id.tvName);

        ImageView ivTypeImg = (ImageView) convertView.findViewById(R.id.ivTypeImg);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

		txtTime.setText(String.valueOf(formatter.format(event.getTime())) + " - ");
        txtName.setText(event.getName());
        switch (event.getType()){
            case 1: //Minicurso
                ivTypeImg.setImageResource(R.drawable.img_course);
                break;
            case 2: //Palestra
                ivTypeImg.setImageResource(R.drawable.img_lecture);
                break;
        }

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public int getGroupCount() {
		return listDays.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return listDays.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		final String day = (String) getGroup(groupPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.item_day, null);
		}

		TextView txtName = (TextView) convertView
				.findViewById(R.id.tvDay);

		txtName.setText(day);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public void onGroupExpanded(int groupPosition){
		//collapse the old expanded group, if not the same
		//as new group to expand
		if(groupPosition != lastExpandedGroupPosition){
			expandableListView.collapseGroup(lastExpandedGroupPosition);
		}

		super.onGroupExpanded(groupPosition);
		lastExpandedGroupPosition = groupPosition;
	}
}
