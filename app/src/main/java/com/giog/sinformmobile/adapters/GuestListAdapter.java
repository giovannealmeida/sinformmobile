package com.giog.sinformmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.fragments.GuestFragment;
import com.giog.sinformmobile.model.Guest;
import com.giog.sinformmobile.webservice.SinformREST;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giovanne on 12/02/2015.
 */
public class GuestListAdapter extends BaseAdapter {

	private List<Guest> list;
	private Context context;

	private static final String TAG = GuestFragment.class.getSimpleName();

	private SinformREST sinformRest;

	public GuestListAdapter(List<Guest> list, Context context) {
		this.list = list;
		this.context = context;
	}


    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Guest getItem(int position) {
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
            convertView = infalInflater.inflate(R.layout.item_guest, null);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvAbout = (TextView) convertView.findViewById(R.id.tvAbout);

        Guest guest = list.get(position);

        tvName.setText(guest.getName());
        tvName.setSelected(true);
        tvAbout.setText(guest.getAbout());

        return convertView;
    }

    public void setList(List<Guest> list) {
            if (this.list == null) {
                this.list = new ArrayList<Guest>();
            }
            this.list.clear();
            this.list.addAll(list);
    }
}
