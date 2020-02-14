package com.example.androidphotos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Tag;
import model.UserData;

public class TagController extends AppCompatActivity {


    static UserData userData = new UserData();
    ListView Tag;
    private int TagIndex = -1;
    private String tagKey = "";
    private String tagValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.tag_view);

            Tag = findViewById(R.id.listTagsFull);
            refreshList();

        }catch (Exception e){
            e.printStackTrace();
        }


        Tag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TagIndex = position;
                tagSelectOptions();
            }
        });
    }

    protected void onStop(){
        super.onStop();
        userData.saveUserData(userData.getUser());
    }

    public void confirmTagDeletion(){
        new AlertDialog.Builder(this)
                .setTitle("Delete?")
                .setMessage("Do you really want to delete this tag?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        userData.getActivePhoto().getTags().remove(TagIndex);
                        refreshList();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void tagSelectOptions(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select Action");
        String[] types = {"Edit Tag", "Delete Tag"};
        b.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch(which){
                    case 0:
                        editTag(TagIndex);
                        break;
                    case 1:
                        confirmTagDeletion();
                        break;
                }
            }

        });
        b.show();
    }


    public void goBack(View view){
        Intent i = new Intent(this, PhotoController.class);
        startActivity(i);
    }


    public void editTag(final int index){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select new Tag Type");
        String[] types = {"Person", "Location"};
        b.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch(which){
                    case 0:
                        tagKey = "Person";
                        break;
                    case 1:
                        tagKey = "Location";
                        break;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(TagController.this);
                builder.setTitle("Enter the new value for the tag");

                final EditText input = new EditText(TagController.this);
                input.setText(userData.getActivePhoto().getTags().get(index).getValue());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tagValue = input.getText().toString();
                        if(tagExists(tagKey, tagValue)){
                            showToast("Tag with this value already exists!");
                            return;
                        }
                        userData.getActivePhoto().getTags().set(index, new Tag(tagKey, tagValue));
                        refreshList();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return;
            }

        });
        b.show();
    }

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void addTag(View view){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select Tag Type");
        String[] types = {"Person", "Location"};
        b.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch(which){
                    case 0:
                        tagKey = "Person";
                        break;
                    case 1:
                        tagKey = "Location";
                        break;
                }
                promptInput();
            }

        });
        b.show();
    }


    public void promptInput(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the value for the tag");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tagValue = input.getText().toString();
                if(tagExists(tagKey, tagValue)){
                    showToast("Tag with this value already exists!");
                    return;
                }
                Tag tag = new Tag(tagKey, tagValue);
                userData.getActivePhoto().getTags().add(tag);
                refreshList();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        return;
    }


    public void refreshList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userData.getActivePhoto().tagsAsStrings());
        Tag.setAdapter(adapter);
    }


    public boolean tagExists(String key, String value){
        for(Tag t : userData.getActivePhoto().getTags()){
            if(t.getKey().equals(key) && t.getValue().equals(value)){
                return true;
            }
        }
        return false;
    }


}
