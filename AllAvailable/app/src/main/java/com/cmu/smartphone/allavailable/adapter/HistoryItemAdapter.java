package com.cmu.smartphone.allavailable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.entities.ReservationBean;

import java.util.List;

/**
 * Created by wangxi on 11/20/15.
 */
public class HistoryItemAdapter extends BaseAdapter {

    private Context context;
    private List<ReservationBean> lists;
    private LayoutInflater layoutInflater;

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.schedule_item, null);
        }
//        timeText = (TextView) convertView.findViewById(R.id.schedule_time);
//        placeText = (TextView) convertView.findViewById(R.id.schedule_place);
//        timeText.setText(lists.get(position).getTime());
//        placeText.setText(lists.get(position).getPlace());
        return convertView;
    }
}
