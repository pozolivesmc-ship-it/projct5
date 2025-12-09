
/**
 * This is the Rocket class 
 * @author {Giovanni Garcia}
 * @version {12.9.2025}
 */
public class Rocket extends AirObject {
    //Number for ascent rate
    private int ascentRate;
    //Launch trajectory 
    private double trajectory;
    /**
     * This is the Rocket class constructor
     * @param name is the name
     * @param x is the x coord
     * @param y is the y coord
     * @param z is the z coord
     * @param xwid is the x width
     * @param ywid is the y width
     * @param zwid is the z width
     * @param ascentRate is the ascentRate
     * @param trajectory is the trajectory
     */
    public Rocket(String name, int x, int y, int z, 
                  int xwid, int ywid, int zwid,
                  int ascentRate, double trajectory)
    {
        super(name, x, y, z, xwid, ywid, zwid);
        this.ascentRate = ascentRate;
        this.trajectory = trajectory;
    }
    /**
     * This is the toString method
     * @return String for Rocket
     */
    public String toString()
    {
        return "Rocket " + getName() + " " + getXorig() + " " + getYorig()
               + " " + getZorig() + " " + getXwidth() + " " + getYwidth()
               + " " + getZwidth() + " " + ascentRate + " " + trajectory;
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
        if (ascentRate <= 0)
        {
            return false;
        }
        return !(trajectory <= 0);
    }
}
