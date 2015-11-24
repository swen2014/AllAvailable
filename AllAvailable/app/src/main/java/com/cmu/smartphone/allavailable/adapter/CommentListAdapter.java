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
 * Created by wangxi on 11/22/15.
 */
public class CommentListAdapter extends BaseAdapter {

    private Context context;
    private List<CommentBean> lists;
    private LayoutInflater layoutInflater;
    private ImageView commentIcon;
    private TextView commentInfo;
    private TextView commentTitle;

    public CommentListAdapter (Context context, List<CommentBean> lists) {
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
            convertView = layoutInflater.inflate(R.layout.comment_item, null);
        }

        commentIcon = (ImageView) convertView.findViewById(R.id.comment_icon);
        commentInfo = (TextView) convertView.findViewById(R.id.comment_info);
        commentTitle = (TextView) convertView.findViewById(R.id.comment_title);
        CommentBean comment = lists.get(position);

        if (comment.getImagePath() == null || comment.getImagePath().equals("")) {
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
