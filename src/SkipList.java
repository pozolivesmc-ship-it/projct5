import java.util.Random;

/**
 * This is the SkipList class 
 * @author {Giovanni Garcia}
 * @version {11.16.2025}
 */
public class SkipList {

    private static final int HIGHEST_LEVEL = 16;
    private SkipNode head;
    private int level;
    private Random rnd;
    
    /**
     * This is the inner SkipNode class
     */
    private static class SkipNode
    {
        String key ;
        AirObject value;
        SkipNode[] forward;
        /**
         * This is the constructor for SkipNode
         * @param key is the name
         * @param value is the AirObject
         * @param level is the level
         */
        public SkipNode(String key, AirObject value, int level)
        {
            this.key = key;
            this.value = value;
            forward = new SkipNode[level + 1];
        }    
    }
        /**
         * This is the SkipList class constructor 
         * @param rnd is the random object
         */
        public SkipList(Random rand)
        {
            if (rand == null)
            {
                rnd = new Random();
            }
            else
            {
                rnd = rand;
            }
            level = 0;
            head = new SkipNode(null, null, HIGHEST_LEVEL);
        }
        
        /**
         * This is the find method
         * @param name is the name we're looking for
         * @return AirObject found
         */
        public AirObject find(String name)
        {
            //Start from the head
            SkipNode start = head;
            //Start comparing from the highest level 
            for (int i = level; i >= 0; i--)
            {
                while (start.forward[i] != null &&
                       start.forward[i].key.compareTo(name) < 0)
                {
                    start = start.forward[i];
                }
            }
            //Move to it if exists
            start = start.forward[0];
            //If found and names match
            if (start != null && start.key.equals(name))
            {
                return start.value;
            }
            //If not found
            return null;
        }
        /**
         * Picks randoms level 
         * @return int for level
         */
        public int randomLevel() 
        {
            int rndLevel = 0;
            while (Math.abs(rnd.nextInt()) % 2 == 0 && rndLevel < HIGHEST_LEVEL)
            {
                rndLevel++;
            }
            return rndLevel;
        }
        
        public void insert(String name, AirObject object)
        {
            SkipNode[] update = new SkipNode[HIGHEST_LEVEL + 1];
            //Start from the head
            SkipNode start = head;
            //Start comparing from the highest level 
            for (int i = level; i >= 0; i--)
            {
                while (start.forward[i] != null &&
                       start.forward[i].key.compareTo(name) < 0)
                {
                    start = start.forward[i];
                }
                //Add end of level i
                update[i] = start; 
            }
            //Move to it if exists
            start = start.forward[0];
            //If found and names match replace
            if (start != null && start.key.equals(name))
            {
                start.value = object;
                return;
            }
            int rndLevel = randomLevel();
            if (rndLevel > level)
            {
                for (int i = level + 1; i <= rndLevel; i++)
                {
                    update[i] = head;
                }
                level = rndLevel;
            }
            start = new SkipNode(name, object, rndLevel);
            //Splice into list for every level
            for (int i = 0; i <= rndLevel; i++)
            {
                start.forward[i] = update[i].forward[i];
                update[i].forward[i] = start;
            }
        }
    }
