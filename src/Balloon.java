/**
 * This is the Balloon class 
 * @author {Giovanni Garcia}
 * @version {12.4.2025}
 */
public class Balloon extends AirObject{
    
    private String type;
    private int ascentRate;
    
    /**
     * This is the Balloon class constructor
     * @param name is the name
     * @param x is the x coord
     * @param y is the y coord
     * @param z is the z coord
     * @param xwid is the x width
     * @param ywid is the y width
     * @param zwid is the z width
     * @param type is the type
     * @param ascentRate is the ascentRate
     */
    public Balloon(String name, int x, int y, int z, 
                   int xwid, int ywid, int zwid,
                   String type, int ascentRate)
    {
        super(name, x, y, z, xwid, ywid, zwid);
        this.type = type;
        this.ascentRate = ascentRate;
    }
    /**
     * This is the toString method
     * @return String for Balloon
     */
    public String toString()
    {
        return "Balloon " + getName() + " " + getXorig() + " " + getYorig()
               + " " + getZorig() + " " + getXwidth() + " " + getYwidth()
               + " " + getZwidth() + " " + type + " " + ascentRate;
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
        if (ascentRate <= 0)
        {
            return false;
        }
        return true;
    }
}
