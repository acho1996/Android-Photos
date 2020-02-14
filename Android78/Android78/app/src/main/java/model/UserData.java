package model;

//import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//import com.example.androidphotos.MainActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
//import java.text.SimpleDateFormat;



/**
 * @author Kanak Borad
 * @author Anthony Cho
 * Class for generating, saving, and retrieving user data
 *
 */
public class UserData {

    public static Album activeAlbum;

    public static User user;

    public static Photo activePhoto;

    private static String DATA_PATH;

    public User getUser()
    {
        return user;
    }


    public void setDataPath(String Datapath)
    {
        this.DATA_PATH = Datapath;
    }

    public Photo getActivePhoto()
    {
        return activePhoto;
    }

    public String getDataPath()
    {
        return this.DATA_PATH;
    }

    public Album getActiveAlbum()
    {
        return activeAlbum;
    }

    public void setUser(User u)
    {
        user = u;
    }

    public void setActiveAlbum(Album a)
    {
        activeAlbum = a;
    }

    public void setActivePhoto(Photo p)
    {
        activePhoto = p;
    }

    /**
     * @param u User to serialize
     */
    public void saveUserData(User u)
    {

        File file = new File(DATA_PATH + "/AndroidPhotos.data");

        try {
            FileOutputStream FileOS = new FileOutputStream(file);
            ObjectOutputStream ObjectOS = new ObjectOutputStream(FileOS);
            ObjectOS.writeObject(u);
            ObjectOS.close();
        }catch (Exception e){
            System.out.println("ERROR" + e.getLocalizedMessage());

            System.exit(0);
        }

    }

    /**
     * username attempt to load user data
     */
    public User loadUserData()
    {
        File file = new File(DATA_PATH + "/AndroidPhotos.data");
        User user = new User();
        if(!file.exists())
        {
            return null;
        }
        try {
            FileInputStream FileIS = new FileInputStream(file);
            ObjectInputStream ObjectIS = new ObjectInputStream(FileIS);
            user = (User) ObjectIS.readObject();
            ObjectIS.close();
        }catch (Exception e){
            System.out.println("ERROR" + e.getLocalizedMessage());
            System.exit(0);
        }

        setUser(user);
        return user;
    }


    public Bitmap getImageBitmap(String url)
    {
        Bitmap bm = null;
        try
        {
            URL URL = new URL(url);
            URLConnection connection = URL.openConnection();
            connection.connect();
            InputStream InputStream = connection.getInputStream();
            BufferedInputStream Buffer = new BufferedInputStream(InputStream);
            bm = BitmapFactory.decodeStream(Buffer);
            Buffer.close();
            InputStream.close();

        }
        catch (IOException e)
        {
            System.out.println("ERERERERRER:" + e.getLocalizedMessage());
        }
        return bm;
    }

    public int numberOfPhotos()
    {
        int totalPhotos = 0;

        for(Album a : getUser().getAlbums()){
            for(Photo p : a.getPhotos()){
                totalPhotos ++;
            }
        }
        return totalPhotos;
    }
}
