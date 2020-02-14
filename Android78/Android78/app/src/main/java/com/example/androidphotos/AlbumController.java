package com.example.androidphotos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import model.Album;
import model.Photo;
import model.User;
import model.UserData;

/**
 *  @author Kanak Borad
 *  @author Anthony Cho
 *  */

public class AlbumController extends AppCompatActivity {

    UserData userData = new UserData();
    User users;
    Album AlbumActive;
    ListView ThumbNails;
    Button AddingImage;
    View ViewLayout;

    public static final int SELECT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {     //saves changes

        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_view);

        AlbumActive = userData.getActiveAlbum();

        users = userData.getUser();
        setTitle(AlbumActive.getName());

        ThumbNails = findViewById(R.id.inAlbumThumbnails);
        ViewLayout = getLayoutInflater().inflate(R.layout.album_view, null);
        AddingImage = ViewLayout.findViewById(R.id.button_addPhoto);
        ThumbNails.setAdapter(new ThumbnailAdapater());

        ThumbNails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Photo toView = userData.getActiveAlbum().getPhotos().get(position);
                userData.setActivePhoto(toView);
                gotoPhotoActivity(position);
            }
        });


    }


    public void gotoMainActivity(){
        Intent x = new Intent(this, Main.class);
        startActivity(x);
    }

    public void backHome(View view)     //back button
    {
        gotoMainActivity();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            gotoMainActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void gotoPhotoActivity(int index)
    {
        try
        {
            Intent gotoPhotoActivityIntent = new Intent(this, PhotoController.class);

            startActivityForResult(gotoPhotoActivityIntent, 1001);
        }
        catch (Exception e)
        {
           showToast(e.getLocalizedMessage());
        }
    }

    public void refreshAlbum()  //album refresh
    {
        ThumbNails.setAdapter(new ThumbnailAdapater());
    }

    public void showFileChooser(View view)
    {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Please select Image to Import");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, SELECT_IMAGE);
    }


    protected void onStop()
    {
        super.onStop();
        userData.saveUserData(userData.getUser());
    }

    public void gotoSlideShow(View view)
    {
        if(AlbumActive.getPhotos().size() == 0)
        {
            showToast("There are no photos to view!");
            return;
        }
        Intent x = new Intent(this, DisplayController.class);
        startActivity(x);
    }


    @Override
    protected void onActivityResult(int requestCode, int result, Intent data){


        if(requestCode == 1001)
        {

        }
        else
            {
            if(data != null)
            {
                Uri URI = data.getData();
                Bitmap bitmap;
                try
                {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), URI);
                }
                catch (Exception e)
                {
                    showToast("We are unable to import local file.");
                    return;
                }

                Photo photos = new Photo(bitmap);
                AlbumActive.getPhotos().add(photos);
                refreshAlbum();
            }
        }

    }

    public void showToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    class ThumbnailAdapater extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return AlbumActive.getPhotos().size();
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            View view = getLayoutInflater().inflate(R.layout.album_list_view, null);
            TextView view_text = view.findViewById(R.id.albumName);
            ImageView view_image = view.findViewById(R.id.albumPreview);
            view_image.setImageBitmap(AlbumActive.getPhotos().get(position).getBitmap());
            view_text.setText("");

            return view;
        }
    }

    public void editAlbumName(View view)
    {
        Intent intent = new Intent();
    }
}
