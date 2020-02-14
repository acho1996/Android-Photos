package model;

import java.io.Serializable;


/**
 * @author Kanak Borad
 * @author Anthony Cho
 * Class of a photo tag
 *
 */
public class Tag implements Comparable<Tag>, Serializable{
    /**
     * The 'key' string value
     */
    private String Keys;

    /**
     * The 'value' string value
     */
    private String Values;

    /**
     * @return key of tag
     */
    public String getKey()
    {
        return this.Keys;
    }

    /**
     * @return value of tag
     */
    public String getValue()
    {
        return this.Values;
    }

    /**
     * Tag constructor
     * @param key of pair
     * @param value of pair
     */
    public Tag(String key, String value)
    {
        this.Keys = key;
        this.Values = value;
    }

    /**
     * @param key
     */
    public void setKey(String key)
    {
        this.Keys = key;
    }

    /**
     * @param value
     */
    public void setValue(String value)
    {
        this.Values = value;
    }

    /**
     *@return joins tag in proper format
     */
    public String toString()
    {
        return this.Keys + "\t:\t" + this.Values;
    }

    /**
     * comparator implements compareTo
     * @param a compares to tag t
     * @return 0 default
     */
    public int compareTo(Tag a)
    {
        return 0;
    }
}
