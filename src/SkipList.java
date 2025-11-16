import java.util.Random;

/**
 * This is the SkipList class 
 * @author {Giovanni Garcia}
 * @version {11.16.2025}
 * @param <N> for Name 
 * @param <O> for object
 */
public class SkipList <N extends Comparable<N>, O>{

    private static final int HIGHEST_LEVEL = 32;
    private SkipNode head;
    private int level;
    private Random rnd;
    
    /**
     * This is the inner SkipNode class
     */
    private class SkipNode
    {
        N name;
        O object;
        SkipNode[] forward;
        
        public SkipNode(N name, O object, int nodeLevel)
        {
            this.name = name;
            this.object = object;
            //this.forward = new SkipNode[nodeLevel + 1];
        }    
    }
        /**
         * This is the SkipList class constructor 
         * @param rnd is the random object
         */
        public SkipList(Random rnd)
        {
            this.rnd = rnd;
            
        }
    }
