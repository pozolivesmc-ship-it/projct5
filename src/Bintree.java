
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
    private static int visited;
    
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
     * This deletes an AirObject from the Bintree
     * @param obj The object to delete
     */
    public void delete(AirObject obj)
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
            root.intersect(sb, x, y, z, xwid, ywid, zwid, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0,
                containsPoint(x, y, z, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE));
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

    private static boolean containsPoint(int px, int py, int pz, int x, int y, int z, int w, int h, int d)
    {
        return px >= x && px < x + w && py >= y && py < y + h && pz >= z && pz < z + d;
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
        void intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
                       int x2, int y2, int z2, int w2, int h2, int d2, int depth, boolean allowEmit);
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
        public void intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
            int x2, int y2, int z2, int w2, int h2, int d2, int depth, boolean allowEmit)
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
            AirObject[] temp = new AirObject[size + 1];
            int i = size - 1;
            while (i >= 0 && objects[i].compareTo(obj) > 0)
            {
                temp[i + 1] = objects[i];
                i--;
            }
            temp[i + 1] = obj;
            while (i >= 0)
            {
                temp[i] = objects[i];
                i--;
            }
            boolean allIntersect = true;
            for (int a = 0; a < temp.length && allIntersect; a++)
            {
                for (int b = a + 1; b < temp.length; b++)
                {
                    if (!overlap(temp[a].getXorig(), temp[a].getYorig(), temp[a].getZorig(),
                        temp[a].getXwidth(), temp[a].getYwidth(), temp[a].getZwidth(),
                        temp[b].getXorig(), temp[b].getYorig(), temp[b].getZorig(),
                        temp[b].getXwidth(), temp[b].getYwidth(), temp[b].getZwidth()))
                    {
                        allIntersect = false;
                        break;
                    }
                }
            }
            if (allIntersect)
            {
                objects = temp;
                size++;
                return this;
            }
            //Leaf is full so split
            InternalNode internal = new InternalNode();
            for (int a = 0; a < temp.length; a++)
            {
                internal.insert(temp[a], x, y, z, w, h, d, depth);
            }
            return internal;
        }
        public BinNode remove(AirObject obj, int x, int y, int z, int w, int h, int d, int depth)
        {
            for (int i = 0; i < size; i++)
            {
                if (objects[i] == obj)
                {
                    for (int j = i; j < size - 1; j++)
                    {
                        objects[j] = objects[j + 1];
                    }
                    size--;
                    objects[size] = null;
                    break;
                }
            }
            if (size == 0)
            {
                return FLYWEIGHT;
            }
            return this;
        }
        public void intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
            int x2, int y2, int z2, int w2, int h2, int d2, int depth, boolean allowEmit)
        {
            Bintree.visited++;
            if (!overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2))
            {
                return;
            }
            if (!allowEmit)
            {
                return;
            }
            sb.append("In leaf node (").append(x2).append(", ").append(y2).append(", ")
              .append(z2).append(", ").append(w2).append(", ").append(h2).append(", ")
              .append(d2).append(") ").append(depth).append("\r\n");
            for (int i = 0; i < size; i++)
            {
                AirObject obj = objects[i];
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(),
                    x1, y1, z1, w1, h1, d1))
                {
                    int interX = Math.max(obj.getXorig(), x1);
                    int interY = Math.max(obj.getYorig(), y1);
                    int interZ = Math.max(obj.getZorig(), z1);
                    if (interX >= x2 && interX < x2 + w2 && interY >= y2
                        && interY < y2 + h2 && interZ >= z2 && interZ < z2 + d2)
                    {
                        sb.append(obj.toString()).append("\r\n");
                    }
                }
            }
        }
        public void collisions(StringBuilder sb, int x, int y, int z, int w, int h, int d, int depth)
        {
            sb.append("In leaf node (").append(x).append(", ").append(y).append(", ")
                .append(z).append(", ").append(w).append(", ").append(h).append(", ")
                .append(d).append(") ").append(depth).append("\r\n");
            if (size < 2)
            {
                return;
            }
            for (int i = 0; i < size; i++)
            {
                AirObject first = objects[i];
                for (int j = i + 1; j < size; j++)
                {
                    AirObject second = objects[j];
                    if (overlap(first.getXorig(), first.getYorig(), first.getZorig(),
                        first.getXwidth(), first.getYwidth(), first.getZwidth(),
                        second.getXorig(), second.getYorig(), second.getZorig(),
                        second.getXwidth(), second.getYwidth(), second.getZwidth()))
                    {
                        int interX = Math.max(first.getXorig(), second.getXorig());
                        int interY = Math.max(first.getYorig(), second.getYorig());
                        int interZ = Math.max(first.getZorig(), second.getZorig());
                        if (interX >= x && interX < x + w && interY >= y && interY < y + h
                            && interZ >= z && interZ < z + d)
                        {
                            sb.append("(").append(first.toString()).append(") and (")
                                .append(second.toString()).append(")\r\n");
                        }
                    }
                }
            }
        }

        private boolean overlap(int x1, int y1, int z1, int w1, int h1, int d1,
                                 int x2, int y2, int z2, int w2, int h2, int d2)
        {
            boolean xOverlap = x1 < x2 + w2 && x2 < x1 + w1;
            boolean yOverlap = y1 < y2 + h2 && y2 < y1 + h1;
            boolean zOverlap = z1 < z2 + d2 && z2 < z1 + d1;
            return xOverlap && yOverlap && zOverlap;
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
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, half, h, d))
                {
                    left = left.insert(obj, x, y, z, half, h, d, depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(),
                    x + half, y, z, w - half, h, d))
                {
                    right = right.insert(obj, x + half, y, z, w - half, h, d, depth + 1);
                }
            }
            else if (split == 1)
            {
                int half = h / 2;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, w, half, d))
                {
                    left = left.insert(obj, x, y, z, w, half, d, depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y + half, z, w, h - half, d))
                {
                    right = right.insert(obj, x, y + half, z, w, h - half, d, depth + 1);
                }
            }
            else
            {
                int half = d / 2;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, w, h, half))
                {
                    left = left.insert(obj, x, y, z, w, h, half, depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z + half, w, h, d - half))
                {
                    right = right.insert(obj, x, y, z + half, w, h, d - half, depth + 1);
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
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, half, h, d))
                {
                    left = left.remove(obj, x, y, z, half, h, d, depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(),
                    x + half, y, z, w - half, h, d))
                {
                    right = right.remove(obj, x + half, y, z, w - half, h, d, depth + 1);
                }
            }
            else if (split == 1)
            {
                int half = h / 2;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, w, half, d))
                {
                    left = left.remove(obj, x, y, z, w, half, d, depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y + half, z, w, h - half, d))
                {
                    right = right.remove(obj, x, y + half, z, w, h - half, d, depth + 1);
                }
            }
            else
            {
                int half = d / 2;
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z, w, h, half))
                {
                    left = left.remove(obj, x, y, z, w, h, half, depth + 1);
                }
                if (overlap(obj.getXorig(), obj.getYorig(), obj.getZorig(),
                    obj.getXwidth(), obj.getYwidth(), obj.getZwidth(), x, y, z + half, w, h, d - half))
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
        public void intersect(StringBuilder sb, int x1, int y1, int z1, int w1, int h1, int d1,
                             int x2, int y2, int z2, int w2, int h2, int d2, int depth, boolean allowEmit)
        {
            Bintree.visited++;
            if (!overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, d2))
            {
                return;
            }
            if (allowEmit)
            {
                sb.append("In Internal node (").append(x2).append(", ").append(y2).append(", ")
                    .append(z2).append(", ").append(w2).append(", ").append(h2).append(", ")
                    .append(d2).append(") ").append(depth).append("\r\n");
            }

            int split = depth % 3;
            if (split == 0)
            {
                int half = w2 / 2;
                boolean leftEmit = allowEmit && containsPoint(x1, y1, z1, x2, y2, z2, half, h2, d2);
                boolean rightEmit = allowEmit && containsPoint(x1, y1, z1, x2 + half, y2, z2, w2 - half, h2, d2);
                if (overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, half, h2, d2))
                {
                    left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2, half, h2, d2, depth + 1, leftEmit);
                }
                if (overlap(x1, y1, z1, w1, h1, d1, x2 + half, y2, z2, w2 - half, h2, d2))
                {
                    right.intersect(sb, x1, y1, z1, w1, h1, d1, x2 + half, y2, z2, w2 - half, h2, d2, depth + 1, rightEmit);
                }
            }
            else if (split == 1)
            {
                int half = h2 / 2;
                boolean leftEmit = allowEmit && containsPoint(x1, y1, z1, x2, y2, z2, w2, half, d2);
                boolean rightEmit = allowEmit && containsPoint(x1, y1, z1, x2, y2 + half, z2, w2, h2 - half, d2);
                if (overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, half, d2))
                {
                    left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, half, d2, depth + 1, leftEmit);
                }
                if (overlap(x1, y1, z1, w1, h1, d1, x2, y2 + half, z2, w2, h2 - half, d2))
                {
                    right.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2 + half, z2, w2, h2 - half, d2, depth + 1, rightEmit);
                }
            }
            else
            {
                int half = d2 / 2;
                boolean leftEmit = allowEmit && containsPoint(x1, y1, z1, x2, y2, z2, w2, h2, half);
                boolean rightEmit = allowEmit && containsPoint(x1, y1, z1, x2, y2, z2 + half, w2, h2, d2 - half);
                if (overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, half))
                {
                    left.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2, w2, h2, half, depth + 1, leftEmit);
                }
                if (overlap(x1, y1, z1, w1, h1, d1, x2, y2, z2 + half, w2, h2, d2 - half))
                {
                    right.intersect(sb, x1, y1, z1, w1, h1, d1, x2, y2, z2 + half, w2, h2, d2 - half, depth + 1, rightEmit);
                }
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

        private boolean overlap(int x1, int y1, int z1, int w1, int h1, int d1,
                                 int x2, int y2, int z2, int w2, int h2, int d2)
        {
            boolean xOverlap = x1 < x2 + w2 && x2 < x1 + w1;
            boolean yOverlap = y1 < y2 + h2 && y2 < y1 + h1;
            boolean zOverlap = z1 < z2 + d2 && z2 < z1 + d1;
            return xOverlap && yOverlap && zOverlap;
        }
    }
}
