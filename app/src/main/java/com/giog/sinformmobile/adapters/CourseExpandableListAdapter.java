package com.giog.sinformmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.model.Course;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Giovanne on 12/02/2015.
 */
public class CourseExpandableListAdapter extends BaseExpandableListAdapter {

	private List<String> listGroups;
	private HashMap<String,List<Course>> listCourses;
	private Context context;

	private int lastExpandedGroupPosition = -1;
	private ExpandableListView expandableListView;

	public CourseExpandableListAdapter(List<String> listGroups, HashMap<String, List<Course>> listCourses, ExpandableListView expandableListView, Context context) {
		this.listGroups = listGroups;
		this.context = context;
		this.expandableListView = expandableListView;
		this.listCourses = listCourses;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return listCourses.get(listGroups.get(groupPosition)).size();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return listCourses.get(listGroups.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final Course course = (Course) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.item_expandable_course_child, null);
		}

		TextView tvTitle = (TextView) convertView
				.findViewById(R.id.tvTitle);

		TextView tvAbout = (TextView) convertView
				.findViewById(R.id.tvAbout);

        tvTitle.setText(course.getTitle());
        tvAbout.setText(course.getAbout());

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public int getGroupCount() {
		return listGroups.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return listGroups.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.item_expandable_course_group, null);
		}

		TextView txtName = (TextView) convertView
				.findViewById(R.id.tvGroup);

		txtName.setText((String) getGroup(groupPosition));
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

//	@Override
//	public void onGroupExpanded(int groupPosition){
//		//collapse the old expanded group, if not the same
//		//as new group to expand
//		if(groupPosition != lastExpandedGroupPosition){
//			expandableListView.collapseGroup(lastExpandedGroupPosition);
//		}
//
//		super.onGroupExpanded(groupPosition);
//		lastExpandedGroupPosition = groupPosition;
//	}
}
