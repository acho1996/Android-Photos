package com.example.androidphotos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import model.Album;
import model.Photo;
import model.Tag;
import model.UserData;

public class MoveController extends AppCompatActivity {

    static UserData userData = new UserData();
    ListView Album;
    ArrayList<String> AlbumNames;
    Album location;

    public Album albumWithName(String name){
        for(Album a : userData.getUser().getAlbums()){
            if(a.getName().equals(name)){
                return a;
            }
        }
        return null;
    }

    protected void onStop(){
        super.onStop();
        userData.saveUserData(userData.getUser());
    }

    public void backFromMove(View view){
        gotoPhotoView();
    }

    public void gotoPhotoView(){
        Intent x = new Intent(this, PhotoController.class);
        startActivity(x);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            gotoPhotoView();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public Photo deepCopy(Photo ph){
        Photo photos = new Photo(ph.getBitmap());
        photos.getTags().addAll(ph.getTags());
        return photos;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        Album = findViewById(R.id.listMoveAlbums);


        AlbumNames = new ArrayList<String>();
        for(Album a : userData.getUser().getAlbums()){
            if(a != userData.getActiveAlbum()){
                AlbumNames.add(a.getName());
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AlbumNames);
        Album.setAdapter(adapter);


        Album.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                location = albumWithName(AlbumNames.get(position));
                new AlertDialog.Builder(MoveController.this)
                        .setTitle("Move? or Copy?")
                        .setMessage("Do you want to delete the photo from the source album?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Photo photo = deepCopy(userData.getActivePhoto());

                                userData.getActiveAlbum().getPhotos().remove(userData.getActivePhoto());
                                location.getPhotos().add(photo);
                                Intent i = new Intent(MoveController.this, Main.class);
                                startActivity(i);

                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Photo photo = deepCopy(userData.getActivePhoto());
                                location.getPhotos().add(photo);
                                Intent i = new Intent(MoveController.this, Main.class);
                                startActivity(i);
                            }}).show();
            }
        });
    }

}
