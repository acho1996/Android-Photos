package com.example.androidphotos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import model.UserData;

public class DisplayController extends AppCompatActivity {

    private static UserData userData = new UserData();
    private int photo_index = 0;

    ImageView DisplayPhoto;
    View ViewLayout;

    Button PrevButton;
    Button NextButton;

    Bitmap[] bitmaps = new Bitmap[userData.getActiveAlbum().getPhotos().size()];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.diplay);

        ViewLayout = getLayoutInflater().inflate(R.layout.diplay, null);

        DisplayPhoto = findViewById(R.id.slideshowDisplay);

        PrevButton = findViewById(R.id.buttonSlideshowPrevious);
        NextButton = findViewById(R.id.buttonSlideshowNext);

        for (int i = 0; i < bitmaps.length; i++){
            bitmaps[i] = userData.getActiveAlbum().getPhotos().get(i).getBitmap();
        }

        checkButtonState();

    }

    public void checkButtonState(){
        try {
            DisplayPhoto.setImageBitmap(bitmaps[photo_index]);
        }catch (Exception e){
            e.printStackTrace();
        }

        int nPhotos = userData.getActiveAlbum().getPhotos().size();
        PrevButton.setVisibility(View.VISIBLE);
        NextButton.setVisibility(View.VISIBLE);

        if(photo_index == 0){
            PrevButton.setVisibility(View.INVISIBLE);
        }
        if(photo_index == nPhotos - 1){
            NextButton.setVisibility(View.INVISIBLE);
        }
    }

    public void backFromSlideshow(View view){
        Intent i = new Intent(this, AlbumController.class);
        startActivity(i);
    }

    protected void onStop(){
        super.onStop();
        userData.saveUserData(userData.getUser());
    }

    public void nextPhoto(View view){
        photo_index++;
        checkButtonState();
    }

    public void prevPhoto(View view){
        photo_index--;
        checkButtonState();
    }
}
