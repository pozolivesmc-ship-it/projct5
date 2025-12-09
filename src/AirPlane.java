/**
 * This is the AirPlane class 
 * @author {Giovanni Garcia}
 * @version {12.4.2025}
 */
public class AirPlane extends AirObject {

    private String carrier;
    private int flight;
    private int engines;
    
    /**
     * This is the AirPlane class constructor
     * @param name is the name
     * @param x is the x coord
     * @param y is the y coord
     * @param z is the z coord
     * @param xwid is the x width
     * @param ywid is the y width
     * @param zwid is the z width
     * @param carrier is the carrier name
     * @param flight is the flight number
     * @param engines is the engine number
     */
    public AirPlane(String name, int x, int y, int z, 
                    int xwid, int ywid, int zwid,
                    String carrier, int flight, int engines)
    {
        super(name, x, y, z, xwid, ywid, zwid);
        this.carrier = carrier;
        this.flight = flight;
        this.engines = engines;
    }
    /**
     * This is the toString method
     * @return String for AirPlane
     */
    public String toString()
    {
        return "Airplane " + getName() + " " + getXorig() + " " + getYorig()
               + " " + getZorig() + " " + getXwidth() + " " + getYwidth()
               + " " + getZwidth() + " " + carrier + " " + flight +
               " " + engines;
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
        if (carrier == null)
        {
            return false;
        }
        if (flight <= 0)
        {
            return false;
        }
        return !(engines <= 0);
    }
}
