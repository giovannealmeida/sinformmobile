package com.giog.sinformmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.model.Course;
import com.giog.sinformmobile.model.Event;
import com.giog.sinformmobile.model.Lecture;
import com.giog.sinformmobile.webservice.SinformREST;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Giovanne on 12/02/2015.
 */
public class ProgrammingExpandableListAdapter extends BaseExpandableListAdapter {

	private List<String> listDays;
	private HashMap<String,List<Event>> listEvents;
	private Context context;

	public ProgrammingExpandableListAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return listEvents != null ? listEvents.get(listDays.get(groupPosition)).size():0;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return listEvents.get(listDays.get(groupPosition)).get(childPosition);
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
			convertView = infalInflater.inflate(R.layout.item_expandable_programming_child, null);
		}

		TextView txtName = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView txtAbout = (TextView) convertView.findViewById(R.id.tvAbout);
        TextView txtTime = (TextView) convertView.findViewById(R.id.tvTime);
        ImageView ivTypeImg = (ImageView) convertView.findViewById(R.id.ivIcon);

        txtName.setText(event.getTitle());
        txtAbout.setText(event.getAbout());
        txtTime.setText(event.getFormattedTime()+"hs");

        if(event instanceof Course){
            ivTypeImg.setImageResource(R.drawable.img_course);
        } else if(event instanceof Lecture) {
            ivTypeImg.setImageResource(R.drawable.img_lecture);
        }

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public int getGroupCount() {
		return listDays != null ? listDays.size():0;
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
			convertView = infalInflater.inflate(R.layout.item_expandable_default_group, null);
		}

		TextView txtName = (TextView) convertView
				.findViewById(R.id.tvGroup);

        ((ImageView) convertView.findViewById(R.id.ivIcon)).setVisibility(View.GONE);
		txtName.setText(day);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

    public void setListDays(List<String> listDays) {
        this.listDays = listDays;
    }

    public void setListEvents(HashMap<String, List<Event>> listEvents) {
        this.listEvents = listEvents;
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
