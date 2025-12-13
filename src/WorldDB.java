import java.util.Random;

/**
 * The world for this project. We have a Skip List and a Bintree.
 *
 * @author {Giovanni Garcia}
 * @version {12.9.2025}
 */
public class WorldDB implements ATC {
    //Size of the world
    private final int worldSize = 1024;
    //Random number for SkipList level 
    private Random rnd;
    //SkipList that stores AirObjects by name 
    private SkipList<String, AirObject> skiplist;
    //Bintree variable
    private Bintree bintree;
    

    /**
     * Create a brave new World.
     * @param r A random number generator to use
     *
     */
    public WorldDB(Random r) {
        rnd = r;
        //If null create new random object
        if (rnd == null) {
            rnd = new Random();
        }
        clear();
    }

    /**
     * Clear the world
     *
     */
    public void clear() {
        //Resets SkipList and Bintree
        skiplist = new SkipList<>(rnd);
        bintree = new Bintree();
    }


    // ----------------------------------------------------------
    /**
     * (Try to) insert an AirObject into the database
     * @param a An AirObject.
     * @return True iff the AirObject is successfully entered into the database
     */
    public boolean add(AirObject a) {
        //Check if object is null
        if (a == null)
        {
            return false;
        }
        //Checks if valid
        if (!a.isValid())
        {
            return false;
        }
        //Checks for duplicate
        if (skiplist.find(a.getName()) != null)
        {
            return false;
        }
        //Insert it into both and return true
        skiplist.insert(a.getName(), a);
        bintree.insert(a);
        return true;
    }


    // ----------------------------------------------------------
    /**
     * The AirObject with this name is deleted from the database (if it exists).
     * Print the AirObject's toString value if one with that name exists.
     * If no such AirObject with this name exists, return null.
     * @param name AirObject name.
     * @return A string representing the AirObject, or null if no such name.
     */
    public String delete(String name) {
        //If name doesn't exist return null
        if (name == null)
        {
            return null;
        }
        //Find target 
        AirObject target = skiplist.find(name);
        if (target == null)
        {
            return null;
        }
        //Remove from SkipList
        skiplist.remove(name);
        return target.toString();
    }
    


    // ----------------------------------------------------------
    /**
     * Return a listing of the Skiplist in alphabetical order on the names.
     * See the sample test cases for details on format.
     * @return String listing the AirObjects in the Skiplist as specified.
     */
    public String printskiplist() {
        return skiplist.printList();
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of the Bintree nodes in preorder.
     * See the sample test cases for details on format.
     * @return String listing the Bintree nodes as specified.
     */
    public String printbintree() {
        return bintree.printTree();
    }



    // ----------------------------------------------------------
    /**
     * Print an AirObject with a given name if it exists
     * @param name The name of the AirObject to print
     * @return String showing the toString for the AirObject if it exists
     *         Return null if there is no such name
     */
    public String print(String name) {
        //If name doesn't exist return null
        if (name == null)
        {
            return null;
        }
        //Find AirObject and return it
        AirObject airObject = skiplist.find(name);
        if (airObject == null)
        {
            return null;
        }
        return airObject.toString();
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of the AirObjects found in the database between the
     * min and max values for names.
     * See the sample test cases for details on format.
     * @param start Minimum of range
     * @param end Maximum of range
     * @return String listing the AirObjects in the range as specified.
     *         Null if the parameters are bad
     */
    public String rangeprint(String start, String end) {
        //Check if either are null
        if (start == null || end == null)
        {
            return null;
        }
        //Check if invalid range
        if (start.compareTo(end) > 0)
        {
            return null;
        }
        return skiplist.range(start, end);
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of all collisions between AirObjects bounding boxes
     * that are found in the database.
     * See the sample test cases for details on format.
     * Note that the collision is only reported for the node that contains the
     * origin of the intersection box.
     * @return String listing the AirObjects that participate in collisions.
     */
    public String collisions() {
        return bintree.collisions();
    }


    // ----------------------------------------------------------
    /**
     * Return a listing of all AirObjects whose bounding boxes
     * that intersect the given bounding box.
     * Note that the collision is only reported for the node that contains the
     * origin of the intersection box.
     * See the sample test cases for details on format.
     * @param x Bounding box upper left x
     * @param y Bounding box upper left y
     * @param z Bounding box upper left z
     * @param xwid Bounding box x width
     * @param ywid Bounding box y width
     * @param zwid Bounding box z width
     * @return String listing the AirObjects that intersect the given box.
     *         Return null if any input parameters are bad
     */
    public String intersect(int x, int y, int z, int xwid, int ywid, int zwid) {
        //Check if input is valid
        if (x < 0 || y < 0 || z < 0 || x >= worldSize || y >= worldSize || z >= worldSize ||
            xwid <= 0 || ywid <= 0 || zwid <= 0 || 
            xwid + x > worldSize || ywid + y > worldSize || zwid + z > worldSize)
        {
            return null;
        }
        return bintree.intersect(x, y, z, xwid, ywid, zwid);
    }
}
