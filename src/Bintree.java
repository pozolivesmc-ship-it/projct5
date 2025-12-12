
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
     * Helper to check if a name has already been collected in the intersect
     * traversal without relying on disallowed collection types.
     *
     * @param names array of names already recorded
     * @param count number of occupied entries in {@code names}
     * @param target the name to search for
     * @return {@code true} if {@code target} is present in {@code names}
     */
    private static boolean hasName(String[] names, int count, String target)
    {
        for (int i = 0; i < count; i++)
        {
            if (names[i].equals(target))
            {
                return true;
            }
        }
        return false;
    }
    
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

        // Track the names added during traversal without using collections that are
        // disallowed in the environment.
        String[] intersectedNames = new String[WORLD_SIZE];
        int[] nameCount = { 0 };

        if (root == FLYWEIGHT)
        {
            visited = 1;
        }
        else
        {
            int[] visitedCount = { 0 };
            root.intersect(sb, x, y, z, xwid, ywid, zwid, 0, 0, 0,
                WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0, visitedCount,
                intersectedNames, nameCount);
            visited = visitedCount[0];
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
    private boolean overlap(int x1, int y1, int z1, int w1, int h1, int d1,
                            int x2, int y2, int z2, int w2, int h2, int d2)
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
        void intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
                       int x2, int y2, int z2, int w2, int h2, int d2, int depth,
                       int[] visited, String[] names, int[] nameCount);
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
        public void intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
            int x2, int y2, int z2, int w2, int h2, int d2, int depth,
            int[] visited, String[] names, int[] nameCount)
        {
            return;
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
        public void intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
            int x2, int y2, int z2, int w2, int h2, int d2, int depth,
            int[] visited, String[] names, int[] nameCount)
        {
            visited[0]++;
            if (!overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2))
            {
                return;
            }

            spacing(sb, depth);
            sb.append("In leaf node (").append(x2).append(", ").append(y2)
                .append(", ").append(z2).append(", ").append(w2).append(", ")
                .append(h2).append(", ").append(d2).append(") ")
                .append(depth).append("\r\n");

            for (int i = 0; i < size; i++)
            {
                AirObject obj = objects[i];
                if (overlap(x1, y1, z1, w1, h1, d1, obj.getXorig(),
                    obj.getYorig(), obj.getZorig(), obj.getXwidth(),
                    obj.getYwidth(), obj.getZwidth()))
                {
                    if (!hasName(names, nameCount[0], obj.getName()))
                    {
                        names[nameCount[0]] = obj.getName();
                        nameCount[0]++;
                        spacing(sb, depth + 1);
                        sb.append(obj.toString()).append("\r\n");
                    }
                }
            }
        }
        public void collisions(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth)
        {
            //Finish
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
            //Finish this
            return this;
        }
        public void intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
                             int x2, int y2, int z2, int w2, int h2, int d2, int depth,
                             int[] visited, String[] names, int[] nameCount)
        {
            //Finish
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
