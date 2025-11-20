
/**
 * This is the Bintree class 
 * @author {Giovanni Garcia}
 * @version {11.20.2025}
 */
public class Bintree {
    
    private BinNode root;
    
    private static final int X = 0;
    private static final int Y = 0;
    private static final int Z = 0;
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 1024;
    private static final int DEPTH = 1024;
    
    /**
     * This is the constructor for Bintree
     * The root is an EmptyNode, depth = 0
     */
    public Bintree() 
    {
        root = new EmptyNode();
    }
    
    private interface BinNode
    {
        String print(int x, int y, int z, int w, int h, int d, int depth);
        int countNodes();
    }
    
    private static class EmptyNode implements BinNode
    {
        public String print(int x, int y, int z, int w, int h, int d, int depth)
        {
            return "E (" + x + ", " + y + ", " + z + ", " 
                   + w + ", " + h + ", " + d + ") " + depth + "\r\n";
        }
        public int countNodes()
        {
            return 1;
        }
    }
    /**
     * This is the insert method for Bintree
     * @param object is the object inserted
     */
    public void insert(AirObject object)
    {
        //Finish this later
    }
    /**
     * This is the printTree method
     * @return String for the tree
     */
    public String printTree() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(root.print(X, Y, Z, WIDTH, HEIGHT, DEPTH, 0));
        sb.append(root.countNodes()).append(" Bintree nodes printed\r\n");
        return sb.toString();
    }
}    
