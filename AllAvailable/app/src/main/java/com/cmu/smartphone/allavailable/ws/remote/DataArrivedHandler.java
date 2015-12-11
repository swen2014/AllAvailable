package com.cmu.smartphone.allavailable.ws.remote;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import java.util.List;

/**
 * Handle the case when data arrived from server
 *
 * @author Xi Wang
 * @version 1.0
 */
public class DataArrivedHandler extends Handler {

    private View loadingView;
    private ListView listView;
    private boolean isEnd;

    /**
     * Constructor
     *
     * @param listView
     * @param loadingView
     */
    public DataArrivedHandler(ListView listView, View loadingView) {
        isEnd = false;
        this.listView = listView;
        this.loadingView = loadingView;
    }

    /**
     * Handle the message
     *
     * @param paramMessage
     */
    @Override
    public void handleMessage(android.os.Message paramMessage) {
        if (paramMessage.what == 1) {
            loadingView.setVisibility(View.GONE);
        } else if (paramMessage.what == 2) {
            listView.removeFooterView(loadingView);
        }
    }

    /**
     * Action when data arrived
     *
     * @param list
     * @param isEnd
     */
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
