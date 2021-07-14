package com.example.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button wallpaper;
    ImageView imageView;
    ImageButton capture;
    Intent intent;
    final static int cameraData = 0;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        InputStream is = getResources().openRawResource(R.raw.bm);
        bm = BitmapFactory.decodeStream(is);


    }

    private void initialize() {
        wallpaper = (Button) findViewById(R.id.btSetWP);
        imageView = (ImageView) findViewById(R.id.pic);
        capture = (ImageButton) findViewById(R.id.btCapture);

        capture.setOnClickListener(this);
        wallpaper.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btSetWP:

                try {
                    getApplicationContext().setWallpaper(bm);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.btCapture:

                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, cameraData);

                break;
        }

    }
    // handling data from camera

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            bm = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bm);


        }
    }

/*

// needs to be tested (should be handles rotation of screen)
   @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt("cameraData",cameraData);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        cameraData = savedInstanceState.getInt("cameraData");
    }
    */
}
