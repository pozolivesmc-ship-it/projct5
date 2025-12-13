
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
        sb.append(x).append(' ').append(y).append(' ').append(z).append(' ');
        sb.append(xwid).append(' ').append(ywid).append(' ').append(zwid);
        sb.append("):\r\n");
        int[] counter = new int[1];
        if (root == FLYWEIGHT)
        {
            counter[0] = 1;
        }
        else
        {
            root.intersect(sb, x, y, z, xwid, ywid, zwid,0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0, counter);
        }
        visited = counter[0];
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
    private static boolean overlap(int x1, int y1, int z1, int w1, int h1, int d1,
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
                       int x2, int y2, int z2, int w2, int h2, int d2, int depth, int[] visited);
        void collisions(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth);
        BinNode remove(AirObject obj, int x, int y, int z, int w, int h, int d, int depth);
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
            int x2, int y2, int z2, int w2, int h2, int d2, int depth, int[] visited)
        {
            return;
        }
        public void collisions(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth)
        {
            return;
        }
        public BinNode remove(AirObject obj, int x, int y, int z, int w, int h, int d, int depth)
        {
            return this;
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
            ensureCapacity(size + 1);
            int i = size - 1;
            while (i >= 0 && objects[i].compareTo(obj) > 0)
            {
                objects[i + 1] = objects[i];
                i--;
            }
            objects[i + 1] = obj;
            size++;
        }
        private void ensureCapacity(int capacity)
        {
            if (capacity <= objects.length)
            {
                return;
            }
            AirObject[] newArr = new AirObject[objects.length * 2];
            for (int i = 0; i < size; i++)
            {
                newArr[i] = objects[i];
            }
            objects = newArr;
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
            if (!overlap(x, y, z, w, h, d,
                obj.getXorig(), obj.getYorig(), obj.getZorig(),
                obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
            {
                return this;
            }
            if (size < LEAF_MAX)
            {
                addObject(obj);
                return this;
            }
            ensureCapacity(size + 1);
            objects[size] = obj;
            size++;
            if (hasCommonIntersection())
            {
                sortObjects();
                return this;
            }
            InternalNode internal = new InternalNode();
            for (int i = 0; i < size; i++)
            {
                internal.insert(objects[i], x, y, z, w, h, d, depth);
            }
            return internal;
        }
        public void intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
            int x2, int y2, int z2, int w2, int h2, int d2, int depth, int[] visited)
        {
            if (!overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2))
            {
                return;
            }
            visited[0]++;
            sb.append("In leaf node (").append(x1).append(", ").append(y1).append(", ")
                .append(z1).append(", ").append(w1).append(", ").append(h1)
                .append(", ").append(d1).append(") ").append(depth).append("\r\n");
            for (int i = 0; i < size; i++)
            {
                AirObject obj = objects[i];
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(),
                    x2, y2, z2, w2, h2, d2))
                {
                    spacing(sb, depth + 1);
                    sb.append(obj.toString()).append("\r\n");
                }
            }
        }
        public void collisions(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth)
        {
            if (size > 0)
            {
                sb.append("In leaf node (").append(x).append(", ").append(y)
                    .append(", ").append(z).append(", ").append(w).append(", ")
                    .append(h).append(", ").append(d).append(") ").append(depth)
                    .append("\r\n");
            }
            for (int i = 0; i < size; i++)
            {
                for (int j = i + 1; j < size; j++)
                {
                    AirObject first = objects[i];
                    AirObject second = objects[j];
                    if (overlap(first.getXorig(), first.getYorig(), first.getZorig(),
                        first.getXwidth(), first.getYwidth(), first.getZwidth(),
                        second.getXorig(), second.getYorig(), second.getZorig(),
                        second.getXwidth(), second.getYwidth(), second.getZwidth()))
                    {
                        int ox = Math.max(first.getXorig(), second.getXorig());
                        int oy = Math.max(first.getYorig(), second.getYorig());
                        int oz = Math.max(first.getZorig(), second.getZorig());
                        if (ox >= x && ox < x + w && oy >= y && oy < y + h
                            && oz >= z && oz < z + d)
                        {
                            sb.append('(').append(first.toString()).append(") and (")
                                .append(second.toString()).append(")\r\n");
                        }
                    }
                }
            }
        }
        private void sortObjects()
        {
            for (int i = 1; i < size; i++)
            {
                AirObject key = objects[i];
                int j = i - 1;
                while (j >= 0 && objects[j].compareTo(key) > 0)
                {
                    objects[j + 1] = objects[j];
                    j--;
                }
                objects[j + 1] = key;
            }
        }
        private boolean hasCommonIntersection()
        {
            if (size == 0)
            {
                return false;
            }
            int xStart = objects[0].getXorig();
            int yStart = objects[0].getYorig();
            int zStart = objects[0].getZorig();
            int xEnd = xStart + objects[0].getXwidth();
            int yEnd = yStart + objects[0].getYwidth();
            int zEnd = zStart + objects[0].getZwidth();
            for (int i = 1; i < size; i++)
            {
                AirObject obj = objects[i];
                xStart = Math.max(xStart, obj.getXorig());
                yStart = Math.max(yStart, obj.getYorig());
                zStart = Math.max(zStart, obj.getZorig());
                xEnd = Math.min(xEnd, obj.getXorig() + obj.getXwidth());
                yEnd = Math.min(yEnd, obj.getYorig() + obj.getYwidth());
                zEnd = Math.min(zEnd, obj.getZorig() + obj.getZwidth());
            }
            return xStart < xEnd && yStart < yEnd && zStart < zEnd;
        }
        public BinNode remove(AirObject obj, int x, int y, int z, int w, int h, int d, int depth)
        {
            for (int i = 0; i < size; i++)
            {
                if (objects[i].getName().equals(obj.getName()))
                {
                    for (int j = i; j < size - 1; j++)
                    {
                        objects[j] = objects[j + 1];
                    }
                    size--;
                    break;
                }
            }
            if (size == 0)
            {
                return FLYWEIGHT;
            }
            return this;
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
                if (overlap(x, y, z, half, h, d, obj.getXorig(), obj.getYorig(),
                    obj.getZorig(), obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.insert(obj, x, y, z, half, h, d, depth + 1);
                }
                if (overlap(x + half, y, z, w - half, h, d, obj.getXorig(),
                    obj.getYorig(), obj.getZorig(), obj.getXwidth(), obj.getYwidth(),
                    obj.getZwidth()))
                {
                    right = right.insert(obj, x + half, y, z, w - half, h, d, depth + 1);
                }
            }
            else if (split == 1)
            {
                int half = h / 2;
                if (overlap(x, y, z, w, half, d, obj.getXorig(), obj.getYorig(),
                    obj.getZorig(), obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.insert(obj, x, y, z, w, half, d, depth + 1);
                }
                if (overlap(x, y + half, z, w, h - half, d, obj.getXorig(),
                    obj.getYorig(), obj.getZorig(), obj.getXwidth(), obj.getYwidth(),
                    obj.getZwidth()))
                {
                    right = right.insert(obj, x, y + half, z, w, h - half, d, depth + 1);
                }
            }
            else
            {
                int half = d / 2;
                if (overlap(x, y, z, w, h, half, obj.getXorig(), obj.getYorig(),
                    obj.getZorig(), obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.insert(obj, x, y, z, w, h, half, depth + 1);
                }
                if (overlap(x, y, z + half, w, h, d - half, obj.getXorig(),
                    obj.getYorig(), obj.getZorig(), obj.getXwidth(), obj.getYwidth(),
                    obj.getZwidth()))
                {
                    right = right.insert(obj, x, y, z + half, w, h, d - half, depth + 1);
                }
            }
            return this;
        }
        public void intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
                             int x2, int y2, int z2, int w2, int h2, int d2, int depth, int[] visited)
        {
            if (!overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2))
            {
                return;
            }
            visited[0]++;
            sb.append("In Internal node (").append(x1).append(", ").append(y1).append(", ")
                .append(z1).append(", ").append(w1).append(", ").append(h1).append(", ")
                .append(d1).append(") ").append(depth).append("\r\n");
            int split = depth % 3;
            if (split == 0)
            {
                int half = w1 / 2;
                left.intersect(sb, x1, y1, z1, half, h1, d1, x2, y2, z2, w2, h2, d2,
                    depth + 1, visited);
                right.intersect(sb, x1 + half, y1, z1, w1 - half, h1, d1, x2, y2, z2,
                    w2, h2, d2, depth + 1, visited);
            }
            else if (split == 1)
            {
                int half = h1 / 2;
                left.intersect(sb, x1, y1, z1, w1, half, d1, x2, y2, z2, w2, h2, d2,
                    depth + 1, visited);
                right.intersect(sb, x1, y1 + half, z1, w1, h1 - half, d1, x2, y2, z2,
                    w2, h2, d2, depth + 1, visited);
            }
            else
            {
                int half = d1 / 2;
                left.intersect(sb, x1, y1, z1, w1, h1, half, x2, y2, z2, w2, h2, d2,
                    depth + 1, visited);
                right.intersect(sb, x1, y1, z1 + half, w1, h1, d1 - half, x2, y2, z2,
                    w2, h2, d2, depth + 1, visited);
            }
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
        public BinNode remove(AirObject obj, int x, int y, int z, int w, int h, int d, int depth)
        {
            int split = depth % 3;
            if (split == 0)
            {
                int half = w / 2;
                if (overlap(x, y, z, half, h, d, obj.getXorig(), obj.getYorig(),
                    obj.getZorig(), obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.remove(obj, x, y, z, half, h, d, depth + 1);
                }
                if (overlap(x + half, y, z, w - half, h, d, obj.getXorig(),
                    obj.getYorig(), obj.getZorig(), obj.getXwidth(), obj.getYwidth(),
                    obj.getZwidth()))
                {
                    right = right.remove(obj, x + half, y, z, w - half, h, d, depth + 1);
                }
            }
            else if (split == 1)
            {
                int half = h / 2;
                if (overlap(x, y, z, w, half, d, obj.getXorig(), obj.getYorig(),
                    obj.getZorig(), obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.remove(obj, x, y, z, w, half, d, depth + 1);
                }
                if (overlap(x, y + half, z, w, h - half, d, obj.getXorig(),
                    obj.getYorig(), obj.getZorig(), obj.getXwidth(), obj.getYwidth(),
                    obj.getZwidth()))
                {
                    right = right.remove(obj, x, y + half, z, w, h - half, d, depth + 1);
                }
            }
            else
            {
                int half = d / 2;
                if (overlap(x, y, z, w, h, half, obj.getXorig(), obj.getYorig(),
                    obj.getZorig(), obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.remove(obj, x, y, z, w, h, half, depth + 1);
                }
                if (overlap(x, y, z + half, w, h, d - half, obj.getXorig(),
                    obj.getYorig(), obj.getZorig(), obj.getXwidth(), obj.getYwidth(),
                    obj.getZwidth()))
                {
                    right = right.remove(obj, x, y, z + half, w, h, d - half, depth + 1);
                }
            }
            if (left == FLYWEIGHT && right == FLYWEIGHT)
            {
                return FLYWEIGHT;
            }
            return this;
        }
    }
    /**
     * Removes an object from the tree
     * @param obj object to remove
     */
    public void remove(AirObject obj)
    {
        if (obj == null)
        {
            return;
        }
        root = root.remove(obj, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0);
    }
}
