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
 * Created by wangxi on 11/20/15.
 */
public class HistoryItemAdapter extends BaseAdapter {

    private Context context;
    private List<ReservationView> lists;
    private LayoutInflater layoutInflater;
    private TextView historyOverviewTime;
    private TextView historyOverviewRoom;
    private ImageButton commentButton;

    public HistoryItemAdapter(Context context, List<ReservationView> lists) {
        this.context = context;
        this.lists = lists;
        layoutInflater = LayoutInflater.from(context);
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
