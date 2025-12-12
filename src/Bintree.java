
import java.util.HashSet;

/**
 * This is the Bintree class
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
     * This is the constructor for Bintree
     * The root is an EmptyNode, depth = 0
     */
    public Bintree() 
    {
        root = FLYWEIGHT; 
    }
    /**
     * This is the insert method for Bintree
     * @param obj is the object inserted
     */
    public void insert(AirObject obj)
    {
        if (obj == null)
        {
            return;
        }
        root = root.insert(obj, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0);
    }
    /**
     * This is the printTree method
     * @return String for the tree
     */
    public String printTree() 
    {
        StringBuilder sb = new StringBuilder();
        root.print(sb, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0);
        sb.append(root.countNodes()).append(" Bintree nodes printed\r\n");
        return sb.toString();
    }
    private static void spacing(StringBuilder sb, int depth)
    {
        for (int i = 0; i < depth; i++)
        {
            sb.append("  ");
        }
    }
    public String intersect(int x, int y, int z, int xwid, int ywid, int zwid)
    {
        visited = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("The following objects intersect (");
        sb.append(x).append(", ").append(y).append(", ").append(z).append(", ");
        sb.append(xwid).append(", ").append(ywid).append(" ").append(zwid).append("):\r\n");
        if (root == FLYWEIGHT)
        {
            visited = 1;
        }
        else
        {
            HashSet<String> seen = new HashSet<>();
            visited = root.intersect(sb, x, y, z, xwid, ywid, zwid, 0, 0, 0,
                WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0, seen);
        }
        sb.append(visited).append(" nodes were visited in the bintree\r\n");
        return sb.toString();
    }
    public String collisions()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("The following collisions exist in the database:\r\n");
        root.collisions(sb, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0);
        return sb.toString();
    }
    /**
     * This is a helper method testing if there's any overlapping
     * @return true or false if it overlaps
     */
    private static boolean overlap(int x1, int y1, int z1, int w1, int h1,
        int d1, int x2, int y2, int z2, int w2, int h2, int d2)
    {
        boolean xOverlap = x1 < x2 + w2 && x2 < x1 + w1;
        boolean yOverlap = y1 < y2 + h2 && y2 < y1 + h1;
        boolean zOverlap = z1 < z2 + d2 && z2 < z1 + d1;
        return xOverlap && yOverlap && zOverlap;
    }
    /**
     * This is the interface for BinNode 
     */
    private interface BinNode
    {
        void print(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth);
        int countNodes();
        BinNode insert(AirObject obj, int x, int y, int z, int w, int h, int d, int depth);
        int intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1,
            int d1, int x2, int y2, int z2, int w2, int h2, int d2, int depth,
            HashSet<String> seen);
        void collisions(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth);
    }
    
    /**
     * This is the EmptyNode class
     */
    private static class EmptyNode implements BinNode {
        public void print(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth)
        {
            spacing(sb, depth);
            sb.append("E (").append(x).append(", ").append(y).append(", ");
            sb.append(z).append(", ").append(w).append(", ").append(h).append(", ");
            sb.append(d).append(") ").append(depth).append("\r\n");
        }
        public int countNodes()
        {
            return 1;
        }
        public BinNode insert(AirObject obj, int x, int y, int z, int w, int h, int d, int depth)
        {
            LeafNode leaf = new LeafNode();
            leaf.addObject(obj);
            return leaf;
        }
        public int intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1,
            int d1, int x2, int y2, int z2, int w2, int h2, int d2, int depth,
            HashSet<String> seen)
        {
            return 1;
        }
        public void collisions(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth)
        {
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
            objects = new AirObject[LEAF_MAX];
            size = 0;
        }
        
        public void addObject(AirObject obj)
        {
            if (size >= LEAF_MAX)
            {
                return;
            }
            int i = size - 1;
            while (i >= 0 && objects[i].compareTo(obj) > 0)
            {
                objects[i + 1] = objects[i];
                i--;
            }
            objects[i + 1] = obj;
            size++;
        }
        public void print(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth)
        {
            spacing(sb, depth);
            sb.append("Leaf with ").append(size).append(" objects (");
            sb.append(x).append(", ").append(y).append(", ");
            sb.append(z).append(", ").append(w).append(", ").append(h).append(", ");
            sb.append(d).append(") ").append(depth).append("\r\n");
            for (int i = 0; i < size; i++)
            {
                spacing(sb, depth + 1);
                sb.append("(").append(objects[i].toString()).append(")\r\n");
            }
        }
        public int countNodes()
        {
            return 1;
        }
        public BinNode insert(AirObject obj, int x, int y, int z, int w, int h, int d, int depth)
        {
            //If not full addObject and return
            if (size < LEAF_MAX)
            {
                addObject(obj);
                return this;
            }
            //Leaf is full so split
            InternalNode internal = new InternalNode();
            //Insert each object into internal node and insert new
            for (int i = 0; i < size; i++)
            {
                internal.insert(objects[i], x, y, z, w, h, d, depth);
            }
            internal.insert(obj, x, y, z, w, h, d, depth);
            return internal;
        }
        public int intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1,
            int d1, int x2, int y2, int z2, int w2, int h2, int d2, int depth,
            HashSet<String> seen)
        {
            //Visit this leaf
            spacing(sb, depth);
            sb.append("In leaf node (").append(x2).append(", ").append(y2).append(", ")
                .append(z2).append(", ").append(w2).append(", ").append(h2).append(", ")
                .append(d2).append(") ").append(depth).append("\r\n");
            for (int i = 0; i < size; i++)
            {
                AirObject obj = objects[i];
                if (!seen.contains(obj.getName()) && overlap(x1, y1, z1, w1, h1, d1,
                    obj.getXorig(), obj.getYorig(), obj.getZorig(), obj.getXwidth(),
                    obj.getYwidth(), obj.getZwidth()))
                {
                    seen.add(obj.getName());
                    sb.append(obj.toString()).append("\r\n");
                }
            }
            return 1;
        }
        public void collisions(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth)
        {
            spacing(sb, depth);
            sb.append("In leaf node (").append(x).append(", ").append(y).append(", ")
                .append(z).append(", ").append(w).append(", ").append(h).append(", ")
                .append(d).append(") ").append(depth).append("\r\n");
            for (int i = 0; i < size; i++)
            {
                for (int j = i + 1; j < size; j++)
                {
                    AirObject a = objects[i];
                    AirObject b = objects[j];
                    if (overlap(a.getXorig(), a.getYorig(), a.getZorig(), a.getXwidth(),
                        a.getYwidth(), a.getZwidth(), b.getXorig(), b.getYorig(),
                        b.getZorig(), b.getXwidth(), b.getYwidth(), b.getZwidth()))
                    {
                        sb.append("(").append(a.toString()).append(") and (")
                            .append(b.toString()).append(")\r\n");
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
        
        public InternalNode() {
            left = FLYWEIGHT;
            right = FLYWEIGHT;
        }
        public void print(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth)
        {
            spacing(sb, depth);
            sb.append("I (");
            sb.append(x).append(", ").append(y).append(", ");
            sb.append(z).append(", ").append(w).append(", ").append(h).append(", ");
            sb.append(d).append(") ").append(depth).append("\r\n");
            
            int split = depth % 3;
            //X split then y split then z split
            if (split == 0)
            {
                int half = w / 2;
                left.print(sb, x, y, z, half, h, d, depth + 1);
                right.print(sb, x + half, y, z, w - half, h, d, depth + 1);
            }
            else if (split == 1)
            {
                int half = h / 2;
                left.print(sb, x, y, z, w, half, d, depth + 1);
                right.print(sb, x, y + half, z, w, h - half, d, depth + 1);
            }
            else
            {
                int half = d / 2;
                left.print(sb, x, y, z, w, h, half, depth + 1);
                right.print(sb, x, y, z + half, w, h, d - half, depth + 1);
            }
        }
        public int countNodes()
        {
            return 1 + left.countNodes() + right.countNodes();
        }
        public BinNode insert(AirObject obj, int x, int y, int z, int w, int h, int d, int depth)
        {
            int split = depth % 3;
            if (split == 0)
            {
                int half = w / 2;
                int rw = w - half;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, half, h, d))
                {
                    left = left.insert(obj, x, y, z, half, h, d, depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x + half, y, z, rw, h, d))
                {
                    right = right.insert(obj, x + half, y, z, rw, h, d, depth + 1);
                }
            }
            else if (split == 1)
            {
                int half = h / 2;
                int rh = h - half;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, w, half, d))
                {
                    left = left.insert(obj, x, y, z, w, half, d, depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y + half, z, w, rh, d))
                {
                    right = right.insert(obj, x, y + half, z, w, rh, d, depth + 1);
                }
            }
            else
            {
                int half = d / 2;
                int rd = d - half;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, w, h, half))
                {
                    left = left.insert(obj, x, y, z, w, h, half, depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z + half, w, h, rd))
                {
                    right = right.insert(obj, x, y, z + half, w, h, rd, depth + 1);
                }
            }
            return this;
        }
        public int intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
            int x2, int y2, int z2, int w2, int h2, int d2, int depth,
            HashSet<String> seen)
        {
            int count = 1;
            spacing(sb, depth);
            sb.append("In Internal node (").append(x2).append(", ").append(y2)
                .append(", ").append(z2).append(", ").append(w2).append(", ")
                .append(h2).append(", ").append(d2).append(") ")
                .append(depth).append("\r\n");
            int split = depth % 3;
            if (split == 0)
            {
                int half = w2 / 2;
                int rw = w2 - half;
                if (overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, half, h2, d2)
                    && left != FLYWEIGHT)
                {
                    count += left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2,
                        half, h2, d2, depth + 1, seen);
                }
                if (overlap(x1, y1, z1, w1, h1, d1, x2 + half, y2, z2, rw, h2, d2)
                    && right != FLYWEIGHT)
                {
                    count += right.intersect(sb, x1, y1, z1, w1, h1, d1, x2 + half, y2,
                        z2, rw, h2, d2, depth + 1, seen);
                }
            }
            else if (split == 1)
            {
                int half = h2 / 2;
                int rh = h2 - half;
                if (overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, half, d2)
                    && left != FLYWEIGHT)
                {
                    count += left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2,
                        w2, half, d2, depth + 1, seen);
                }
                if (overlap(x1, y1, z1, w1, h1, d1, x2, y2 + half, z2, w2, rh, d2)
                    && right != FLYWEIGHT)
                {
                    count += right.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2 + half,
                        z2, w2, rh, d2, depth + 1, seen);
                }
            }
            else
            {
                int half = d2 / 2;
                int rd = d2 - half;
                if (overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, half)
                    && left != FLYWEIGHT)
                {
                    count += left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2,
                        w2, h2, half, depth + 1, seen);
                }
                if (overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2 + half, w2, h2, rd)
                    && right != FLYWEIGHT)
                {
                    count += right.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2,
                        z2 + half, w2, h2, rd, depth + 1, seen);
                }
            }
            return count;
        }
        public void collisions(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth)
        {
            int split = depth % 3;
            //X split then y split then z split
            if (split == 0)
            {
                int half = w / 2;
                left.collisions(sb, x, y, z, half, h, d, depth + 1);
                right.collisions(sb, x + half, y, z, w - half, h, d, depth + 1);
            }
            else if (split == 1)
            {
                int half = h / 2;
                left.collisions(sb, x, y, z, w, half, d, depth + 1);
                right.collisions(sb, x, y + half, z, w, h - half, d, depth + 1);
            }
            else
            {
                int half = d / 2;
                left.collisions(sb, x, y, z, w, h, half, depth + 1);
                right.collisions(sb, x, y, z + half, w, h, d - half, depth + 1);
            }
        }
    }
}    
