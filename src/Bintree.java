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
     * This removes an AirObject from the tree
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
        StringBuilder sb = new StringBuilder();
        sb.append("The following objects intersect (");
        sb.append(x).append(", ").append(y).append(", ").append(z).append(", ");
        sb.append(xwid).append(", ").append(ywid).append(" ").append(zwid).append("):\r\n");
        int nodesVisited;
        if (root == FLYWEIGHT)
        {
            nodesVisited = 1;
        }
        else
        {
            nodesVisited = root.intersect(sb, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE,
                x, y, z, xwid, ywid, zwid, 0);
        }
        visited = nodesVisited;
        sb.append(nodesVisited).append(" nodes were visited in the bintree\r\n");
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
        BinNode remove(AirObject obj, int x, int y, int z, int w, int h, int d, int depth);
        int intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
                       int x2, int y2, int z2, int w2, int h2, int d2, int depth);
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
        public BinNode remove(AirObject obj, int x, int y, int z, int w, int h, int d, int depth)
        {
            return this;
        }
        public int intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
            int x2, int y2, int z2, int w2, int h2, int d2, int depth)
        {
            return 0;
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
            if (size >= objects.length)
            {
                AirObject[] temp = new AirObject[objects.length + 1];
                for (int j = 0; j < size; j++)
                {
                    temp[j] = objects[j];
                }
                objects = temp;
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
            if (size < LEAF_MAX)
            {
                addObject(obj);
                return this;
            }

            if (commonIntersection(obj))
            {
                addObject(obj);
                return this;
            }

            InternalNode internal = new InternalNode();
            for (int i = 0; i < size; i++)
            {
                internal.insert(objects[i], x, y, z, w, h, d, depth);
            }
            internal.insert(obj, x, y, z, w, h, d, depth);
            return internal;
        }
        public BinNode remove(AirObject obj, int x, int y, int z, int w, int h, int d, int depth)
        {
            int index = -1;
            for (int i = 0; i < size; i++)
            {
                if (objects[i] == obj)
                {
                    index = i;
                    break;
                }
            }

            if (index == -1)
            {
                return this;
            }

            for (int i = index; i < size - 1; i++)
            {
                objects[i] = objects[i + 1];
            }
            size--;

            if (size == 0)
            {
                return FLYWEIGHT;
            }
            return this;
        }
        public int intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
            int x2, int y2, int z2, int w2, int h2, int d2, int depth)
        {
            if (!overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2))
            {
                return 0;
            }
            if (x2 < x1 || x2 >= x1 + w1 || y2 < y1 || y2 >= y1 + h1 || z2 < z1
                || z2 >= z1 + d1)
            {
                return 1;
            }
            sb.append("In leaf node (").append(x1).append(", ").append(y1).append(", ");
            sb.append(z1).append(", ").append(w1).append(", ").append(h1).append(", ");
            sb.append(d1).append(") ").append(depth).append("\r\n");
            for (int i = 0; i < size; i++)
            {
                int ox = objects[i].getXorig();
                int oy = objects[i].getYorig();
                int oz = objects[i].getZorig();
                int ow = objects[i].getXwidth();
                int oh = objects[i].getYwidth();
                int od = objects[i].getZwidth();
                if (overlap(ox, oy, oz, ow, oh, od, x2, y2, z2, w2, h2, d2))
                {
                    int ix = Math.max(ox, x2);
                    int iy = Math.max(oy, y2);
                    int iz = Math.max(oz, z2);
                    if (ix >= x1 && ix < x1 + w1 && iy >= y1 && iy < y1 + h1 &&
                        iz >= z1 && iz < z1 + d1)
                    {
                        sb.append(objects[i].toString()).append("\r\n");
                    }
                }
            }
            return 1;
        }
        public void collisions(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth)
        {
            sb.append("In leaf node (").append(x).append(", ").append(y).append(", ");
            sb.append(z).append(", ").append(w).append(", ").append(h).append(", ");
            sb.append(d).append(") ").append(depth).append("\r\n");

            for (int i = 0; i < size; i++)
            {
                for (int j = i + 1; j < size; j++)
                {
                    int x1 = objects[i].getXorig();
                    int y1 = objects[i].getYorig();
                    int z1 = objects[i].getZorig();
                    int w1 = objects[i].getXwidth();
                    int h1 = objects[i].getYwidth();
                    int d1 = objects[i].getZwidth();
                    int x2 = objects[j].getXorig();
                    int y2 = objects[j].getYorig();
                    int z2 = objects[j].getZorig();
                    int w2 = objects[j].getXwidth();
                    int h2 = objects[j].getYwidth();
                    int d2 = objects[j].getZwidth();

                    if (overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2))
                    {
                        int ix = Math.max(x1, x2);
                        int iy = Math.max(y1, y2);
                        int iz = Math.max(z1, z2);
                        if (ix >= x && ix < x + w && iy >= y && iy < y + h && iz >= z && iz < z + d)
                        {
                            sb.append("(").append(objects[i].toString()).append(") and (");
                            sb.append(objects[j].toString()).append(")\r\n");
                        }
                    }
                }
            }
        }

        public int getSize()
        {
            return size;
        }

        public AirObject[] getObjects()
        {
            AirObject[] result = new AirObject[size];
            for (int i = 0; i < size; i++)
            {
                result[i] = objects[i];
            }
            return result;
        }

        public boolean contains(AirObject obj)
        {
            for (int i = 0; i < size; i++)
            {
                if (objects[i] == obj)
                {
                    return true;
                }
            }
            return false;
        }

        private boolean commonIntersection(AirObject obj)
        {
            if (size == 0)
            {
                return false;
            }

            int maxX = objects[0].getXorig();
            int maxY = objects[0].getYorig();
            int maxZ = objects[0].getZorig();
            int minX = objects[0].getXorig() + objects[0].getXwidth();
            int minY = objects[0].getYorig() + objects[0].getYwidth();
            int minZ = objects[0].getZorig() + objects[0].getZwidth();

            for (int i = 1; i < size; i++)
            {
                maxX = Math.max(maxX, objects[i].getXorig());
                maxY = Math.max(maxY, objects[i].getYorig());
                maxZ = Math.max(maxZ, objects[i].getZorig());
                minX = Math.min(minX, objects[i].getXorig() + objects[i].getXwidth());
                minY = Math.min(minY, objects[i].getYorig() + objects[i].getYwidth());
                minZ = Math.min(minZ, objects[i].getZorig() + objects[i].getZwidth());
            }

            maxX = Math.max(maxX, obj.getXorig());
            maxY = Math.max(maxY, obj.getYorig());
            maxZ = Math.max(maxZ, obj.getZorig());
            minX = Math.min(minX, obj.getXorig() + obj.getXwidth());
            minY = Math.min(minY, obj.getYorig() + obj.getYwidth());
            minZ = Math.min(minZ, obj.getZorig() + obj.getZwidth());

            return maxX < minX && maxY < minY && maxZ < minZ;
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
                int leftW = half;
                int rightW = w - half;
                if (overlap(x, y, z, leftW, h, d, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.insert(obj, x, y, z, leftW, h, d, depth + 1);
                }
                if (overlap(x + half, y, z, rightW, h, d, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    right = right.insert(obj, x + half, y, z, rightW, h, d, depth + 1);
                }
            }
            else if (split == 1)
            {
                int half = h / 2;
                int topH = half;
                int bottomH = h - half;
                if (overlap(x, y, z, w, topH, d, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.insert(obj, x, y, z, w, topH, d, depth + 1);
                }
                if (overlap(x, y + half, z, w, bottomH, d, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    right = right.insert(obj, x, y + half, z, w, bottomH, d, depth + 1);
                }
            }
            else
            {
                int half = d / 2;
                int frontD = half;
                int backD = d - half;
                if (overlap(x, y, z, w, h, frontD, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.insert(obj, x, y, z, w, h, frontD, depth + 1);
                }
                if (overlap(x, y, z + half, w, h, backD, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    right = right.insert(obj, x, y, z + half, w, h, backD, depth + 1);
                }
            }
            return this;
        }
        public BinNode remove(AirObject obj, int x, int y, int z, int w, int h, int d, int depth)
        {
            int split = depth % 3;
            if (split == 0)
            {
                int half = w / 2;
                int leftW = half;
                int rightW = w - half;
                if (overlap(x, y, z, leftW, h, d, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.remove(obj, x, y, z, leftW, h, d, depth + 1);
                }
                if (overlap(x + half, y, z, rightW, h, d, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    right = right.remove(obj, x + half, y, z, rightW, h, d, depth + 1);
                }
            }
            else if (split == 1)
            {
                int half = h / 2;
                int topH = half;
                int bottomH = h - half;
                if (overlap(x, y, z, w, topH, d, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.remove(obj, x, y, z, w, topH, d, depth + 1);
                }
                if (overlap(x, y + half, z, w, bottomH, d, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    right = right.remove(obj, x, y + half, z, w, bottomH, d, depth + 1);
                }
            }
            else
            {
                int half = d / 2;
                int frontD = half;
                int backD = d - half;
                if (overlap(x, y, z, w, h, frontD, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    left = left.remove(obj, x, y, z, w, h, frontD, depth + 1);
                }
                if (overlap(x, y, z + half, w, h, backD, obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth()))
                {
                    right = right.remove(obj, x, y, z + half, w, h, d - half, depth + 1);
                }
            }

            if (left instanceof LeafNode && right instanceof LeafNode)
            {
                LeafNode merged = new LeafNode();
                for (AirObject o : ((LeafNode)left).getObjects())
                {
                    merged.addObject(o);
                }
                for (AirObject o : ((LeafNode)right).getObjects())
                {
                    if (!merged.contains(o))
                    {
                        merged.addObject(o);
                    }
                }
                if (merged.getSize() <= LEAF_MAX)
                {
                    return merged;
                }
            }

            if (left == FLYWEIGHT && right == FLYWEIGHT)
            {
                return FLYWEIGHT;
            }
            if (left == FLYWEIGHT)
            {
                return right;
            }
            if (right == FLYWEIGHT)
            {
                return left;
            }
            return this;
        }
        public int intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
                             int x2, int y2, int z2, int w2, int h2, int d2, int depth)
        {
            if (!overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2))
            {
                return 0;
            }

            sb.append("In Internal node (").append(x1).append(", ").append(y1).append(", ");
            sb.append(z1).append(", ").append(w1).append(", ").append(h1).append(", ");
            sb.append(d1).append(") ").append(depth).append("\r\n");

            int split = depth % 3;
            int visitedNodes = 1;
            if (split == 0)
            {
                int half = w1 / 2;
                visitedNodes += left.intersect(sb, x1, y1, z1, half, h1, d1,
                    x2, y2, z2, w2, h2, d2, depth + 1);
                visitedNodes += right.intersect(sb, x1 + half, y1, z1, w1 - half, h1, d1,
                    x2, y2, z2, w2, h2, d2, depth + 1);
            }
            else if (split == 1)
            {
                int half = h1 / 2;
                visitedNodes += left.intersect(sb, x1, y1, z1, w1, half, d1,
                    x2, y2, z2, w2, h2, d2, depth + 1);
                visitedNodes += right.intersect(sb, x1, y1 + half, z1, w1, h1 - half, d1,
                    x2, y2, z2, w2, h2, d2, depth + 1);
            }
            else
            {
                int half = d1 / 2;
                visitedNodes += left.intersect(sb, x1, y1, z1, w1, h1, half,
                    x2, y2, z2, w2, h2, d2, depth + 1);
                visitedNodes += right.intersect(sb, x1, y1, z1 + half, w1, h1, d1 - half,
                    x2, y2, z2, w2, h2, d2, depth + 1);
            }
            return visitedNodes;
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