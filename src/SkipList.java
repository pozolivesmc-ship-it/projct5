import java.util.Random;

/**
 * This is the SkipList class 
 * @author {Giovanni Garcia}
 * @version {12.4.2025}
 * @param <K> is for key (String)
 * @param <V> is for value (AirObject)
 */
public class SkipList<K extends Comparable<K>, V> {

    private static final int HIGHEST_LEVEL = 16;
    private SkipNode<K, V> head;
    private int level;
    private Random rnd;

    /**
     * This is the inner SkipNode class
     */
    private static class SkipNode<K, V>
    {
        K key;
        V value;
        SkipNode<K, V>[] forward;
        /**
         * This is the constructor for SkipNode
         * @param key is the name
         * @param value is the AirObject
         * @param level is the level
         */
        @SuppressWarnings("unchecked")
        public SkipNode(K key, V value, int level)
        {
            this.key = key;
            this.value = value;
            this.forward = (SkipNode<K, V>[])new SkipNode[level + 1];
        }    
    }
    /**
     * This is the SkipList class constructor 
     * @param rand is the random object
     */
    public SkipList(Random rand)
    {
        //Check if null and create new random
        if (rand == null)
        {
            rnd = new Random();
        }
        else
        {
                rnd = rand;
        }
        level = 0;
        head = new SkipNode<>(null, null, HIGHEST_LEVEL);
    }

    /**
     * This is the find method
     * @param name is the name we're looking for
     * @return AirObject found
     */
    public V find(K name)
    {
        //Start from the head
        SkipNode<K, V> start = head;
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
        if (start != null && start.key.compareTo(name) == 0)
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
    private int randomLevel() 
    {
        //Chooses random level between 0 and HIGHEST_LEVEL
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
    @SuppressWarnings("unchecked")
    public void insert(K name, V object)
    {
        //Update array to keep track at each level
        SkipNode<K, V>[] update = (SkipNode<K, V>[])new SkipNode[HIGHEST_LEVEL + 1];
        //Start from the head
        SkipNode<K, V> start = head;
        //Start comparing from the highest level to level 0
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
        if (start != null && start.key.compareTo(name) == 0)
        {
            start.value = object;
            return;
        }
        //If not create node at random level
        int rndLevel = randomLevel();
        //Checks if the node level is higher
        if (rndLevel > level)
        {
            //Updates each new level 
            for (int i = level + 1; i <= rndLevel; i++)
            {
                update[i] = head;
            }
            //Updates level  
            level = rndLevel;
        }
        start = new SkipNode<>(name, object, rndLevel);
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
    @SuppressWarnings("unchecked")
    public boolean remove(K name)
    {
        SkipNode<K, V>[] update = (SkipNode<K, V>[])new SkipNode[HIGHEST_LEVEL + 1];
        //Start from the head
        SkipNode<K, V> start = head;
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
        if (start == null || start.key.compareTo(name) != 0)
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
        SkipNode<K, V> start = head.forward[0];
        int count = 0;
        //This starts at level 0 and prints each node
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
    public String range(K begin, K end)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Found these records in the range ");
        sb.append(begin).append(" to ").append(end);
        sb.append("\r\n");
        SkipNode<K, V> start = head.forward[0];
        //Goes through and prints only the keys between begin and end
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
