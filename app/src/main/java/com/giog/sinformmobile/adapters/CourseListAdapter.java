package com.giog.sinformmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.fragments.CourseFragment;
import com.giog.sinformmobile.fragments.ProgrammingFragment;
import com.giog.sinformmobile.model.Course;
import com.giog.sinformmobile.model.Event;
import com.giog.sinformmobile.webservice.SinformREST;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Giovanne on 12/02/2015.
 */
public class CourseListAdapter extends BaseAdapter {

	private List<Course> list;
	private Context context;

	private static final String TAG = CourseFragment.class.getSimpleName();

	private SinformREST sinformRest;

	public CourseListAdapter(List<Course> list, Context context) {
		this.list = list;
		this.context = context;
	}


    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Course getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_course, null);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvDay = (TextView) convertView.findViewById(R.id.tvDay);
        TextView tvAbout = (TextView) convertView.findViewById(R.id.tvAbout);

        Course course = list.get(position);

        tvTitle.setText(course.getTitle());
        tvTitle.setSelected(true);
        tvAbout.setText(course.getAbout());
        tvTime.setText(course.getFormattedTime()+" - ");
        tvDate.setText(course.getFormattedDate());
        tvDay.setText(course.getDayOfWeek());

        return convertView;
    }

    public void setList(List<Course> list) {
            if (this.list == null) {
                this.list = new ArrayList<Course>();
            }
            this.list.clear();
            this.list.addAll(list);
    }
}
