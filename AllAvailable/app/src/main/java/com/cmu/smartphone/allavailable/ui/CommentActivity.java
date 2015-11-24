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
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
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
import com.cmu.smartphone.allavailable.ws.remote.ImageUploader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private static final int ASK_PHOTOS = 1;

    private EditText editComment;
    private ImageButton cameraPhoto;
    private ListView listView;
    private Button createComment;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        editComment = (EditText) findViewById(R.id.edit_post);
        cameraPhoto = (ImageButton) findViewById(R.id.button_camera);
        createComment = (Button) findViewById(R.id.post_button);
        listView = (ListView) findViewById(R.id.comment_list);

        List<CommentBean> postList = new ArrayList<CommentBean>();
        // The fake data, need to be removed later
        CommentBean comment1 = new CommentBean();
        comment1.setCommentId(1);
        comment1.setContent("The room is enough for 6 people.");
        comment1.setDate("11/11/2015");
        comment1.setTime("10:56");
        comment1.setUserId("xiw1@andrew.cmu.edu");
        postList.add(comment1);

        CommentBean comment2 = new CommentBean();
        comment2.setCommentId(2);
        comment2.setContent("My friends and I always stay at this room, the environment is good!");
        comment2.setDate("11/05/2015");
        comment2.setTime("20:00");
        comment2.setUserId("simba@andrew.cmu.edu");
        comment2.setImagePath("imagePath");
        postList.add(comment2);

        CommentListAdapter adapter = new CommentListAdapter(this, postList);

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

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showImageDialog("file:///storage/emulated/0/Pictures/AllAvailable/IMG_20151123_200656.jpg");
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
                String value = data.getStringExtra(ImageUploader.RESULT_TYPE);
                uri = Uri.parse(value);
                Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showImageDialog(String uri) {
        // Get screen size
        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        // Get target image size
        ContentResolver cr = this.getContentResolver();
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(uri)));

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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
