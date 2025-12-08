/**
 * This is the Bird class 
 * @author {Giovanni Garcia}
 * @version {12.4.2025}
 */
public class Bird extends AirObject {

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
        return !(number <= 0);
    }
}
