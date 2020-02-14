package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 * Class of a User of the application
 *
 */
public class User implements Serializable {

    /**
     * List of this user's albums
     */
    public ArrayList<Album> albums = new ArrayList<Album>();

    /**
     * List of this user's tag types (tag keys)
     */
    private ArrayList<String> tagTypes = new ArrayList<String>();

    /**
     * User constructor
     */
    public User() {
        this.tagTypes.add("Person");
        this.tagTypes.add("Location");
    }

    /**
     * @return gets list of Albums
     */
    public ArrayList<String> getTagTypes(){
        return this.tagTypes;
    }

    /**
     * Retrieves albums of this user
     * @return ArrayList of this user's albums
     */
    public ArrayList<Album> getAlbums(){
        return this.albums;
    }

    /**
     * @return Gets list of tag
     */
    public void addTagType(String type) {
        this.tagTypes.add(type);
    }
}