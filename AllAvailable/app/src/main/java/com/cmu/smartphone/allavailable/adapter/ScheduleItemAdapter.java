package com.cmu.smartphone.allavailable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.model.ScheduleListItem;

import java.util.List;

/**
 * Created by wangxi on 11/6/15.
 */
public class ScheduleItemAdapter extends BaseAdapter {

    private Context context;
    private List<ScheduleListItem> lists;
    private LayoutInflater layoutInflater;
    private TextView timeText;
    private TextView placeText;

    public ScheduleItemAdapter(Context context, List<ScheduleListItem> lists) {
        this.context = context;
        this.lists = lists;
        layoutInflater = LayoutInflater.from(this.context);
    }

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
        timeText = (TextView) convertView.findViewById(R.id.schedule_time);
        placeText = (TextView) convertView.findViewById(R.id.schedule_place);
        timeText.setText(lists.get(position).getTime());
        placeText.setText(lists.get(position).getPlace());
        return convertView;
    }

}
