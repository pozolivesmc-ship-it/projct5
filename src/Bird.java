/**
 * This is the Bird class 
 * @author {Giovanni Garcia}
 * @version {11.16.2025}
 */
public class Bird extends AirObject{

    private String type;
    private int number;
    /**
     * This is the Bird class constructor
     * @param name is the name
     * @param x is the x coord
     * @param y is the y coord
     * @param z is the z coord
     * @param xwid is the x width
     * @param ywid is the y width
     * @param zwid is the z width
     * @param type is the type
     * @param number is the number
     */
    public Bird(String name, int x, int y, int z, 
                int xwid, int ywid, int zwid,
                String type, int number)
    {
        super(name, x, y, z, xwid, ywid, zwid);
        this.type = type;
        this.number = number;
    }
    /**
     * This is a getter method for type
     * @return string for type
     */
    public String getType()
    {
        return this.type;
    }
    /**
     * This is a getter method for number
     * @return int for number
     */
    public int getNumber()
    {
        return this.number;
    }
    /**
     * This is the toString method
     * @return String for Bird
     */
    public String toString()
    {
        return "Bird " + getName() + " " + getXorig() + " " + getYorig()
               + " " + getZorig() + " " + getXwidth() + " " + getYwidth()
               + " " + getZwidth() + " " + type + " " + number;
    }
    /**
     * This checks if the input is valid
     * @return true or false
     */
    public boolean isValid()
    {
        if (!super.isValid())
        {
            return false;
        }
        if (type == null)
        {
            return false;
        }
        if (number <= 0)
        {
            return false;
        }
        return true;
    }
}
