
/**
 * This is the interface for BinNode
 * @author {Giovanni Garcia}
 * @version {12.13.2025}
 */
public interface BinNode {
    /**
     * This prints node in preorder 
     * @param sb is the StringBuilder
     * @param x is the x value
     * @param y is the y value
     * @param z is the z value
     * @param w is the width
     * @param h is the height
     * @param d is the dimension depth
     * @param depth is the tree depth
     */
    void print(StringBuilder sb, int x, int y, int z, int w, int h, int d,
               int depth);
    /**
     * This counts the nodes
     * @return int for nodes
     */
    int countNodes();
    /**
     * This inserts an object into the subtree at this node
     * @param obj is the object being inserted
     * @param x is the x value
     * @param y is the y value
     * @param z is the z value
     * @param w is the width
     * @param h is the height
     * @param d is the dimension depth
     * @param depth is the tree depth
     * @return BinNode node 
     */
    BinNode insert(AirObject obj, int x, int y, int z, int w, int h,
                  int d, int depth);
    /**
     * This removes an object from the subtree at this node
     * @param obj is the object being inserted
     * @param x is the x value
     * @param y is the y value
     * @param z is the z value
     * @param w is the width
     * @param h is the height
     * @param d is the dimension depth
     * @param depth is the tree depth
     * @return BinNode node 
     */
    BinNode remove(AirObject obj, int x, int y, int z, int w, int h,
            int d, int depth);
    /**
     * This method checks for intersections
     * @param sb is StringBuilder
     * @param x1 is the x value 
     * @param y1 is the y value 
     * @param z1 is the z value 
     * @param w1 is the width
     * @param h1 is the height
     * @param d1 is the dimension depth
     * @param x2 is the x value 
     * @param y2 is the y value 
     * @param z2 is the z value 
     * @param w2 is the width
     * @param h2 is the height
     * @param d2 is the dimension depth
     * @param depth is the tree depth 
     * @param visited is the array of visited nodes
     */
    void intersect(StringBuilder sb, int x1, int y1, int z1, int w1,
                   int h1, int d1, int x2, int y2, int z2, int w2, int h2, int d2,
                   int depth, int[] visited);
    /**
     * This method appends collisions found in subtree
     * @param sb is StringBuilder
     * @param x is the x value 
     * @param y is the y value 
     * @param z is the y value
     * @param w is the width 
     * @param h is the height 
     * @param d is the dimension depth 
     * @param depth is the tree depth
     */
    void collisions(StringBuilder sb, int x, int y, int z, int w, int h,
                   int d, int depth);
}
