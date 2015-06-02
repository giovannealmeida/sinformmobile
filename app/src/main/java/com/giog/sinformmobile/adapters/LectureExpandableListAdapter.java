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
import com.giog.sinformmobile.model.Lecture;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Giovanne on 12/02/2015.
 */
public class LectureExpandableListAdapter extends BaseExpandableListAdapter {

	private List<String> listDates;
	private HashMap<String,List<Lecture>> listLecturesByDate;
	private Context context;

	private ExpandableListView expandableListView;

    public LectureExpandableListAdapter(Context context, ExpandableListView expandableListView) {
        this.context = context;
        this.expandableListView = expandableListView;
    }

    @Override
	public int getChildrenCount(int groupPosition) {
		return listLecturesByDate!=null?listLecturesByDate.get(listDates.get(groupPosition)).size():0;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return listLecturesByDate.get(listDates.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final Lecture lecture = (Lecture) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.item_expandable_lecture_child, null);
		}

		TextView tvTitle = (TextView) convertView
				.findViewById(R.id.tvTitle);

		TextView tvAbout = (TextView) convertView
				.findViewById(R.id.tvAbout);

        tvTitle.setText(lecture.getTitle());
        tvAbout.setText(lecture.getAbout());

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public int getGroupCount() {
		return listDates != null?listDates.size():0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return listDates.get(groupPosition);
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
			convertView = infalInflater.inflate(R.layout.item_expandable_default_group, null);
		}

		ImageView ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
        ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.img_lecture));
		TextView txtName = (TextView) convertView.findViewById(R.id.tvGroup);

		txtName.setText((String) getGroup(groupPosition));
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

    public void setListDates(List<String> listDates) {
        this.listDates = listDates;
    }

    public void setListLecturesByDate(HashMap<String, List<Lecture>> listLecturesByDate) {
        this.listLecturesByDate = listLecturesByDate;
    }
}
