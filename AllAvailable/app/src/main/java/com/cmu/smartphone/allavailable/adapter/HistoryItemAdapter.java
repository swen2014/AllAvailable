package com.cmu.smartphone.allavailable.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.entities.ReservationView;
import com.cmu.smartphone.allavailable.ui.CommentActivity;

import java.util.List;

/**
 * This class fill in the item of the History List in the History page
 *
 * @author Xi Wang
 * @version 1.0
 */
public class HistoryItemAdapter extends BaseAdapter {

    private Context context;
    private List<ReservationView> lists;
    private LayoutInflater layoutInflater;
    private TextView historyOverviewTime;
    private TextView historyOverviewRoom;
    private ImageButton commentButton;

    /**
     * Default Constructor
     *
     * @param context the Activity Context
     * @param lists   the List of all history items
     */
    public HistoryItemAdapter(Context context, List<ReservationView> lists) {
        this.context = context;
        this.lists = lists;
        layoutInflater = LayoutInflater.from(context);
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
            convertView = layoutInflater.inflate(R.layout.history_item, null);
        }

        historyOverviewTime = (TextView) convertView.findViewById(R.id.history_time_overview);
        historyOverviewRoom = (TextView) convertView.findViewById(R.id.history_room_overview);
        commentButton = (ImageButton) convertView.findViewById(R.id.history_comment_button);

        final ReservationView reservationView = lists.get(position);

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("room", reservationView.getRoom());
                intent.putExtra("roomId", reservationView.getRoom().getRoomId());
                intent.putExtra("buildingId", reservationView.getBuilding().getBuildingId());
                context.startActivity(intent);
            }
        });

        String time = reservationView.getReservation().getDate() + ", "
                + reservationView.getReservation().getTime();
        historyOverviewTime.setText(time);
        String room = reservationView.getBuilding().getBuildingName() +
                ", " + reservationView.getRoom().getName();
        historyOverviewRoom.setText(room);

        return convertView;
    }
}
