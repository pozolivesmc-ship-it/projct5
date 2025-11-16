/**
 * This is the Balloon class 
 * @author {Giovanni Garcia}
 * @version {11.16.2025}
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
     * This is a getter method for type
     * @return string for type
     */
    public String getType()
    {
        return this.type;
    }
    /**
     * This is a getter method for ascentRate
     * @return int for ascentRate
     */
    public int getAscentRate()
    {
        return this.ascentRate;
    }
}
