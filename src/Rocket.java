
/**
 * This is the Rocket class 
 * @author {Giovanni Garcia}
 * @version {11.16.2025}
 */
public class Rocket extends AirObject{

    private int ascentRate;
    private float trajectory;
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
                  int ascentRate, float trajectory)
    {
        super(name, x, y, z, xwid, ywid, zwid);
        this.ascentRate = ascentRate;
        this.trajectory = trajectory;
    }
    /**
     * This is a getter method for ascentRate
     * @return int for ascentRate
     */
    public int getAscentRate()
    {
        return this.ascentRate;
    }
    /**
     * This is a getter method for trajectory
     * @return float for trajectory
     */
    public float getTrajectory()
    {
        return this.trajectory;
    }
    
}
