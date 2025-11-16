
/**
 * This is the Drone class 
 * @author {Giovanni Garcia}
 * @version {11.16.2025}
 */
public class Drone extends AirObject{

    private String brand;
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
     * This is a getter method for brand
     * @return string for brand
     */
    public String getBrand()
    {
        return this.brand;
    }
    /**
     * This is a getter method for engines
     * @return int for engines
     */
    public int getEngines()
    {
        return this.engines;
    }
}
