package model;

import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 *  @author Kanak Borad
 *  @author Anthony Cho
 *  Class representation of a photo album
 *
 */
public class Album implements Comparable<Album>, Serializable{
    /**
     * Name of album
     */
    private String name;

    /**
     * List of photos contained in the album
     */
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    /**
     * @param  name of the album
     */
    public void setName(String name)
    {
        this.name = name;   //sets album name
    }

    /*
     * @return gets name of the album
     */
    public String getName()
    {
        return this.name;   //get album name
    }

    /**
     * Album Constructor
     * @param name Name to assign to the album
     */
    public Album(String name) {

        this.name = name;   //assigns name to string
    }

    /**
     * @param calendar
     * @return string in MM/dd/yyyy format
     */
    private String getDateString(Calendar calendar)
    {
        int Month = calendar.get(Calendar.MONTH)+1;
        int Day = calendar.get(Calendar.DATE);
        int Year = calendar.get(Calendar.YEAR);

        return Month + "/" + Day + "/" + Year;
    }

    /**
     * @param a compares to other album
     * @return 0 as default
     */
    public int compareTo(Album a)
    {
        return 0;   //compares albums
    }

    /**
     * @return list of photos in album
     */
    public ArrayList<Photo> getPhotos()
    {
        return this.photos; //gets photos
    }

    /**
     * @param  photo removes photo from this album
     */
    public void removePhoto(Photo photo)
    {
        this.photos.remove(photo);
    }

    /**
     * @return the album as a string.
     */
    public String toString() {
        int numOfPhotos = photos.size();
        if(numOfPhotos == 0)
        {
            return this.name;
        }

        SimpleDateFormat Date = new SimpleDateFormat("MM/dd/yyyy");

        Calendar Recent = Calendar.getInstance();
        Recent.clear();
        try {
            Recent.setTime(Date.parse("12/31/9999"));
        }
        catch (ParseException e2)
        {
            e2.printStackTrace();
        }
        Recent.set(Calendar.MILLISECOND, 0);
        Calendar MostRecent = Calendar.getInstance();
        MostRecent.clear();

        try
        {
            MostRecent.setTime(Date.parse("12/31/1000"));
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }
        MostRecent.set(Calendar.MILLISECOND, 0);


        Date PicDate = new Date();
        Calendar CalendarCheck = Calendar.getInstance();
        CalendarCheck.clear();

        for(int i = 0; i < numOfPhotos; i++)
        {
            Photo p = photos.get(i);

            try
            {
                PicDate = Date.parse(p.getDate());
            } catch(Exception e){
                return this.name + "  ||  " + "Photos: " + numOfPhotos + "    " + "Date Range: " + " unknown ";
            }
            CalendarCheck.setTime(PicDate);
            CalendarCheck.set(Calendar.MILLISECOND, 0);

            if(CalendarCheck.before(Recent))
            {
                Recent.setTime(CalendarCheck.getTime());
            }

            if(CalendarCheck.after(MostRecent))
            {
                MostRecent.setTime(CalendarCheck.getTime());
            }
        }

        String date1 = getDateString(Recent);
        String date2 = getDateString(MostRecent);
        return this.name + "  ||  " + "Photos: " + numOfPhotos + "    " + "Date Range: " + date1 + " - " + date2;
    }
}
