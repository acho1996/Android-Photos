package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.io.File;
import java.io.Serializable;
//import java.net.URI;
import java.util.Calendar;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 * Class of a photo
 *
 */
public class Photo implements Serializable{

    /**
     * Path of image file
     */
    private String Paths;

    /**
     * List of tags
     */
    private ArrayList<Tag> Tag = new ArrayList<Tag>();

    /**
     * last modified date of photo
     */
    private Calendar Cal = Calendar.getInstance();

    /**
     * Caption for photo
     */
    private String Captions = "";

    private byte[] BitmapBytes;

    public Photo(Bitmap bitMap)
    {
        this.BitmapBytes = BitmapToBytes(bitMap);
    }

    /**
     * @param month sets month
     * @param day sets day
     * @param year sets year
     */
    public void setDate(int month, int day, int year)
    {
        this.Cal.clear();
        this.Cal.set(year, month-1, day);
        this.Cal.set(Calendar.MILLISECOND, 0);
    }

    public File getFile()
    {
        File file = new File(this.Paths.toString());
        return file;
    }

    public Bitmap getBitmap()
    {
        return BitmapFactory.decodeByteArray(this.BitmapBytes, 0, this.BitmapBytes.length);
    }

    public ArrayList<String> tagsAsStrings()
    {
        ArrayList<String> list = new ArrayList<String>();

        for(Tag t: Tag){
            list.add(t.toString());
        }
        return list;
    }

    private byte[] BitmapToBytes(Bitmap bitmap)
    {
        ByteArrayOutputStream Stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, Stream);
        byte[] byteArray = Stream.toByteArray();
        bitmap.recycle();

        return byteArray;
    }

    /**
     * @param uri path path of image
     */
    public Photo(String uri)
    {
        this.Paths = uri;
    }

    /**
     * @return date in MM/dd/yyyy format
     */
    public String getDate()
    {
        int Month = this.Cal.get(Calendar.MONTH) + 1;
        int Day = this.Cal.get(Calendar.DATE);
        int Year = this.Cal.get(Calendar.YEAR);
        return Month + "/" + Day + "/" + Year;
    }

    /**
     * @return Calendar of photo
     */
    public Calendar getCalendar()
    {
        return this.Cal;
    }

    /**
     * @return Caption of current photo
     */
    public String getCaption()
    {
        return this.Captions;
    }

    /**
     * @param caption sets string as caption
     */
    public void setCaption(String caption)
    {
        this.Captions = caption;
    }

    /**
     * Retrieves path of this photo's image file
     * @return file path
     */
    public String getUri()
    {
        return Paths;
    }

    /**
     * @return ArrayList of tags of photo
     */
    public ArrayList<Tag> getTags()
    {
        return this.Tag;
    }
}
