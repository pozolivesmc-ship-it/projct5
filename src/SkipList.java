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
            while (rnd.nextBoolean() && rndLevel < HIGHEST_LEVEL)
            {
                rndLevel++;
            }
            return rndLevel;
        }
        /**
         * This is the insert method
         * @param name is the name inserted
         * @param object is the object inserted
         */
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
        /**
         * This is the remove method
         * @param name is the name being removed
         * @return true or false if remove successful or not
         */
        public boolean remove(String name)
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
            if (start == null || !start.key.equals(name))
            {
                return false;
            }
            //Unlink at every level
            for (int i = 0; i <= level; i++)
            {
                if (update[i].forward[i] != start)
                {
                    break;
                }
                update[i].forward[i] = start.forward[i];
            }
            //Fix level if necessary
            while (head.forward[level] == null && level > 0)
            {
                level--;
            }
            //Was successfully removed
            return true;
        }
        /**
         * This is the printList method
         * @return string for list
         */
        public String printList()
        {
            //Checks if empty first
            if (head.forward[0] == null)
            {
                return "SkipList is empty";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Node has depth ").append(level).append(", Value (null)\r\n");
            SkipNode start = head.forward[0];
            int count = 0;
            //This prints everything
            while (start != null)
            {
                int depth = start.forward.length - 1;
                sb.append("Node has depth ").append(depth);
                sb.append(", Value (").append(start.value.toString());
                sb.append(")\r\n");
                count++;
                start = start.forward[0];
            }
            sb.append(count).append(" skiplist nodes printed\r\n");
            return sb.toString();
        }
        /**
         * This is the range method
         * @param begin is the start
         * @param end is the end
         * @return String for range
         */
        public String range(String begin, String end)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Found these records in the range ");
            sb.append(begin).append(" to ").append(end);
            sb.append("\r\n");
            SkipNode start = head.forward[0];
            while (start != null)
            {
                if (start.key.compareTo(begin) >= 0 &&
                    start.key.compareTo(end) <= 0)
                {
                    sb.append(start.value.toString()).append("\r\n");
                }
                start = start.forward[0];
            }
            return sb.toString();
        }
    }
