/**
 * This is the EmptyNode class
 * @author {Giovanni Garcia}
 * @version {12.13.2025}
 */
public class EmptyNode implements BinNode {
    /**
     * This method prints for an empty node
     * @param sb is StringBuilder
     * @param x is the x value 
     * @param y is the y value 
     * @param z is the z value
     * @param w is the width 
     * @param h is the height 
     * @param d is the dimension depth 
     * @param depth is the depth
     */
    public void print(
        StringBuilder sb,
        int x,
        int y,
        int z,
        int w,
        int h,
        int d,
        int depth) {
        //Checks for spacing and appends info
        Bintree.spacing(sb, depth);
        sb.append("E (").append(x).append(", ").append(y).append(", ");
        sb.append(z).append(", ").append(w).append(", ").append(h).append(", ");
        sb.append(d).append(") ").append(depth).append("\r\n");
    }

    /**
     * Empty node = 1 node
     * @return int for node count
     */
    public int countNodes() {
        return 1;
    }

    /**
     * This creates new LeafNode and stores object
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
    public BinNode insert(
        AirObject obj,
        int x,
        int y,
        int z,
        int w, 
        int h,
        int d,
        int depth) {
        LeafNode leaf = new LeafNode();
        leaf.addObject(obj);
        return leaf;
    }

    /**
     * This removes from empty leaf so nothing
     * @param obj is the object being removed
     * @param x is the x value
     * @param y is the y value
     * @param z is the z value
     * @param w is the width
     * @param h is the height
     * @param d is the dimension depth
     * @param depth is the tree depth
     * @return BinNode node 
     */
    public BinNode remove(
        AirObject obj,
        int x,
        int y,
        int z,
        int w,
        int h,
        int d,
        int depth) {
        return this;
    }

    /**
     * If overlaps then count node as visited
     * Doesn't print anything since empty
     * @param sb is StringBuilder
     * @param x1 is the x value 
     * @param y1 is the y value 
     * @param z1 is the z value 
     * @param w1 is the width
     * @param h1 is the height
     * @param d1 is the depth
     * @param x2 is the x value 
     * @param y2 is the y value 
     * @param z2 is the z value 
     * @param w2 is the width
     * @param h2 is the height
     * @param d2 is the depth
     * @param depth is the tree depth 
     * @param visited is the array of visited nodes
     */
    public void intersect(
        StringBuilder sb,
        int x1,
        int y1,
        int z1,
        int w1,
        int h1,
        int d1,
        int x2,
        int y2,
        int z2,
        int w2,
        int h2,
        int d2,
        int depth,
        int[] visited) {
        //If overlap then update visited
        if (Bintree.overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2)) {
            visited[0]++;
        }
    }

    /**
     * This is for collisions but no objects
     * So no collisions
     * @param sb is StringBuilder
     * @param x is the x value
     * @param y is the y value
     * @param z is the z value
     * @param w is the width
     * @param h is the height
     * @param d is the depth
     * @param depth is the tree depth
     */
    public void collisions(
        StringBuilder sb,
        int x,
        int y,
        int z,
        int w,
        int h,
        int d,
        int depth) {
        return;
    }
}