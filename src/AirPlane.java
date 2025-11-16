/**
 * This is the AirPlane class 
 * @author {Giovanni Garcia}
 * @version {11.16.2025}
 */
public class AirPlane extends AirObject{

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
     * This is the getter method for carrier
     * @return string for carrier
     */
    public String getCarrier()
    {
        return this.carrier;
    }
    /**
     * This is the getter method for flight
     * @return int for flight number
     */
    public int getFlight()
    {
        return this.flight;
    }
    /**
     * This is the getter method for engines
     * @return int for engines number
     */
    public int getEngines()
    {
        return this.engines;
    }
}
