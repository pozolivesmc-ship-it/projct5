
/**
 * This is the Drone class 
 * @author {Giovanni Garcia}
 * @version {12.9.2025}
 */
public class Drone extends AirObject {
    //Name of drone
    private String brand;
    //Number for engine
    private int engines;
    /**
     * This is the Drone class constructor
     * @param name is the name
     * @param x is the x coord
     * @param y is the y coord
     * @param z is the z coord
     * @param xwid is the x width
     * @param ywid is the y width
     * @param zwid is the z width
     * @param brand is the brand
     * @param engines is the engine number
     */
    public Drone(String name, int x, int y, int z, 
                 int xwid, int ywid, int zwid,
                 String brand, int engines)
    {
        super(name, x, y, z, xwid, ywid, zwid);
        this.brand = brand;
        this.engines = engines;
    }
    /**
     * This is the toString method
     * @return String for Drone
     */
    public String toString()
    {
        return "Drone " + getName() + " " + getXorig() + " " + getYorig()
               + " " + getZorig() + " " + getXwidth() + " " + getYwidth()
               + " " + getZwidth() + " " + brand + " " + engines;
    }
    /**
     * This checks if the input is valid
     * @return true or false
     */
    public boolean isValid()
    {
        //Check if core inputs are valid
        //Or if any other inputs violate the bounds
        if (!super.isValid())
        {
            return false;
        }
        if (brand == null)
        {
            return false;
        }
        return !(engines <= 0);
    }
}
