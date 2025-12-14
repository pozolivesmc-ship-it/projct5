/**
 * This is the Bintree class
 * 
 * @author {Giovanni Garcia}
 * @version {12.13.2025}
 */
public class Bintree {

    /**
     * Root of the Bintree
     */
    private BinNode root;
    /**
     * Flyweight empty node
     */
    public static final EmptyNode FLYWEIGHT = new EmptyNode();
    /**
     * Max number of AirObjects in a leaf before splitting
     */
    public static final int LEAF_MAX = 3;
    /**
     * Size of world
     */
    public static final int WORLD_SIZE = 1024;

    /**
     * This is the constructor for Bintree The root is an EmptyNode,
     * depth = 0
     */
    public Bintree() {
        this.root = FLYWEIGHT;
    }

    /**
     * This is the insert method for Bintree
     * 
     * @param obj is the object inserted
     */
    public void insert(AirObject obj) {
        //If object is null return
        if (obj == null) {
            return;
        }
        //If not then insert accordingly 
        this.root = this.root.insert(obj, 0, 0, 0, WORLD_SIZE, WORLD_SIZE,
            WORLD_SIZE, 0);
    }

    /**
     * Removes an object from the bintree
     * 
     * @param obj the object to remove
     */
    public void remove(AirObject obj) {
        //If object is null return
        if (obj == null) {
            return;
        }
        //If not then remove accordingly
        this.root = this.root.remove(obj, 0, 0, 0, WORLD_SIZE, WORLD_SIZE,
            WORLD_SIZE, 0);
    }

    /**
     * This is the printTree method
     * 
     * @return String for the tree
     */
    public String printTree() {
        StringBuilder sb = new StringBuilder();
        this.root.print(sb, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0);
        sb.append(this.root.countNodes()).append(" Bintree nodes printed\r\n");
        return sb.toString();
    }
    /**
     * This helps with indenting the tree
     * @param sb is the StringBuilder
     * @param depth is the indentation - 2 spaces each level
     */
    public static void spacing(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
    }

    /**
     * This is a helper method testing if there's any overlapping
     * @param x1 is box1 x value
     * @param y1 is box1 y value
     * @param z1 is box1 z value
     * @param w1 is box1 width value
     * @param h1 is box1 height value
     * @param d1 is box1 depth value
     * @param x2 is box2 x value
     * @param y2 is box2 y value
     * @param z2 is box2 z value
     * @param w2 is box2 width value
     * @param h2 is box2 height value
     * @param d2 is box2 depth value
     * @return true or false if it overlaps
     */
    public static boolean overlap(int x1, int y1, int z1, int w1, int h1,
        int d1, int x2, int y2, int z2, int w2, int h2, int d2) {
        //Checks if x values overlap
        boolean xOverlap = x1 < x2 + w2 && x2 < x1 + w1;
        //Checks if y values overlap
        boolean yOverlap = y1 < y2 + h2 && y2 < y1 + h1;
        //Checks if z values overlap
        boolean zOverlap = z1 < z2 + d2 && z2 < z1 + d1;
        //Compares all
        return xOverlap && yOverlap && zOverlap;
    }

    /**
     * 
     * Checks if a point is inside a bounding box
     * @param px is the new x value being checked
     * @param py is the new y value being checked
     * @param pz is the new z value being checked
     * @param x is the original x value
     * @param y is the original y value
     * @param z is the original z value
     * @param w is the width
     * @param h is the height
     * @param d is the dimension depth
     * @return true if point is inside
     */
    public static boolean containsPoint(int px, int py, int pz, int x, int y,
        int z, int w, int h, int d) {
        return px >= x && px < x + w && py >= y && py < y + h && pz >= z
            && pz < z + d;
    }
    /**
     * This prints all the objects that intersect
     * @param x is the x value
     * @param y is the y value
     * @param z is the z value
     * @param xwid is the width
     * @param ywid is the height
     * @param zwid is the depth
     * @return string of objects that intersect
     */
    public String intersect(int x, int y, int z, int xwid, int ywid,
        int zwid) {
        //Checks for invalid dimensions
        if (xwid <= 0 || ywid <= 0 || zwid <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("The following objects intersect (");
        sb.append(x).append(" ").append(y).append(" ").append(z).append(" ");
        sb.append(xwid).append(" ").append(ywid).append(" ").append(zwid)
            .append("):\r\n");
        //Special case if tree is empty still 1 node visited
        if (this.root == FLYWEIGHT)
        {
            sb.append(1).append(" nodes were visited in the bintree\r\n");
            return sb.toString();
        }
        //Visited gets incremented
        int[] visited = new int[] {0};
        this.root.intersect(sb, x, y, z, xwid, ywid, zwid, 0, 0, 0,
                WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0, visited);
        sb.append(visited[0])
            .append(" nodes were visited in the bintree\r\n");
        return sb.toString();
    }

    /**
     * This prints all collisions
     * @return String of object collisions
     */
    public String collisions() {
        StringBuilder sb = new StringBuilder();
        sb.append("The following collisions exist in the database:\r\n");
        this.root.collisions(sb, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE,
            0);
        return sb.toString();
    }
}