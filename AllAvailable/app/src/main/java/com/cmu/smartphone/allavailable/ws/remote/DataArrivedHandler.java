package com.cmu.smartphone.allavailable.ws.remote;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import java.util.List;

/**
 * Created by wangxi on 12/3/15.
 */
public class DataArrivedHandler extends Handler {

    private View loadingView;
    private ListView listView;
    private boolean isEnd;


    public DataArrivedHandler(ListView listView, View loadingView) {
        isEnd = false;
        this.listView = listView;
        this.loadingView = loadingView;
    }

    @Override
    public void handleMessage(android.os.Message paramMessage) {
        if (paramMessage.what == 1) {
            loadingView.setVisibility(View.GONE);
        } else if (paramMessage.what == 2) {
            listView.removeFooterView(loadingView);
        }
    }

    public void serverDataArrived(List list, boolean isEnd) {
        this.isEnd = isEnd;
        android.os.Message localMessage = new Message();
        if (!isEnd) {
            localMessage.what = 1;
        } else {
            localMessage.what = 2;
        }

        this.sendMessage(localMessage);
    }
}
