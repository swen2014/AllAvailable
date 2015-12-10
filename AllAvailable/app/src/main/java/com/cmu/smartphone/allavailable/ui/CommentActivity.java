package com.cmu.smartphone.allavailable.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.adapter.CommentListAdapter;
import com.cmu.smartphone.allavailable.entities.CommentBean;
import com.cmu.smartphone.allavailable.entities.RoomBean;
import com.cmu.smartphone.allavailable.exception.NetworkException;
import com.cmu.smartphone.allavailable.util.JsonHelper;
import com.cmu.smartphone.allavailable.ws.remote.DataArrivedHandler;
import com.cmu.smartphone.allavailable.ws.remote.DataReceiver;
import com.cmu.smartphone.allavailable.ws.remote.ImageUploader;
import com.cmu.smartphone.allavailable.ws.remote.SessionControl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private static final int ASK_PHOTOS = 1;

    private EditText editComment;
    private ImageButton cameraPhoto;
    private ListView listView;
    private Button createComment;
    private Uri uri;
//    private Bitmap bitmap;

    private List<CommentBean> commentResults;
    private RoomBean room;

    private DataArrivedHandler handler;

    private TransparentProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        room = (RoomBean) intent.getSerializableExtra("room");

        SessionControl session = SessionControl.getInstance();
        final String user = session.getUserSession(this);
        final String uriHost = getResources().getText(R.string.host).toString();

        new GetCommentAsyncTask().execute(uriHost, room.getRoomId() + "");

        editComment = (EditText) findViewById(R.id.edit_post);
        cameraPhoto = (ImageButton) findViewById(R.id.button_camera);
        createComment = (Button) findViewById(R.id.post_button);
        listView = (ListView) findViewById(R.id.comment_list);

        View loadingView = LayoutInflater.from(this).inflate(R.layout.listfooter,
                null);

        listView.addFooterView(loadingView);
        handler = new DataArrivedHandler(listView, loadingView);

        CommentListAdapter adapter = new CommentListAdapter(this, new ArrayList<CommentBean>());
        listView.setAdapter(adapter);

        cameraPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentActivity.this, TakePhotoActivity.class);
                startActivityForResult(intent, ASK_PHOTOS);
            }
        });

        createComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editComment.getText().toString();
                editComment.setText("");
                if (uri == null) {
                    new CreateCommentAsyncTask().execute(uriHost, user, room.getRoomId() + "", text, null);
                } else {

                    String[] split = uri.toString().split("/");
                    if (split.length == 0) {
                        Log.v("Image problem", "The image is null");
                        return;
                    }
                    String last = split[split.length - 1];

                    ImageUploader uploader = new ImageUploader(CommentActivity.this);
                    ContentResolver cr = CommentActivity.this.getContentResolver();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        uploader.uploadImage(bitmap, uriHost + "Comment", last);
                        new CreateCommentAsyncTask().execute(uriHost, user, room.getRoomId() + "", text, last);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    uri = null;
                }
            }
        });

        progress = new TransparentProgressDialog(this, R.drawable.progress);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showImageDialog("file:///storage/emulated/0/Pictures/AllAvailable/IMG_20151123_200656.jpg");
                CommentBean comment = commentResults.get((int) id);
                if (!comment.getImagePath().equals("N/A")) {
                    progress.show();
                    new GetImageAsyncTask().execute(uriHost, comment.getImagePath());
                }
            }
        });
    }

    /**
     * Override the onOptionsItemSelected method
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ASK_PHOTOS) { // Please, use a final int instead of hardcoded int value
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(CommentActivity.this, "Image Append!", Toast.LENGTH_SHORT).show();
                String value = data.getStringExtra(ImageUploader.RESULT_TYPE);
                uri = Uri.parse(value);

                Log.v("uri", uri.toString());
            }
        }
    }

    private void showImageDialog(Bitmap bitmap) {
        // Get screen size
        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

//        // Get target image size
//        ContentResolver cr = this.getContentResolver();
//        Bitmap bitmap = null;
//            bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(uri)));

        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();

        // Scale the image down to fit perfectly into the screen
        // The value (250 in this case) must be adjusted for phone/tables displays
        while (bitmapHeight > (screenHeight - 250) || bitmapWidth > (screenWidth - 250)) {
            bitmapHeight = bitmapHeight * 9 / 10;
            bitmapWidth = bitmapWidth * 9 / 10;
        }

        // Create resized bitmap image
        BitmapDrawable resizedBitmap = new BitmapDrawable(this.getResources(),
                Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, false));

        // Create dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.thumbnail_dialog);

        ImageView image = (ImageView) dialog.findViewById(R.id.imageview);

        // Do here setBackground() instead of setImageDrawable() //
        image.setBackground(resizedBitmap);

        dialog.getWindow().setBackgroundDrawable(null);

        dialog.show();


    }

    public class CreateCommentAsyncTask extends
            AsyncTask<String, Integer, ArrayList<CommentBean>> {


        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<CommentBean> result) {
            try {
                if (result == null) {
                    throw new NetworkException();
                }

                commentResults = result;
                CommentListAdapter adapter = new CommentListAdapter(CommentActivity.this, result);

                listView.setAdapter(adapter);

            } catch (NetworkException ne)

            {
                ne.fix(CommentActivity.this);
            }
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected ArrayList<CommentBean> doInBackground(String... arg0) {
            ArrayList<CommentBean> result = null;

            try {
                URL url = new URL(arg0[0]
                        + "Comment");
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                StringBuffer params = new StringBuffer();
                params.append("action=new&user=")
                        .append(arg0[1]).append("&rId=")
                        .append(arg0[2]).append("&content=")
                        .append(arg0[3]).append("&pic=")
                        .append(arg0[4]);
                Log.v("DEBUG", params.toString());
                byte[] bypes = params.toString().getBytes();
                connection.getOutputStream().write(bypes);
                int code = connection.getResponseCode();
                if (code == 200) {

                    StringBuilder sb = new StringBuilder();
                    sb.append(arg0[0]);
                    sb.append("Comment?action=check&rId=");
                    sb.append(arg0[2]);

                    url = new URL(sb.toString());
                    Log.v("DEBUG", url.toString());
                    connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setConnectTimeout(3000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    code = connection.getResponseCode();
                    if (code == 200) {
                        String jsonString = DataReceiver.ChangeInputStream(connection
                                .getInputStream());
                        Log.v("result", jsonString);
                        result = (ArrayList<CommentBean>) JsonHelper.getComments(jsonString);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public class GetCommentAsyncTask extends
            AsyncTask<String, Integer, ArrayList<CommentBean>> {


        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<CommentBean> result) {
            try {
                if (result == null) {
                    throw new NetworkException();
                }

                commentResults = result;
                CommentListAdapter adapter = new CommentListAdapter(CommentActivity.this, result);

                listView.setAdapter(adapter);
                handler.serverDataArrived(result, true);

            } catch (NetworkException ne)

            {
                ne.fix(CommentActivity.this);
            }
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected ArrayList<CommentBean> doInBackground(String... arg0) {
            ArrayList<CommentBean> result = null;

            try {

                StringBuilder sb = new StringBuilder();
                sb.append(arg0[0]);
                sb.append("Comment?action=check&rId=");
                sb.append(arg0[1]);

                URL url = new URL(sb.toString());
                Log.v("DEBUG", url.toString());
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                int code = connection.getResponseCode();
                if (code == 200) {
                    String jsonString = DataReceiver.ChangeInputStream(connection
                            .getInputStream());
                    Log.v("result", jsonString);
                    result = (ArrayList<CommentBean>) JsonHelper.getComments(jsonString);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    /**
     * The specific thread to get image
     *
     * @author Brandon
     * @version 1.0 2015-12-07
     */
    public class GetImageAsyncTask extends AsyncTask<String, Integer, byte[]> {


        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(byte[] result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progress.dismiss();
            if (result == null) {
                Toast.makeText(CommentActivity.this, "Network Error", Toast.LENGTH_LONG);
                return;
            }
            byte[] data = result;
            int length = data.length;

            Bitmap bitMap = BitmapFactory.decodeByteArray(data, 0, length);
            showImageDialog(bitMap);
        }


        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }


        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected byte[] doInBackground(String... arg0) {
            byte[] result = null;
            InputStream in = null;

            try {
                URL url = new URL(arg0[0] + "Comment?action=download&path=" + arg0[1]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                int code = connection.getResponseCode();
                if (code == 200) {
                    in = connection.getInputStream();
                    result = readStream(in);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }


        /**
         * Read Image Stream
         *
         * @param in
         * @return
         * @throws Exception
         */
        public byte[] readStream(InputStream in) throws Exception {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            in.close();
            return outputStream.toByteArray();
        }


    }
}
