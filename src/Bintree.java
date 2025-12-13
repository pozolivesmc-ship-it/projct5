/**
 * This is the Bintree class
 * 
 * @author {Giovanni Garcia}
 * @version {12.10.2025}
 */
public class Bintree {

    private BinNode root;
    private static final EmptyNode FLYWEIGHT = new EmptyNode();
    private static final int LEAF_MAX = 3;
    private static final int WORLD_SIZE = 1024;
    private int visited;

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
        if (obj == null) {
            return;
        }
        this.root = this.root.insert(obj, 0, 0, 0, WORLD_SIZE, WORLD_SIZE,
            WORLD_SIZE, 0);
    }

    /**
     * Removes an object from the bintree
     * 
     * @param obj the object to remove
     */
    public void remove(AirObject obj) {
        if (obj == null) {
            return;
        }
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

    private static void spacing(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
    }

    /**
     * This is a helper method testing if there's any overlapping
     * 
     * @return true or false if it overlaps
     */
    private static boolean overlap(int x1, int y1, int z1, int w1, int h1,
        int d1, int x2, int y2, int z2, int w2, int h2, int d2) {
        boolean xOverlap = x1 < x2 + w2 && x2 < x1 + w1;
        boolean yOverlap = y1 < y2 + h2 && y2 < y1 + h1;
        boolean zOverlap = z1 < z2 + d2 && z2 < z1 + d1;
        return xOverlap && yOverlap && zOverlap;
    }

    /**
     * Checks if a point is inside a bounding box
     * 
     * @return true if point is inside
     */
    private static boolean containsPoint(int px, int py, int pz, int x, int y,
        int z, int w, int h, int d) {
        return px >= x && px < x + w && py >= y && py < y + h && pz >= z
            && pz < z + d;
    }

    public String intersect(int x, int y, int z, int xwid, int ywid,
        int zwid) {
        if (xwid <= 0 || ywid <= 0 || zwid <= 0) {
            return null;
        }
        this.visited = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("The following objects intersect (");
        sb.append(x).append(" ").append(y).append(" ").append(z).append(" ");
        sb.append(xwid).append(" ").append(ywid).append(" ").append(zwid)
            .append("):\r\n");
        if (this.root == FLYWEIGHT) {
            this.visited = 1;
        }
        else {
            int[] count = new int[] {0};
            this.root.intersect(sb, x, y, z, xwid, ywid, zwid, 0, 0, 0,
                WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0, count);
            this.visited = count[0];
        }
        sb.append(this.visited)
            .append(" nodes were visited in the bintree\r\n");
        return sb.toString();
    }

    public String collisions() {
        StringBuilder sb = new StringBuilder();
        sb.append("The following collisions exist in the database:\r\n");
        this.root.collisions(sb, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE,
            0);
        return sb.toString();
    }

    /**
     * This is the interface for BinNode
     */
    private interface BinNode {
        void print(StringBuilder sb, int x, int y, int z, int w, int h, int d,
            int depth);

        int countNodes();

        BinNode insert(AirObject obj, int x, int y, int z, int w, int h,
            int d, int depth);

        BinNode remove(AirObject obj, int x, int y, int z, int w, int h,
            int d, int depth);

        void intersect(StringBuilder sb, int x1, int y1, int z1, int w1,
            int h1, int d1, int x2, int y2, int z2, int w2, int h2, int d2,
            int depth, int[] visited);

        void collisions(StringBuilder sb, int x, int y, int z, int w, int h,
            int d, int depth);
    }

    /**
     * This is the EmptyNode class
     */
    private static class EmptyNode implements BinNode {
        public void print(StringBuilder sb, int x, int y, int z, int w, int h,
            int d, int depth) {
            spacing(sb, depth);
            sb.append("E (").append(x).append(", ").append(y).append(", ");
            sb.append(z).append(", ").append(w).append(", ").append(h)
                .append(", ");
            sb.append(d).append(") ").append(depth).append("\r\n");
        }

        public int countNodes() {
            return 1;
        }

        public BinNode insert(AirObject obj, int x, int y, int z, int w,
            int h, int d, int depth) {
            LeafNode leaf = new LeafNode();
            leaf.addObject(obj);
            return leaf;
        }

        public BinNode remove(AirObject obj, int x, int y, int z, int w,
            int h, int d, int depth) {
            return this;
        }

        public void intersect(StringBuilder sb, int x1, int y1, int z1,
            int w1, int h1, int d1, int x2, int y2, int z2, int w2, int h2,
            int d2, int depth, int[] visited) {
            return;
        }

        public void collisions(StringBuilder sb, int x, int y, int z, int w,
            int h, int d, int depth) {
            return;
        }
    }

    /**
     * This is the LeafNode class
     */
    private static class LeafNode implements BinNode {
        private AirObject[] objects;
        private int size;

        public LeafNode() {
            this.objects = new AirObject[LEAF_MAX + 1];
            this.size = 0;
        }

        public void addObject(AirObject obj) {
            if (this.size == this.objects.length) {
                AirObject[] newArr = new AirObject[this.objects.length * 2];
                for (int i = 0; i < this.objects.length; i++) {
                    newArr[i] = this.objects[i];
                }
                this.objects = newArr;
            }
            int i = this.size - 1;
            while (i >= 0 && this.objects[i].compareTo(obj) > 0) {
                this.objects[i + 1] = this.objects[i];
                i--;
            }
            this.objects[i + 1] = obj;
            this.size++;
        }

        public void print(StringBuilder sb, int x, int y, int z, int w, int h,
            int d, int depth) {
            spacing(sb, depth);
            sb.append("Leaf with ").append(this.size).append(" objects (");
            sb.append(x).append(", ").append(y).append(", ");
            sb.append(z).append(", ").append(w).append(", ").append(h)
                .append(", ");
            sb.append(d).append(") ").append(depth).append("\r\n");
            for (int i = 0; i < this.size; i++) {
                spacing(sb, depth + 1);
                sb.append("(").append(this.objects[i].toString())
                    .append(")\r\n");
            }
        }

        public int countNodes() {
            return 1;
        }

        public BinNode insert(AirObject obj, int x, int y, int z, int w,
            int h, int d, int depth) {
            this.addObject(obj);
            if (this.size <= LEAF_MAX) {
                return this;
            }
            if (this.hasCommonIntersection()) {
                return this;
            }
            InternalNode internal = new InternalNode();
            for (int i = 0; i < this.size; i++) {
                internal.insert(this.objects[i], x, y, z, w, h, d, depth);
            }
            return internal;
        }

        public BinNode remove(AirObject obj, int x, int y, int z, int w,
            int h, int d, int depth) {
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
            for (int i = idx; i < this.size - 1; i++) {
                this.objects[i] = this.objects[i + 1];
            }
            this.size--;
            if (this.size == 0) {
                return FLYWEIGHT;
            }
            return this;
        }

        private boolean hasCommonIntersection() {
            if (this.size == 0) {
                return false;
            }
            int ix = this.objects[0].getXorig();
            int iy = this.objects[0].getYorig();
            int iz = this.objects[0].getZorig();
            int iw = this.objects[0].getXwidth();
            int ih = this.objects[0].getYwidth();
            int id = this.objects[0].getZwidth();
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
                if (iw <= 0 || ih <= 0 || id <= 0) {
                    return false;
                }
            }
            return iw > 0 && ih > 0 && id > 0;
        }

        public int getSize() {
            return this.size;
        }

        public AirObject getObject(int index) {
            return this.objects[index];
        }

        public boolean hasCommonIntersectionPublic() {
            return this.hasCommonIntersection();
        }

        public void intersect(StringBuilder sb, int x1, int y1, int z1,
            int w1, int h1, int d1, int x2, int y2, int z2, int w2, int h2,
            int d2, int depth, int[] visited) {
            if (!overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2)) {
                return;
            }
            visited[0]++;
            spacing(sb, depth);
            sb.append("In leaf node (").append(x2).append(", ").append(y2)
                .append(", ");
            sb.append(z2).append(", ").append(w2).append(", ").append(h2)
                .append(", ");
            sb.append(d2).append(") ").append(depth).append("\r\n");
            for (int i = 0; i < this.size; i++) {
                if (!overlap(this.objects[i].getXorig(),
                    this.objects[i].getYorig(), this.objects[i].getZorig(),
                    this.objects[i].getXwidth(), this.objects[i].getYwidth(),
                    this.objects[i].getZwidth(), x1, y1, z1, w1, h1, d1)) {
                    continue;
                }
                int ix = Math.max(this.objects[i].getXorig(), x1);
                int iy = Math.max(this.objects[i].getYorig(), y1);
                int iz = Math.max(this.objects[i].getZorig(), z1);
                if (containsPoint(ix, iy, iz, x2, y2, z2, w2, h2, d2)) {
                    sb.append(this.objects[i].toString()).append("\r\n");
                }
            }
        }

        public void collisions(StringBuilder sb, int x, int y, int z, int w,
            int h, int d, int depth) {
            spacing(sb, depth);
            sb.append("In leaf node (").append(x).append(", ").append(y)
                .append(", ");
            sb.append(z).append(", ").append(w).append(", ").append(h)
                .append(", ");
            sb.append(d).append(") ").append(depth).append("\r\n");
            for (int i = 0; i < this.size; i++) {
                for (int j = i + 1; j < this.size; j++) {
                    AirObject a = this.objects[i];
                    AirObject b = this.objects[j];
                    if (!overlap(a.getXorig(), a.getYorig(), a.getZorig(),
                        a.getXwidth(), a.getYwidth(), a.getZwidth(),
                        b.getXorig(), b.getYorig(), b.getZorig(),
                        b.getXwidth(), b.getYwidth(), b.getZwidth())) {
                        continue;
                    }
                    int ix = Math.max(a.getXorig(), b.getXorig());
                    int iy = Math.max(a.getYorig(), b.getYorig());
                    int iz = Math.max(a.getZorig(), b.getZorig());
                    if (containsPoint(ix, iy, iz, x, y, z, w, h, d)) {
                        sb.append("(").append(a.toString()).append(") and (");
                        sb.append(b.toString()).append(")\r\n");
                    }
                }
            }
        }
    }

    /**
     * This is the InternalNode class
     */
    private static class InternalNode implements BinNode {
        private BinNode left;
        private BinNode right;

        private static int addUnique(AirObject obj, AirObject[] arr,
            int count) {
            for (int i = 0; i < count; i++) {
                if (arr[i] == obj) {
                    return count;
                }
            }
            if (count < arr.length) {
                arr[count] = obj;
                return count + 1;
            }
            return count;
        }

        private static int gatherObjects(BinNode node, AirObject[] buffer,
            int count) {
            if (node instanceof LeafNode) {
                LeafNode leaf = (LeafNode)node;
                for (int i = 0; i < leaf.getSize(); i++) {
                    count = addUnique(leaf.getObject(i), buffer, count);
                }
                return count;
            }
            if (node instanceof InternalNode) {
                InternalNode internal = (InternalNode)node;
                count = gatherObjects(internal.left, buffer, count);
                count = gatherObjects(internal.right, buffer, count);
            }
            return count;
        }

        public InternalNode() {
            this.left = FLYWEIGHT;
            this.right = FLYWEIGHT;
        }

        public void print(StringBuilder sb, int x, int y, int z, int w, int h,
            int d, int depth) {
            spacing(sb, depth);
            sb.append("I (");
            sb.append(x).append(", ").append(y).append(", ");
            sb.append(z).append(", ").append(w).append(", ").append(h)
                .append(", ");
            sb.append(d).append(") ").append(depth).append("\r\n");

            int split = depth % 3;
            // X split then y split then z split
            if (split == 0) {
                int half = w / 2;
                this.left.print(sb, x, y, z, half, h, d, depth + 1);
                this.right.print(sb, x + half, y, z, w - half, h, d,
                    depth + 1);
            }
            else if (split == 1) {
                int half = h / 2;
                this.left.print(sb, x, y, z, w, half, d, depth + 1);
                this.right.print(sb, x, y + half, z, w, h - half, d,
                    depth + 1);
            }
            else {
                int half = d / 2;
                this.left.print(sb, x, y, z, w, h, half, depth + 1);
                this.right.print(sb, x, y, z + half, w, h, d - half,
                    depth + 1);
            }
        }

        public int countNodes() {
            return 1 + this.left.countNodes() + this.right.countNodes();
        }

        public BinNode insert(AirObject obj, int x, int y, int z, int w,
            int h, int d, int depth) {
            int split = depth % 3;
            if (split == 0) {
                int half = w / 2;
                int rightWidth = w - half;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y,
                    z, half, h, d)) {
                    this.left = this.left.insert(obj, x, y, z, half, h, d,
                        depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(),
                    x + half, y, z, rightWidth, h, d)) {
                    this.right = this.right.insert(obj, x + half, y, z,
                        rightWidth, h, d, depth + 1);
                }
            }
            else if (split == 1) {
                int half = h / 2;
                int rightHeight = h - half;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y,
                    z, w, half, d)) {
                    this.left = this.left.insert(obj, x, y, z, w, half, d,
                        depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x,
                    y + half, z, w, rightHeight, d)) {
                    this.right = this.right.insert(obj, x, y + half, z, w,
                        rightHeight, d, depth + 1);
                }
            }
            else {
                int half = d / 2;
                int rightDepth = d - half;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y,
                    z, w, h, half)) {
                    this.left = this.left.insert(obj, x, y, z, w, h, half,
                        depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y,
                    z + half, w, h, rightDepth)) {
                    this.right = this.right.insert(obj, x, y, z + half, w, h,
                        rightDepth, depth + 1);
                }
            }
            return this;
        }

        public BinNode remove(AirObject obj, int x, int y, int z, int w,
            int h, int d, int depth) {
            int split = depth % 3;
            if (split == 0) {
                int half = w / 2;
                int rightWidth = w - half;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y,
                    z, half, h, d)) {
                    this.left = this.left.remove(obj, x, y, z, half, h, d,
                        depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(),
                    x + half, y, z, rightWidth, h, d)) {
                    this.right = this.right.remove(obj, x + half, y, z,
                        rightWidth, h, d, depth + 1);
                }
            }
            else if (split == 1) {
                int half = h / 2;
                int rightHeight = h - half;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y,
                    z, w, half, d)) {
                    this.left = this.left.remove(obj, x, y, z, w, half, d,
                        depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x,
                    y + half, z, w, rightHeight, d)) {
                    this.right = this.right.remove(obj, x, y + half, z, w,
                        rightHeight, d, depth + 1);
                }
            }
            else {
                int half = d / 2;
                int rightDepth = d - half;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y,
                    z, w, h, half)) {
                    this.left = this.left.remove(obj, x, y, z, w, h, half,
                        depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y,
                    z + half, w, h, rightDepth)) {
                    this.right = this.right.remove(obj, x, y, z + half, w, h,
                        rightDepth, depth + 1);
                }
            }
            if (this.left == FLYWEIGHT && this.right == FLYWEIGHT) {
                return FLYWEIGHT;
            }
            if (this.right == FLYWEIGHT) {
                return this.left;
            }
            if (this.left == FLYWEIGHT) {
                return this.right;
            }
            if (this.left instanceof LeafNode
                && this.right instanceof LeafNode) {
                LeafNode l = (LeafNode)this.left;
                LeafNode r = (LeafNode)this.right;
                int total = l.getSize() + r.getSize();
                LeafNode combined = new LeafNode();
                for (int i = 0; i < l.getSize(); i++) {
                    combined.addObject(l.getObject(i));
                }
                for (int i = 0; i < r.getSize(); i++) {
                    combined.addObject(r.getObject(i));
                }
                if (combined.getSize() <= LEAF_MAX
                    || combined.hasCommonIntersectionPublic()) {
                    return combined;
                }
            }
            AirObject[] buffer = new AirObject[32];
            int count = gatherObjects(this, buffer, 0);
            if (count <= LEAF_MAX) {
                LeafNode merged = new LeafNode();
                for (int i = 0; i < count; i++) {
                    merged.addObject(buffer[i]);
                }
                return merged;
            }
            return this;
        }

        public void intersect(StringBuilder sb, int x1, int y1, int z1,
            int w1, int h1, int d1, int x2, int y2, int z2, int w2, int h2,
            int d2, int depth, int[] visited) {
            if (!overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2)) {
                return;
            }
            visited[0]++;
            spacing(sb, depth);
            sb.append("In Internal node (").append(x2).append(", ")
                .append(y2).append(", ");
            sb.append(z2).append(", ").append(w2).append(", ").append(h2)
                .append(", ");
            sb.append(d2).append(") ").append(depth).append("\r\n");

            int split = depth % 3;
            if (split == 0) {
                int half = w2 / 2;
                this.left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2,
                    half, h2, d2, depth + 1, visited);
                this.right.intersect(sb, x1, y1, z1, w1, h1, d1, x2 + half,
                    y2, z2, w2 - half, h2, d2, depth + 1, visited);
            }
            else if (split == 1) {
                int half = h2 / 2;
                this.left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2,
                    w2, half, d2, depth + 1, visited);
                this.right.intersect(sb, x1, y1, z1, w1, h1, d1, x2,
                    y2 + half, z2, w2, h2 - half, d2, depth + 1, visited);
            }
            else {
                int half = d2 / 2;
                this.left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2,
                    w2, h2, half, depth + 1, visited);
                this.right.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2,
                    z2 + half, w2, h2, d2 - half, depth + 1, visited);
            }
        }

        public void collisions(StringBuilder sb, int x, int y, int z, int w,
            int h, int d, int depth) {
            int split = depth % 3;
            // X split then y split then z split
            if (split == 0) {
                int half = w / 2;
                this.left.collisions(sb, x, y, z, half, h, d, depth + 1);
                this.right.collisions(sb, x + half, y, z, w - half, h, d,
                    depth + 1);
            }
            else if (split == 1) {
                int half = h / 2;
                this.left.collisions(sb, x, y, z, w, half, d, depth + 1);
                this.right.collisions(sb, x, y + half, z, w, h - half, d,
                    depth + 1);
            }
            else {
                int half = d / 2;
                this.left.collisions(sb, x, y, z, w, h, half, depth + 1);
                this.right.collisions(sb, x, y, z + half, w, h, d - half,
                    depth + 1);
            }
        }
    }
}