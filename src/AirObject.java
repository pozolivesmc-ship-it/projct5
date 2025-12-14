/**
 * This is the AirObject class that will be used for 
 * AirPlane, Balloon, Bird, Drone, and Rocket 
 * @author {Giovanni Garcia}
 * @version {12.9.2025}
 */
public abstract class AirObject implements Comparable<AirObject> {
    //Size of the world
    private static final int WORLD_SIZE = 1024;
    //x, y, and z bounds
    private int x;
    private int y;
    private int z;
    //x, y, and z bounds for the width
    private int xwid;
    private int ywid;
    private int zwid;
    //Name of key in SkipList
    private String name;
    
    /**
     * This is the constructor for the AirObject class
     * @param name is the name 
     * @param x is the x coord
     * @param y is the y coord
     * @param z is the z coord
     * @param xwid is the x width
     * @param ywid is the y width
     * @param zwid is the z width
     */
    public AirObject(String name, int x, int y, int z,
                     int xwid, int ywid, int zwid)
    {
        //This updates all the parameters
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xwid = xwid;
        this.ywid = ywid;
        this.zwid = zwid;
    }
    /**
     * This is the getter method for x coord
     * @return x coord
     */
    public int getXorig()
    {
        return this.x;
    }
    /**
     * This is the getter method for y coord
     * @return y coord
     */
    public int getYorig()
    {
        return this.y;
    }
    /**
     * This is the getter method for z coord
     * @return z coord
     */
    public int getZorig()
    {
        return this.z;
    }
    /**
     * This is the getter method for x width
     * @return x width
     */
    public int getXwidth()
    {
        return this.xwid;
    }
    /**
     * This is the getter method for y width
     * @return y width
     */
    public int getYwidth()
    {
        return this.ywid;
    }
    /**
     * This is the getter method for z width
     * @return z widths
     */
    public int getZwidth()
    {
        return this.zwid;
    }
    /**
     * This is the getter method for name
     * @return string for name
     */
    public String getName()
    {
        return this.name;
    }
    /**
     * This is the compareTo method that compares the objects
     * @param other is the object being compared
     */
    @Override
    public int compareTo(AirObject other)
    {
        return this.name.compareTo(other.name);
    }
    /**
     * This checks if the input is valid
     * @return true or false
     */
    public boolean isValid()
    {
        //Check if name is null
        //Or if any inputs violate the bounds
        if (name == null)
        { 
            return false;
        }
        if (x < 0 || y < 0 || z < 0)
        {
            return false;
        }
        if (xwid <= 0 || ywid <= 0 || zwid <= 0)
        {
            return false;
        }
        return !(x + xwid > WORLD_SIZE || y + ywid > WORLD_SIZE
            || z + zwid > WORLD_SIZE);
        
    }
}