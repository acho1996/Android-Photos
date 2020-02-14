package com.example.androidphotos;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import model.Album;
import model.Photo;
import model.Tag;
import model.User;
import model.UserData;

public class PhotoController extends AppCompatActivity {

    UserData userData = new UserData();
    static User USER;
    Album CurrentAlbum;
    Photo CurrentPhoto;

    ListView tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.image_view);
            tag = findViewById(R.id.listTags);



            USER = userData.getUser();
        CurrentAlbum = userData.getActiveAlbum();
        CurrentPhoto = userData.getActivePhoto();

            setTitle(CurrentAlbum.getName());

            ImageView img_view = findViewById(R.id.imageDisplay);

            img_view.setImageBitmap(CurrentPhoto.getBitmap());


            ArrayList<String> tagStrings = new ArrayList<String>();
            for(Tag t: CurrentPhoto.getTags()){
                tagStrings.add(t.toString());
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tagStrings);
            tag.setAdapter(adapter);

    }

    public void deletePhoto(View view){

        new AlertDialog.Builder(this)
                .setTitle("Delete?")
                .setMessage("You really want to delete this photo?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        CurrentAlbum.getPhotos().remove(CurrentPhoto);
                        gotoAlbumActivity(null);
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void editTags(View view){
        Intent i = new Intent(this, TagController.class);
        startActivity(i);
    }
    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void onStop(){
        super.onStop();
        userData.saveUserData(userData.getUser());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            gotoAlbumActivity(null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void gotoAlbumActivity(View view){
        Intent gotoAlbumActivityIntent = new Intent(this, AlbumController.class);
        startActivity(gotoAlbumActivityIntent);
    }

    public void movePhoto(View view){
        if(userData.getUser().getAlbums().size() < 2){
            showToast("Sorry, There are no albums to move to!");
            return;
        }
        gotoMovePhoto();
    }

    public void gotoMovePhoto(){
        Intent x = new Intent(this, MoveController.class);
        startActivity(x);
    }

}
