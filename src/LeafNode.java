/**
 * This is the LeafNode class
 * @author {Giovanni Garcia}
 * @version {12.13.2025}
 */
public class LeafNode implements BinNode {
    //Array of objects in leaf node
    private AirObject[] objects;
    //Number of objects in array
    private int size;

    /**
     * Constructor creates empty leaf node
     */
    public LeafNode() {
        this.objects = new AirObject[Bintree.LEAF_MAX + 1];
        this.size = 0;
    }


    /**
     * This method adds an object to the leaf
     * Expands array if needed
     * @param obj is the object being added
     */
    public void addObject(AirObject obj) {
        //If array is full update array size
        if (this.size == this.objects.length) {
            AirObject[] newArr = new AirObject[this.objects.length * 2];
            for (int i = 0; i < this.objects.length; i++) {
                newArr[i] = this.objects[i];
            }
            this.objects = newArr;
        }
        //Insert but keep correct order 
        int i = this.size - 1;
        while (i >= 0 && this.objects[i].compareTo(obj) > 0) {
            this.objects[i + 1] = this.objects[i];
            i--;
        }
        //Update
        this.objects[i + 1] = obj;
        this.size++;
    }

    /**
     * This prints leaf node and every object
     * @param sb is the StringBuilder
     * @param x is the x value
     * @param y is the y value
     * @param z is the z value
     * @param w is the width
     * @param h is the height
     * @param d is the dimension depth
     * @param depth is the tree depth
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
        //Check spacing and then append info
        Bintree.spacing(sb, depth);
        sb.append("Leaf with ").append(this.size).append(" objects (");
        sb.append(x).append(", ").append(y).append(", ");
        sb.append(z).append(", ").append(w).append(", ").append(h).append(", ");
        sb.append(d).append(") ").append(depth).append("\r\n");
        for (int i = 0; i < this.size; i++) {
            Bintree.spacing(sb, depth + 1);
            sb.append("(").append(this.objects[i].toString()).append(")\r\n");
        }
    }

    /**
     * Leaf = 1 node
     * This counts the nodes
     */
    public int countNodes() {
        return 1;
    }

    /**
     * This inserts an object into the leaf
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
        this.addObject(obj);
        //Checks size is valid
        if (this.size <= Bintree.LEAF_MAX) {
            return this;
        }
        //If objects overlap don't split
        if (this.hasCommonIntersection()) {
            return this;
        }
        //If not then split into internal node
        //Reinsert every object and return
        InternalNode internal = new InternalNode();
        for (int i = 0; i < this.size; i++) {
            internal.insert(this.objects[i], x, y, z, w, h, d, depth);
        }
        return internal;
    }

    /**
     * This removes an object from the leaf
     * If leaf becomes empty -> Flyweight
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
    public BinNode remove(
        AirObject obj,
        int x,
        int y,
        int z,
        int w,
        int h,
        int d,
        int depth) {
        //If null then return
        if (obj == null) {
            return this;
        }
        int idx = -1;
        for (int i = 0; i < this.size; i++) {
            if (this.objects[i] == obj) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            return this;
        }
        //Shift items left to correctly remove
        for (int i = idx; i < this.size - 1; i++) {
            this.objects[i] = this.objects[i + 1];
        }
        //Update size
        size--;
        //If no more objects then return FLYWEIGHT
        if (size == 0) {
            return Bintree.FLYWEIGHT;
        }
        return this;
    }

    /**
     * This method checks if there's a shared intersection
     * So then splitting would recurse forever  
     * @return true or false if there's an intersection 
     */
    public boolean hasCommonIntersection() {
        //Check if empty
        if (this.size == 0) {
            return false;
        }
        //Intersection at first object 
        int ix = this.objects[0].getXorig();
        int iy = this.objects[0].getYorig();
        int iz = this.objects[0].getZorig();
        int iw = this.objects[0].getXwidth();
        int ih = this.objects[0].getYwidth();
        int id = this.objects[0].getZwidth();
        //Intersection for every other object now
        for (int i = 1; i < this.size; i++) {
            int nx = this.objects[i].getXorig();
            int ny = this.objects[i].getYorig();
            int nz = this.objects[i].getZorig();
            int nw = this.objects[i].getXwidth();
            int nh = this.objects[i].getYwidth();
            int nd = this.objects[i].getZwidth();
            int maxX = Math.max(ix, nx);
            int maxY = Math.max(iy, ny);
            int maxZ = Math.max(iz, nz);
            int minX = Math.min(ix + iw, nx + nw);
            int minY = Math.min(iy + ih, ny + nh);
            int minZ = Math.min(iz + id, nz + nd);
            iw = minX - maxX;
            ih = minY - maxY;
            id = minZ - maxZ;
            ix = maxX;
            iy = maxY;
            iz = maxZ;
            //If it becomes empty then no shared overlap
            if (iw <= 0 || ih <= 0 || id <= 0) {
                return false;
            }
        }
        return iw > 0 && ih > 0 && id > 0;
    }


    /**
     * This is a getter for the size
     * @return int for number of objects
     */
    public int getSize() {
        return this.size;
    }

    /**
     * This is a getter for the object at a certain index
     * @param index is the index of the object
     * @return AirObject object
     */
    public AirObject getObject(int index) {
        return this.objects[index];
    }

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
        //If overlap then return
        if (!Bintree.overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2)) {
            return;
        }
        //Visited node so update
        visited[0]++;
        //Check spacing and append
        Bintree.spacing(sb, depth);
        sb.append("In leaf node (").append(x2).append(", ").append(y2).append(
            ", ");
        sb.append(z2).append(", ").append(w2).append(", ").append(h2).append(
            ", ");
        sb.append(d2).append(") ").append(depth).append("\r\n");
        //Check each object in leaf
        for (int i = 0; i < this.size; i++) {
            AirObject a = this.objects[i];
            //Skip objects that don't overlap
            if (!Bintree.overlap(a.getXorig(), a.getYorig(), a.getZorig(), a
                .getXwidth(), a.getYwidth(), a.getZwidth(), x1, y1, z1, w1, h1,
                d1)) {
                continue;
            }
            //Calculate origin point of intersection
            int ix = Math.max(a.getXorig(), x1);
            int iy = Math.max(a.getYorig(), y1);
            int iz = Math.max(a.getZorig(), z1);
            //Print point if object is in node region
            if (Bintree.containsPoint(ix, iy, iz, x2, y2, z2, w2, h2, d2)) {
                sb.append(a.toString()).append("\r\n");
            }
        }
    }

    /**
     * This method checks collisions in leaf
     * @param sb is StringBuilder
     * @param x is the x value 
     * @param y is the y value 
     * @param z is the z value
     * @param w is the width 
     * @param h is the height 
     * @param d is the dimension depth 
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
        //Checks spacing and appends info
        Bintree.spacing(sb, depth);
        sb.append("In leaf node (").append(x).append(", ").append(y).append(
            ", ");
        sb.append(z).append(", ").append(w).append(", ").append(h).append(", ");
        sb.append(d).append(") ").append(depth).append("\r\n");
        for (int i = 0; i < this.size; i++) {
            for (int j = i + 1; j < this.size; j++) {
                AirObject a = this.objects[i];
                AirObject b = this.objects[j];
                //Checks if 2 objects overlap
                if (!Bintree.overlap(a.getXorig(), a.getYorig(), a.getZorig(),
                    a.getXwidth(), a.getYwidth(), a.getZwidth(), b.getXorig(), 
                    b.getYorig(), b.getZorig(), b.getXwidth(), b.getYwidth(),
                    b.getZwidth())) {
                    continue;
                }
                //Origin of intersection
                int ix = Math.max(a.getXorig(), b.getXorig());
                int iy = Math.max(a.getYorig(), b.getYorig());
                int iz = Math.max(a.getZorig(), b.getZorig());
                //Append collision if in leaf
                if (Bintree.containsPoint(ix, iy, iz, x, y, z, w, h, d)) {
                    sb.append("(").append(a.toString()).append(") and (");
                    sb.append(b.toString()).append(")\r\n");
                }
            }
        }
    }
}