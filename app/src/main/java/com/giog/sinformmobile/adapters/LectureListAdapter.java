package com.giog.sinformmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.fragments.CourseFragment;
import com.giog.sinformmobile.fragments.LectureFragment;
import com.giog.sinformmobile.model.Course;
import com.giog.sinformmobile.model.Lecture;
import com.giog.sinformmobile.webservice.SinformREST;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giovanne on 12/02/2015.
 */
public class LectureListAdapter extends BaseAdapter {

	private List<Lecture> list;
	private Context context;

	private static final String TAG = LectureFragment.class.getSimpleName();

	private SinformREST sinformRest;

	public LectureListAdapter(List<Lecture> list, Context context) {
		this.list = list;
		this.context = context;
	}


    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Lecture getItem(int position) {
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
            convertView = infalInflater.inflate(R.layout.item_lecture, null);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvDay = (TextView) convertView.findViewById(R.id.tvDay);
        TextView tvAbout = (TextView) convertView.findViewById(R.id.tvAbout);

        Lecture lecture = list.get(position);

        tvTitle.setText(lecture.getTitle());
        tvTitle.setSelected(true);
        tvAbout.setText(lecture.getAbout());
        tvTime.setText(lecture.getFormattedTime()+"hs - ");
        tvDate.setText(lecture.getFormattedDate());
        tvDay.setText(lecture.getDayOfWeek());

        return convertView;
    }

    public void setList(List<Lecture> list) {
            if (this.list == null) {
                this.list = new ArrayList<Lecture>();
            }
            this.list.clear();
            this.list.addAll(list);
    }
}
