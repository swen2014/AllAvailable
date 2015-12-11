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
 * This class fill in the item of the Schedule Item List in the Schedule page
 *
 * @author Xi Wang
 * @version 1.0
 */
public class ScheduleItemAdapter extends BaseAdapter {

    private Context context;
    private List<ScheduleListItem> lists;
    private LayoutInflater layoutInflater;
    private TextView timeText;
    private TextView placeText;

    /**
     * Default Constructor
     *
     * @param context the Activity Context
     * @param lists   the List of all schedule items
     */
    public ScheduleItemAdapter(Context context, List<ScheduleListItem> lists) {
        this.context = context;
        this.lists = lists;
        layoutInflater = LayoutInflater.from(this.context);
    }

    /**
     * Get the count of the list
     *
     * @return the count of the list
     */
    @Override
    public int getCount() {
        return lists.size();
    }

    /**
     * Get the item of the given position
     *
     * @param position the position number
     * @return the item of the given position
     */
    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    /**
     * Get the item id of the given position
     *
     * @param position the position number
     * @return the item of the given position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get the view of the given position
     *
     * @param position    the position number
     * @param convertView the convert view
     * @param parent      the parent view
     * @return the view of the given position
     */
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
