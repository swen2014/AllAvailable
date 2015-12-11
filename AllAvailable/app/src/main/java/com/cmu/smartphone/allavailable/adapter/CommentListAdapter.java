package com.cmu.smartphone.allavailable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.entities.CommentBean;

import java.util.List;

/**
 * This class fill in the item of the Comment List in the Comment page
 *
 * @author Xi Wang
 * @version 1.0
 */
public class CommentListAdapter extends BaseAdapter {

    private Context context;
    private List<CommentBean> lists;
    private LayoutInflater layoutInflater;
    private ImageView commentIcon;
    private TextView commentInfo;
    private TextView commentTitle;

    /**
     * Default Constructor
     *
     * @param context the Activity Context
     * @param lists   the List of all comments
     */
    public CommentListAdapter(Context context, List<CommentBean> lists) {
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
            convertView = layoutInflater.inflate(R.layout.comment_item, null);
        }

        commentIcon = (ImageView) convertView.findViewById(R.id.comment_icon);
        commentInfo = (TextView) convertView.findViewById(R.id.comment_info);
        commentTitle = (TextView) convertView.findViewById(R.id.comment_title);
        CommentBean comment = lists.get(position);

        if (comment.getImagePath() == null || comment.getImagePath().equals("N/A")) {
            commentIcon.setVisibility(View.INVISIBLE);
        } else {
            commentIcon.setVisibility(View.VISIBLE);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(comment.getUserId().substring(0, comment.getUserId().indexOf('@')));
        sb.append("(");
        sb.append(comment.getDate());
        sb.append(",");
        sb.append(comment.getTime());
        sb.append(")");
        commentTitle.setText(sb.toString());
        commentInfo.setText(comment.getContent());

        return convertView;
    }
}
