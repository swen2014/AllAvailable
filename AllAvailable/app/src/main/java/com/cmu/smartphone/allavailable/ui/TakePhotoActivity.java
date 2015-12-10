package com.cmu.smartphone.allavailable.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.exception.NoImageChosenException;
import com.cmu.smartphone.allavailable.ws.remote.ImageUploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePhotoActivity extends AppCompatActivity {

    private static final int PHOTO_REQUEST_GALLERY = 100;
    private static final int PHOTO_REQUEST_CAMERA = 200;
    private static final int MEDIA_TYPE_IMAGE = 1;

    private Button takePhoto;
    private Button galleyPhoto;
    private Button done;
    private ImageView image;
    private Bitmap bitmap;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        takePhoto = (Button) findViewById(R.id.new_photo_button);
        galleyPhoto = (Button) findViewById(R.id.gallery_button);
        done = (Button) findViewById(R.id.done_button);

        image = (ImageView) findViewById(R.id.temp_image);

        galleyPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    photoUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent,
                            PHOTO_REQUEST_CAMERA);
                } else {
                    Toast.makeText(TakePhotoActivity.this, "Cannot find external storage to store!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (bitmap == null) {
                        throw new NoImageChosenException("No image needs to be uploaded!");
                    }
                    Intent resultIntent = new Intent();
                    Log.v("Debug Image", photoUri + "");
                    resultIntent.putExtra(ImageUploader.RESULT_TYPE, photoUri.toString());
//                    resultIntent.putExtra(ImageUploader.RESULT_TYPE, bitmap);
                    TakePhotoActivity.this.setResult(Activity.RESULT_OK, resultIntent);
                    TakePhotoActivity.this.finish();
                } catch (NoImageChosenException nice) {
                    nice.fix(TakePhotoActivity.this);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ContentResolver cr = this.getContentResolver();
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    photoUri = uri;
                    bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    if (bitmap == null) {
                        // Possible Exception
                        throw new NoImageChosenException("Choose Image Failed!");
                    }
                    image.setImageBitmap(bitmap);
                    storeImage(bitmap);
                } catch (FileNotFoundException fe) {
                    fe.printStackTrace();
                } catch (NoImageChosenException nice) {
                    nice.fix(this);
                }
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                try {
                    bitmap = BitmapFactory.decodeStream(cr.openInputStream(photoUri));
                    if (bitmap == null) {
                        // Possible Exception
                        Toast.makeText(this, "Take Photo Failed!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    image.setImageBitmap(bitmap);
                } catch (FileNotFoundException fe) {
                    fe.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }

    /**
     * Create a file Uri for saving an image
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "AllAvailable");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("AllAvailable", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    public boolean storeImage(Bitmap bitmap) {
        try {
//            File folder = new File(Environment.getExternalStorageDirectory() + "/Icon Select/");
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//            File nomediaFile = new File(folder, ".nomedia");
//            if (!nomediaFile.exists()) {
//                nomediaFile.createNewFile();
//            }

            File file = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            photoUri = Uri.fromFile(file);

            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            File bitmapFile = new File(file);

            if (file.exists()) {
                return true;
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("beewhale", "Error writing data");
        }

        return false;
    }
}
