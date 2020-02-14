//package com.example.androidphotos;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//import model.Album;
//import model.Photo;
//import model.Tag;
//import model.UserData;
//
//public class SearchController extends AppCompatActivity {
//
//    private static UserData uData = new UserData();
//
//    Switch nTags;
//    Switch andOr;
//
//    Spinner tagKey1;
//    Spinner tagKey2;
//
//    EditText tagValue1;
//    EditText tagValue2;
//
//    TextView orText;
//    TextView andText;
//
//    private int tagsQ = 1;
//
//    protected void onCreate(final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.search_view);
//
//        nTags = findViewById(R.id.switch_nTags);
//        andOr = findViewById(R.id.switch_andOr);
//
//        tagKey1 = findViewById(R.id.spinner_tagKey1);
//        tagKey2 = findViewById(R.id.spinner_tagKey2);
//
//        tagValue1 = findViewById(R.id.text_tagValue1);
//        tagValue2 = findViewById(R.id.text_tagValue2);
//
//        orText = findViewById(R.id.text_orText);
//        andText = findViewById(R.id.text_andText);
//
//        ArrayList<String> tagKeys = new ArrayList<String>();
//        tagKeys.add("Person");
//        tagKeys.add("Location");
//        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tagKeys);
//
//        tagKey1.setAdapter(spinner_adapter);
//        tagKey2.setAdapter(spinner_adapter);
//
//
//        tagValue1.setText("");
//        tagValue2.setText("");
//        setStateOne();
//
//
//        nTags.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    tagsQ = 2;
//                    setStateTwo();
//                }else{
//                    tagsQ = 1;
//                    setStateOne();
//                }
//            }
//        });
//
//    }
//
//    public void leaveSearch(View view){
//        Intent i = new Intent(this, Main.class);
//        startActivity(i);
//    }
//
//
//    public void setStateOne(){
//        tagKey2.setVisibility(View.INVISIBLE);
//        tagValue2.setVisibility(View.INVISIBLE);
//        andOr.setVisibility(View.INVISIBLE);
//        orText.setVisibility(View.INVISIBLE);
//        andText.setVisibility(View.INVISIBLE);
//    }
//
//    public void setStateTwo(){
//        tagKey2.setVisibility(View.VISIBLE);
//        tagValue2.setVisibility(View.VISIBLE);
//        andOr.setVisibility(View.VISIBLE);
//        orText.setVisibility(View.VISIBLE);
//        andText.setVisibility(View.VISIBLE);
//    }
//
//
//    public void Search(View view){
//        if(tagsQ == 1 && tagValue1.getText().toString().equals("")){
//            showToast("Please do not leave values blank!");
//            return;
//        }
//
//        if(tagsQ == 2 && (tagValue1.getText().toString().equals("") || tagValue2.getText().toString().equals(""))) {
//            showToast("Please do not leave values blank!");
//            return;
//        }
//        if(tagsQ == 1){
//            SearchForOneTag();
//        }else{
//            SearchForTwoTags();
//        }
//    }
//
//    private void SearchForOneTag(){
//        String key = tagKey1.getSelectedItem().toString();
//        String value = tagValue1.getText().toString();
//
//        Album searchAlbum = new Album("Searched: " + key + " : " + value);
//
//        for(Album a : uData.getUser().getAlbums()){
//            for(Photo p : a.getPhotos()){
//                for(Tag t : p.getTags()){
//                    if(t.getKey().toLowerCase().equals(key.toLowerCase()) && t.getValue().toLowerCase().contains(value.toLowerCase())){
//                        searchAlbum.getPhotos().add(deepCopy(p));
//                    }
//                }
//            }
//        }
//
//        if(searchAlbum.getPhotos().size() == 0){
//            showToast("There are no matches found.");
//            return;
//        }
//
//        uData.setActiveAlbum(searchAlbum);
//
//        Intent i = new Intent(this, AlbumController.class);
//        startActivity(i);
//    }
//
//    private void SearchForTwoTags(){
//
//        String key1 = tagKey1.getSelectedItem().toString();
//        String value1 = tagValue1.getText().toString();
//
//        String key2 = tagKey2.getSelectedItem().toString();
//        String value2 = tagValue2.getText().toString();
//
//        Album search1 = new Album("");
//        Album search2 = new Album("");
//
//        for(Album a : uData.getUser().getAlbums()){
//            for(Photo p : a.getPhotos()){
//                for(Tag t : p.getTags()){
//                    //showToast(key2 + " vs " + t.getKey() + "     " + value2 + " vs " + t.getValue());
//                    if(t.getKey().toLowerCase().equals(key1.toLowerCase()) && t.getValue().toLowerCase().contains(value1.toLowerCase())){
//                        search1.getPhotos().add(p);
//                    }
//
//                    if(t.getKey().toLowerCase().equals(key2.toLowerCase()) && t.getValue().toLowerCase().contains(value2.toLowerCase())){
//                        search2.getPhotos().add(p);
//                    }
//                }
//            }
//        }
//
//        Album searchAlbum = new Album("Searched: " + key1 + " : " + value1 + "," + key2 + " : " + value2);
//        if(andOr.isChecked()){ // And
//            for(int i = 0; i < search1.getPhotos().size(); i++){
//                if(search2.getPhotos().contains(search1.getPhotos().get(i))){
//                    searchAlbum.getPhotos().add(deepCopy(search1.getPhotos().get(i)));
//                }
//            }
//        }else{ // Or
//
//            for(Photo ph : search1.getPhotos()){
//                if(!search2.getPhotos().contains(ph)){
//                    search2.getPhotos().add(ph);
//                }
//            }
//
//            for (Photo ph : search2.getPhotos()){
//                searchAlbum.getPhotos().add(deepCopy(ph));
//            }
//
//        }
//
//
//        if(searchAlbum.getPhotos().size() == 0){
//            showToast("There are no matches found.");
//            return;
//        }
//
//        uData.setActiveAlbum(searchAlbum);
//
//        Intent i = new Intent(this, AlbumController.class);
//        startActivity(i);
//
//    }
//
//    public void showToast(String text){
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//    }
//
//    public Photo deepCopy(Photo ph){
//        Photo photo = new Photo(ph.getBitmap());
//        photo.getTags().addAll(ph.getTags());
//        return photo;
//    }
//}

package com.example.androidphotos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Album;
import model.Photo;
import model.Tag;
import model.UserData;

public class SearchController extends AppCompatActivity {

    private static UserData uData = new UserData();

    Switch tags;
    Switch andOr;

    Spinner tagKey1;
    Spinner tagKey2;

    EditText tagVal1;
    EditText tagVal2;

    TextView orText;
    TextView andText;

    private int tagsQ = 1;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);

        tags = findViewById(R.id.switchTag);
        andOr = findViewById(R.id.switchAndOr);

        tagKey1 = findViewById(R.id.spinnerTagKey1);
        tagKey2 = findViewById(R.id.spinnerTagKey2);

        tagVal1 = findViewById(R.id.textTagValue1);
        tagVal2 = findViewById(R.id.textTagValue2);

        orText = findViewById(R.id.orText);
        andText = findViewById(R.id.andText);

        ArrayList<String> tagKeys = new ArrayList<String>();
        tagKeys.add("Person");
        tagKeys.add("Location");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tagKeys);

        tagKey1.setAdapter(spinnerAdapter);
        tagKey2.setAdapter(spinnerAdapter);


        tagVal1.setText("");
        tagVal2.setText("");
        setStateOne();


        tags.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tagsQ = 2;
                    setStateTwo();
                } else {
                    tagsQ = 1;
                    setStateOne();
                }
            }
        });

    }

    public void Search(View view) {
        if (tagsQ == 1 && tagVal1.getText().toString().equals("")) {
            showToast("Please do not leave values blank!");
            return;
        }

        if (tagsQ == 2 && (tagVal1.getText().toString().equals("") || tagVal2.getText().toString().equals(""))) {
            showToast("Please do not leave values blank!");
            return;
        }
        if (tagsQ == 1) {
            SearchForOneTag();
        } else {
            SearchForTwoTags();
        }
    }

    public void leaveSearch(View view) {
        Intent i = new Intent(this, Main.class);
        startActivity(i);
    }

    private void SearchForOneTag() {
        String key = tagKey1.getSelectedItem().toString();
        String val = tagVal1.getText().toString();

        Album searchAlbum = new Album("Searched: " + key + " : " + val);

        for (Album a : uData.getUser().getAlbums()) {
            for (Photo p : a.getPhotos()) {
                for (Tag t : p.getTags()) {
                    if (t.getKey().toLowerCase().equals(key.toLowerCase()) && t.getValue().toLowerCase().contains(val.toLowerCase())) {
                        searchAlbum.getPhotos().add(deepCopy(p));
                    }
                }
            }
        }

        if (searchAlbum.getPhotos().size() == 0) {
            showToast("There are no matches found.");
            return;
        }

        uData.setActiveAlbum(searchAlbum);

        Intent i = new Intent(this, AlbumController.class);
        startActivity(i);
    }

    private void SearchForTwoTags() {

        String key1 = tagKey1.getSelectedItem().toString();
        String val1 = tagVal1.getText().toString();

        String key2 = tagKey2.getSelectedItem().toString();
        String val2 = tagVal2.getText().toString();

        Album search1 = new Album("");
        Album search2 = new Album("");

        for (Album a : uData.getUser().getAlbums()) {
            for (Photo p : a.getPhotos()) {
                for (Tag t : p.getTags()) {
                    //showToast(key2 + " vs " + t.getKey() + "     " + value2 + " vs " + t.getValue());
                    if (t.getKey().toLowerCase().equals(key1.toLowerCase()) && t.getValue().toLowerCase().contains(val1.toLowerCase())) {
                        search1.getPhotos().add(p);
                    }

                    if (t.getKey().toLowerCase().equals(key2.toLowerCase()) && t.getValue().toLowerCase().contains(val2.toLowerCase())) {
                        search2.getPhotos().add(p);
                    }
                }
            }
        }

        Album searchAlbum = new Album("Searched: " + key1 + " : " + val1 + "," + key2 + " : " + val2);
        if (andOr.isChecked()) { // And
            for (int i = 0; i < search1.getPhotos().size(); i++) {
                if (search2.getPhotos().contains(search1.getPhotos().get(i))) {
                    searchAlbum.getPhotos().add(deepCopy(search1.getPhotos().get(i)));
                }
            }
        } else { // Or

            for (Photo ph : search1.getPhotos()) {
                if (!search2.getPhotos().contains(ph)) {
                    search2.getPhotos().add(ph);
                }
            }

            for (Photo ph : search2.getPhotos()) {
                searchAlbum.getPhotos().add(deepCopy(ph));
            }

        }


        if (searchAlbum.getPhotos().size() == 0) {
            showToast("There are no matches found.");
            return;
        }

        uData.setActiveAlbum(searchAlbum);

        Intent i = new Intent(this, AlbumController.class);
        startActivity(i);

    }

    public void setStateOne() {
        tagKey2.setVisibility(View.INVISIBLE);
        tagVal2.setVisibility(View.INVISIBLE);
        andOr.setVisibility(View.INVISIBLE);
        orText.setVisibility(View.INVISIBLE);
        andText.setVisibility(View.INVISIBLE);
    }

    public void setStateTwo() {
        tagKey2.setVisibility(View.VISIBLE);
        tagVal2.setVisibility(View.VISIBLE);
        andOr.setVisibility(View.VISIBLE);
        orText.setVisibility(View.VISIBLE);
        andText.setVisibility(View.VISIBLE);
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public Photo deepCopy(Photo ph) {
        Photo photo = new Photo(ph.getBitmap());
        photo.getTags().addAll(ph.getTags());
        return photo;
    }
}
