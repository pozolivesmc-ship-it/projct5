import java.util.Random;
import student.TestCase;

/**
 * @author {Giovanni Garcia}
 * @version {12.4.2025}
 */
public class AirControlTest extends TestCase {

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * Get code coverage of the class declaration.
     *
     * @throws Exception
     */
    public void testRInit() throws Exception {
        AirControl recstore = new AirControl();
        assertNotNull(recstore);
    }


    // ----------------------------------------------------------
    /**
     * Test syntax: Sample Input/Output
     *
     * @throws Exception
     */
    public void testSampleInput() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new AirPlane("Air1",
            0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertTrue(w.add(new Drone("Air2",
            100, 1010, 101, 924, 2, 900, "Droners", 3)));
        assertTrue(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertFalse(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertTrue(w.add(new Rocket("Enterprise",
            0, 100, 20, 10, 50, 50, 5000, 99.29)));

        assertFuzzyEquals(
            "Rocket Enterprise 0 100 20 10 50 50 5000 99.29",
            w.delete("Enterprise"));

        assertFuzzyEquals("Airplane Air1 0 10 1 20 2 30 USAir 717 4",
            w.print("Air1"));
        assertNull(w.print("air1"));

        assertFuzzyEquals(
            "I (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "  I (0, 0, 0, 512, 1024, 1024) 1\r\n"
                + "    Leaf with 3 objects (0, 0, 0, 512, 512, 1024) 2\r\n"
                + "    (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
                + "    (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                + "    (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
                + "    Leaf with 1 objects (0, 512, 0, 512, 512, 1024) 2\r\n"
                + "    (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                + "  Leaf with 1 objects (512, 0, 0, 512, 1024, 1024) 1\r\n"
                + "  (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                + "5 Bintree nodes printed\r\n",
                w.printbintree());

        assertFuzzyEquals(
            "Node has depth 3, Value (null)\r\n"
                + "Node has depth 3, "
                + "Value (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
                + "Node has depth 1, "
                + "Value (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                + "Node has depth 2, "
                + "Value (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                + "Node has depth 2, "
                + "Value (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
                + "4 skiplist nodes printed\r\n",
                w.printskiplist());

        assertFuzzyEquals(
            "Found these records in the range a to z\r\n"
                + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n",
                w.rangeprint("a", "z"));
        assertFuzzyEquals(
            "Found these records in the range a to l\r\n",
            w.rangeprint("a", "l"));
        assertNull(w.rangeprint("z", "a"));

        assertFuzzyEquals(
            "The following collisions exist in the database:\r\n"
                + "In leaf node (0, 0, 0, 512, 512, 1024) 2\r\n"
                + "(Airplane Air1 0 10 1 20 2 30 USAir 717 4) "
                + "and (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                + "In leaf node (0, 512, 0, 512, 512, 1024) 2\r\n"
                + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n",
                w.collisions());

        assertFuzzyEquals(
            "The following objects intersect (0 0 0 1024 1024 1024):\r\n"
                + "In Internal node (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "In Internal node (0, 0, 0, 512, 1024, 1024) 1\r\n"
                + "In leaf node (0, 0, 0, 512, 512, 1024) 2\r\n"
                + "Airplane Air1 0 10 1 20 2 30 USAir 717 4\r\n"
                + "Balloon B1 10 11 11 21 12 31 hot_air 15\r\n"
                + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n"
                + "In leaf node (0, 512, 0, 512, 512, 1024) 2\r\n"
                + "Drone Air2 100 1010 101 924 2 900 Droners 3\r\n"
                + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n"
                + "5 nodes were visited in the bintree\r\n",
                w.intersect(0, 0, 0, 1024, 1024, 1024));
    }


    // ----------------------------------------------------------
    /**
     * Test syntax: Check various forms of bad input parameters
     *
     * @throws Exception
     */
    public void testBadInput() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, null, 1, 1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, "Alaska", 0, 1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, "Alaska", 1, 0)));
        assertFalse(w.add(new Balloon(null, 1, 1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", -1, 1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, -1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, -1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 0, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 0, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 0, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 1, "hot", -1)));
        assertFalse(w.add(new Bird("b", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Bird("b", 1, 1, 1, 1, 1, 1, "Ostrich", 0)));
        assertFalse(w.add(new Bird("b", -1, 1, 1, 1, 1, 1, "Ostrich", 0)));
        assertFalse(w.add(new Bird("b", 1, 1, 1, 0, 1, 1, "Ostrich", 0)));
        assertFalse(w.add(new Drone("d", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Drone("d", 1, 1, 1, 1, 1, 1, "Droner", 0)));
        assertFalse(w.add(new Drone("d", -1, 1, 1, 1, 1, 1, "Droner", 0)));
        assertFalse(w.add(new Drone("d", 1, 1, 1, 2000, 1, 1, "Droner", 0)));
        assertFalse(w.add(new Rocket("r", -1, 1, 1, 1, 1, 1, 1, 1.1)));
        assertFalse(w.add(new Rocket("r", -1, 1, 1, 1, 1, 1, 1, 1.1)));
        assertFalse(w.add(new Rocket("r", 1, 1, 1, 1, 1, 1, -1, 1.1)));
        assertFalse(w.add(new Rocket("r", 1, 1, 1, 2000, 1, 1, 1, -1.1)));
        assertFalse(w.add(new Rocket("r", 1, 1, 1, 1, 1, 1, 1, -1.1)));
        assertFalse(w.add(
            new AirPlane("a", 2000, 1, 1, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 2000, 1, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 2000, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 2000, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 1, 2000, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1, 1, 1, 2000, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1000, 1, 1, 1000, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1000, 1, 1, 1000, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
            new AirPlane("a", 1, 1, 1000, 1, 1, 1000, "Alaska", 1, 1)));
        assertNull(w.delete(null));
        assertNull(w.print(null));
        assertNull(w.rangeprint(null, "a"));
        assertNull(w.rangeprint("a", null));
        assertNull(w.intersect(-1, 1, 1, 1, 1, 1));
        assertNull(w.intersect(1, -1, 1, 1, 1, 1));
        assertNull(w.intersect(1, 1, -1, 1, 1, 1));
        assertNull(w.intersect(1, 1, 1, -1, 1, 1));
        assertNull(w.intersect(1, 1, 1, 1, -1, 1));
        assertNull(w.intersect(1, 1, 1, 1, 1, -1));
        assertNull(w.intersect(2000, 1, 1, 1, 1, 1));
        assertNull(w.intersect(1, 2000, 1, 1, 1, 1));
        assertNull(w.intersect(1, 1, 2000, 1, 1, 1));
        assertNull(w.intersect(1, 1, 1, 2000, 1, 1));
        assertNull(w.intersect(1, 1, 1, 1, 2000, 1));
        assertNull(w.intersect(1, 1, 1, 1, 1, 2000));
        assertNull(w.intersect(1000, 1, 1, 1000, 1, 1));
        assertNull(w.intersect(1, 1000, 1, 1, 1000, 1));
        assertNull(w.intersect(1, 1, 1000, 1, 1, 1000));
    }


    // ----------------------------------------------------------
    /**
     * Test empty: Check various returns from commands on empty database
     *
     * @throws Exception
     */
    public void testEmpty() throws Exception {
        WorldDB w = new WorldDB(null);
        assertNull(w.delete("hello"));
        assertFuzzyEquals("SkipList is empty", w.printskiplist());
        assertFuzzyEquals(
            "E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "1 Bintree nodes printed\r\n",
                w.printbintree());
        assertNull(w.print("hello"));
        assertFuzzyEquals("Found these records in the range begin to end\n",
            w.rangeprint("begin", "end"));
        assertFuzzyEquals("The following collisions exist in the database:\n",
            w.collisions());
        assertFuzzyEquals(
            "The following objects intersect (1, 1, 1, 1, 1, 1)\n" +
                "1 nodes were visited in the bintree\n",
                w.intersect(1, 1, 1, 1, 1, 1));
    }
    /**
     * This tests adding a null AirObject and print
     */
    public void testAddAndPrint()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertFalse(w.add(null));
        assertTrue(w.add(new AirPlane("Air1",
            0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertFuzzyEquals(
                w.print("Air1"),
        	    "Airplane Air1 0 10 1 20 2 30 USAir 717 4");
    }
    /**
     * This tests when there's 2 of the same name
     */
    public void testDuplicateName()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        Bird b1 = new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1);
        Bird b2 = new Bird("pterodactyl",
            10, 20, 30, 30, 30, 40, "Hawk", 2);
        assertTrue(w.add(b1));
        assertFalse(w.add(b2));
        assertFuzzyEquals(w.print("pterodactyl"), "Bird pterodactyl 0 100"
        		+ " 20 10 50 50 Dinosaur 1");
    }
    /**
     * This tests the delete logic 
     */
    public void testDelete()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        Drone d = new Drone("Air2",
            100, 1010, 101, 924, 2, 900, "Droners", 3);
        assertTrue(w.add(d));
        String removed = w.delete("Air2");
        assertNotNull(removed);
        assertTrue(removed.contains("Drone Air2"));
        assertNull(w.print("Air2"));
        assertNull(w.delete("air2"));
    }
    /**
     * This tests the clear logic and ensures 
     * everything gets reset
     */
    public void testClear()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertNotNull(w.print("B1"));
        assertNotNull(w.print("pterodactyl"));
        w.clear();
        assertNull(w.print("B1"));
        assertNull(w.print("pterodactyl"));
        assertFuzzyEquals(w.printskiplist(), "SkipList is empty");
        assertFuzzyEquals(
            "E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "1 Bintree nodes printed\r\n",
                w.printbintree());
    }
    /**
     * This tests the rangeprint logic
     */
    public void testRangePrint()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new Balloon("B2",
            11, 10, 10, 20, 11, 30, "hot_air", 16)));
        String result = w.rangeprint("a", "b");
        assertNotNull(result);
        assertTrue(result.contains("Found these records in the range a to b"));
        assertFalse(result.contains("Balloon B1"));
        assertFalse(result.contains("Balloon B2"));
    }
    /**
     * This tests intersect for special cases
     */
    public void testIntersectExtraCase()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertNull(w.intersect(0, 0, 0, 0, 1, 1));
        assertNull(w.intersect(0, 0, 0, 1, 1, 0));
        assertNull(w.intersect(0, 0, 0, 1, 0, 1));
    }
    /**
     * This tests extra case with rangeprint
     */
    public void testRangePrintExtraCase()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertNull(w.rangeprint(null, "a"));
        assertNull(w.rangeprint("b", null));
        assertNull(w.rangeprint("z", "a"));
        String result = w.rangeprint("a", "z");
        assertNotNull(result);
        assertTrue(result.startsWith("Found these records in the range a"
        		+ " to z"));  
    }
    /**
     * This tests the printbintree() method
     */
    public void testPrintBintree()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new AirPlane("Air1",
            0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertTrue(w.add(new Drone("Air2",
            100, 1010, 101, 924, 2, 900, "Droners", 3)));
        assertTrue(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertFuzzyEquals(
                "I (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                    + "  I (0, 0, 0, 512, 1024, 1024) 1\r\n"
                    + "    Leaf with 3 objects (0, 0, 0, 512, 512, 1024) 2\r\n"
                    + "    (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
                    + "    (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                    + "    (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
                    + "    Leaf with 1 objects (0, 512, 0, 512, 512, 1024)"
                    + " 2\r\n"
                    + "    (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                    + "  Leaf with 1 objects (512, 0, 0, 512, 1024, 1024)"
                    + " 1\r\n"
                    + "  (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                    + "5 Bintree nodes printed\r\n",
                    w.printbintree());
    }
    /**
     * This test the printskiplist() method
     */
    public void testPrintSkiplist()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new AirPlane("Air1",
            0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertTrue(w.add(new Drone("Air2",
            100, 1010, 101, 924, 2, 900, "Droners", 3)));
        assertTrue(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertFuzzyEquals(
              "Node has depth 3, Value (null)\r\n"
                  + "Node has depth 3, "
                  + "Value (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
                  + "Node has depth 1, "
                  + "Value (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                  + "Node has depth 2, "
                  + "Value (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                  + "Node has depth 2, "
                  + "Value (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
                   + "4 skiplist nodes printed\r\n",
                  w.printskiplist());
    }
    /**
     * This tests rangeprint() method
     */
    public void testRangePrintExtra()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new AirPlane("Air1",
            0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertTrue(w.add(new Drone("Air2",
            100, 1010, 101, 924, 2, 900, "Droners", 3)));
        assertTrue(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertFuzzyEquals(
            "Found these records in the range a to z\r\n"
                + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n",
                w.rangeprint("a", "z"));
        assertFuzzyEquals(
            "Found these records in the range a to l\r\n",
            w.rangeprint("a", "l"));
        assertNull(w.rangeprint("z", "a"));
    }
    /**
     * This tests collisions() method
     */
    public void testCollisions()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new AirPlane("Air1",
            0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertTrue(w.add(new Drone("Air2",
            100, 1010, 101, 924, 2, 900, "Droners", 3)));
        assertTrue(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertFuzzyEquals(
            "The following collisions exist in the database:\r\n"
                + "In leaf node (0, 0, 0, 512, 512, 1024) 2\r\n"
                + "(Airplane Air1 0 10 1 20 2 30 USAir 717 4) "
                + "and (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                + "In leaf node (0, 512, 0, 512, 512, 1024) 2\r\n"
                + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n",
                w.collisions());
    }
    /**
     * This test the intersect() method
     */
    public void testIntersect()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new AirPlane("Air1",
            0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertTrue(w.add(new Drone("Air2",
            100, 1010, 101, 924, 2, 900, "Droners", 3)));
        assertTrue(w.add(new Bird("pterodactyl",
            0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertFuzzyEquals(
            "The following objects intersect (0 0 0 1024 1024 1024):\r\n"
                + "In Internal node (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "In Internal node (0, 0, 0, 512, 1024, 1024) 1\r\n"
                + "In leaf node (0, 0, 0, 512, 512, 1024) 2\r\n"
                + "Airplane Air1 0 10 1 20 2 30 USAir 717 4\r\n"
                + "Balloon B1 10 11 11 21 12 31 hot_air 15\r\n"
                + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n"
                + "In leaf node (0, 512, 0, 512, 512, 1024) 2\r\n"
                + "Drone Air2 100 1010 101 924 2 900 Droners 3\r\n"
                + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n"
                + "5 nodes were visited in the bintree\r\n",
                w.intersect(0, 0, 0, 1024, 1024, 1024));
    }
    /**
     * This tests intersect() method extra case
     */
    public void testIntersectSpecialCase()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        String result = w.intersect(200, 200, 200, 100, 100, 100);
        assertFalse(result.contains("Balloon B1"));
        assertTrue(result.startsWith("The following objects intersect"));
    }
    
    /**
     * Test inserting null object - covers line 29
     */
    public void testInsertNull() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // This should trigger the null check in Bintree.insert()
        w.add(null);
        
        // Verify tree is still empty
        assertFuzzyEquals(
            "E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "1 Bintree nodes printed\r\n",
            w.printbintree());
    }
    
    /**
     * Test removing null object - covers line 42
     */
    public void testRemoveNull() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        assertTrue(w.add(
        	    new Balloon("B1", 10, 11, 11, 21, 12, 31, "hot_air", 15)));
        
        // This triggers the null check in Bintree.remove()
        w.delete(null);
        
        // Object should still be there
        assertNotNull(w.print("B1"));
    }
    
    /**
     * Test that triggers array expansion in LeafNode - covers lines 201-206
     */
    public void testLeafNodeArrayExpansion() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Add 5+ objects that all overlap to trigger array expansion
        // They all share common intersection so won't split
        assertTrue(w.add(new Balloon("B1", 10, 10, 10, 50, 50, 50, "hot", 5)));
        assertTrue(w.add(new Balloon("B2", 15, 15, 15, 40, 40, 40, "hot", 5)));
        assertTrue(w.add(new Balloon("B3", 20, 20, 20, 30, 30, 30, "hot", 5)));
        assertTrue(w.add(new Balloon("B4", 25, 25, 25, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(new Balloon("B5", 30, 30, 30, 10, 10, 10, "hot", 5)));
        
        String result = w.printbintree();
        assertTrue(result.contains("Leaf with 5 objects"));
    }
    
    /**
     * Test hasCommonIntersection when size is 0 - covers line 58 in Image 5
     */
    public void testHasCommonIntersectionEmptyLeaf() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Add then remove to create empty scenario
        assertTrue(w.add(new Balloon("B1", 10, 10, 10, 20, 20, 20, "hot", 5)));
        assertNotNull(w.delete("B1"));
        
        // Tree should be empty now
        assertFuzzyEquals(
            "E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "1 Bintree nodes printed\r\n",
            w.printbintree());
    }
    
    /**
     * Test hasCommonIntersection full logic - covers lines 311-956 in Image 5
     */
    public void testHasCommonIntersectionMultipleObjects() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Create objects with complex intersection pattern
        assertTrue(w.add(
        	    new Bird("B1", 100, 100, 100, 50, 50, 50, "Eagle", 1)));

        	assertTrue(w.add(
        	    new Bird("B2", 120, 120, 120, 40, 40, 40, "Hawk", 1)));

        	assertTrue(w.add(
        	    new Bird("B3", 130, 130, 130, 30, 30, 30, "Falcon", 1)));

        	assertTrue(w.add(
        	    new Bird("B4", 140, 140, 140, 20, 20, 20, "Sparrow", 1)));

        
        // These should all be in same leaf due to common intersection
        String result = w.printbintree();
        assertTrue(result.contains("Leaf"));
    }
    
    /**
     * Test LeafNode.remove when object not found - covers line 1069
     */
    public void testRemoveNonexistentFromLeaf() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        assertTrue(w.add(new Balloon("B1", 10, 10, 10, 20, 20, 20, "hot", 5)));
        
        // Try to delete something that doesn't exist
        assertNull(w.delete("NonExistent"));
        
        // B1 should still be there
        assertNotNull(w.print("B1"));
    }
    
    /**
     * Test LeafNode.remove that makes leaf empty - covers line 1235
     */
    public void testRemoveLastObjectFromLeaf() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Add single object
        assertTrue(w.add(new Balloon("B1", 10, 10, 10, 20, 20, 20, "hot", 5)));
        assertNotNull(w.print("B1"));
        
        // Remove it - should return to FLYWEIGHT
        assertNotNull(w.delete("B1"));
        
        // Tree should be empty
        assertFuzzyEquals(
            "E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "1 Bintree nodes printed\r\n",
            w.printbintree());
    }
    
    /**
     * Test InternalNode collapse scenarios - covers lines 564-597
     */
    public void testInternalNodeCollapse() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Create a tree that will split
        assertTrue(w.add(new Balloon("B1", 10, 10, 10, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(
        	    new Balloon("B2", 600, 600, 600, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(new Balloon("B3", 30, 30, 30, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(new Balloon("B4", 50, 50, 50, 20, 20, 20, "hot", 5)));
        
        // Should have internal nodes now
        String before = w.printbintree();
        assertTrue(before.contains("I ("));
        
        // Remove objects to trigger collapse
        w.delete("B2");
        w.delete("B3");
        w.delete("B4");
        
        // Tree should collapse
        String after = w.printbintree();
        assertTrue(after.contains("Leaf"));
    }
    
    /**
     * Test both children are leaf nodes merge - covers lines 573-587
     */
    public void testMergeTwoLeafNodes() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Create scenario with two leaf children
        assertTrue(w.add(new Drone("D1", 100, 100, 100, 50, 50, 50, "DJI", 4)));
        assertTrue(w.add(
        	    new Drone("D2", 600, 100, 100, 50, 50, 50, "Parrot", 4)));

        	assertTrue(w.add(
        	    new Drone("D3", 200, 100, 100, 50, 50, 50, "Skydio", 4)));

        
        // Create internal node structure
        String before = w.printbintree();
        
        // Remove to trigger merge
        w.delete("D2");
        w.delete("D3");
        
        String after = w.printbintree();
        assertTrue(after.contains("Leaf"));
    }
    
    /**
     * Test gatherObjects with InternalNode - covers line 799
     */
    public void testGatherObjectsFromInternalNode() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Create deep tree structure
        assertTrue(w.add(new Rocket("R1", 50, 50, 50, 30, 30, 30, 100, 45.0)));
        assertTrue(w.add(new Rocket("R2", 600, 50, 50, 30, 30, 30, 100, 45.0)));
        assertTrue(w.add(new Rocket("R3", 100, 50, 50, 30, 30, 30, 100, 45.0)));
        assertTrue(w.add(new Rocket("R4", 150, 50, 50, 30, 30, 30, 100, 45.0)));
        
        // Remove some to trigger gather
        w.delete("R2");
        w.delete("R3");
        
        assertNotNull(w.print("R1"));
    }
    
    /**
     * Test Y-dimension split in InternalNode.print - covers lines 504-505
     */
    public void testYDimensionSplit() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Need 4+ objects to force split (LEAF_MAX = 3)
        assertTrue(w.add(new AirPlane(
        	    "A1", 100, 100, 100, 50, 50,
        	    50, "United", 100, 2)));
        	assertTrue(w.add(new AirPlane(
        	    "A2", 100, 600, 100, 50, 50,
        	    50, "Delta", 200, 2)));
        	assertTrue(w.add(new AirPlane(
        	    "A3", 150, 100, 100, 50, 50,
        	    50, "Southwest", 300, 2)));
        	assertTrue(w.add(new AirPlane(
        	    "A4", 150, 600, 100, 50, 50,
        	    50, "American", 400, 2)));

        
        String result = w.printbintree();
        // Should have internal nodes at different levels
        assertTrue(result.contains("I ("));
    }
    
    /**
     * Test Z-dimension split - covers Image 9 else block
     */
    public void testZDimensionSplit() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Need 4+ objects to force split (LEAF_MAX = 3)
        assertTrue(w.add(new AirPlane(
        	    "A1", 100, 100, 100, 50, 50,
        	    50, "United", 100, 2)));
        	assertTrue(w.add(new AirPlane(
        	    "A2", 100, 100, 600, 50, 50,
        	    50, "Delta", 200, 2)));
        	assertTrue(w.add(new AirPlane(
        	    "A3", 150, 100, 100, 50, 50,
        	    50, "Southwest", 300, 2)));
        	assertTrue(w.add(new AirPlane(
        	    "A4", 150, 100, 600, 50, 50,
        	    50, "American", 400, 2)));


        
        String result = w.printbintree();
        assertTrue(result.contains("I ("));
    }
    
    /**
     * Test InternalNode.insert on all three dimensions - covers Image 10
     */
    public void testInsertAllDimensions() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // X split (depth 0)
        assertTrue(w.add(
        		new Bird("B1", 100, 100, 100, 50, 50, 50, "Eagle", 1)));
        assertTrue(w.add(
        		new Bird("B2", 600, 100, 100, 50, 50, 50, "Hawk", 1)));
        
        // Y split (depth 1)
        assertTrue(w.add(
        		new Bird("B3", 100, 600, 100, 50, 50, 50, "Falcon", 1)));
        
        // Z split (depth 2)
        assertTrue(w.add(
        		new Bird("B4", 100, 100, 600, 50, 50, 50, "Sparrow", 1)));
        
        String result = w.printbintree();
        assertTrue(result.contains("I ("));
    }
    
    /**
     * Test InternalNode.remove on Y and Z dimensions - covers Image 11
     */
    public void testRemoveYZDimensions() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Create objects across all dimensions
        assertTrue(w.add(new Drone("D1", 100, 100, 100, 50, 50, 50, "DJI", 4)));
        assertTrue(w.add(
        		new Drone("D2", 100, 600, 100, 50, 50, 50, "Parrot", 4)));
        assertTrue(w.add(
        		new Drone("D3", 100, 100, 600, 50, 50, 50, "Skydio", 4)));
        assertTrue(w.add(
        		new Drone("D4", 600, 100, 100, 50, 50, 50, "Autel", 4)));
        
        // Remove to trigger Y and Z dimension removes
        assertNotNull(w.delete("D2"));
        assertNotNull(w.delete("D3"));
        
        String result = w.printbintree();
        assertNotNull(result);
    }
    
    /**
     * Test InternalNode with only left child - covers line 567-571
     */
    public void testInternalNodeOnlyLeftChild() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Create split
        assertTrue(w.add(new Balloon("B1", 10, 10, 10, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(
        		new Balloon("B2", 600, 10, 10, 20, 20, 20, "cold", 5)));
        assertTrue(w.add(new Balloon("B3", 30, 10, 10, 20, 20, 20, "warm", 5)));
        
        // Remove right side
        w.delete("B2");
        
        String result = w.printbintree();
        assertNotNull(result);
    }
    
    /**
     * Test InternalNode with only right child - covers line 570
     */
    public void testInternalNodeOnlyRightChild() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Create split
        assertTrue(w.add(new Balloon("B1", 600, 10, 10, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(new Balloon("B2", 10, 10, 10, 20, 20, 20, "cold", 5)));
        assertTrue(w.add(
        		new Balloon("B3", 700, 10, 10, 20, 20, 20, "warm", 5)));
        
        // Remove left side
        w.delete("B2");
        
        String result = w.printbintree();
        assertNotNull(result);
    }
    
    /**
     * Test InternalNode.intersect on all dimensions - covers Image 13
     */
    public void testIntersectAllDimensions() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Create objects across all dimensions
        assertTrue(w.add(
        	    new Rocket("R1", 100, 100, 100, 50, 50, 50, 100, 45.0)));
        	assertTrue(w.add(
        	    new Rocket("R2", 600, 100, 100, 50, 50, 50, 100, 45.0)));
        	assertTrue(w.add(
        	    new Rocket("R3", 100, 600, 100, 50, 50, 50, 100, 45.0)));
        	assertTrue(w.add(
        	    new Rocket("R4", 100, 100, 600, 50, 50, 50, 100, 45.0)));

        
        // Intersect query that spans multiple nodes
        String result = w.intersect(50, 50, 50, 600, 600, 600);
        assertNotNull(result);
        assertTrue(result.contains("Rocket R1"));
        assertTrue(result.contains("nodes were visited"));
    }
    
    /**
     * Test InternalNode.collisions on all dimensions - covers Image 13 bottom
     */
    public void testCollisionsAllDimensions() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Create overlapping objects
        assertTrue(w.add(new AirPlane(
        	    "A1", 100, 100, 100, 100, 100,
        	    100, "United", 100, 2)));
        assertTrue(w.add(new AirPlane(
        	    "A2", 150, 150, 150, 100, 100,
        	    100, "Delta", 200, 2)));
        assertTrue(w.add(new AirPlane(
        	    "A3", 600, 600, 600, 100, 100,
        	    100, "Southwest", 300, 2)));
        
        String result = w.collisions();
        assertNotNull(result);
        assertTrue(result.contains("collisions exist"));
    }
    
    /**
     * Test collision with containsPoint check - covers Image 7 line 607
     */
    public void testCollisionContainsPoint() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Create colliding objects in specific leaf
        assertTrue(w.add(
        		new Drone("D1", 100, 100, 100, 80, 80, 80, "DJI", 4)));
        assertTrue(w.add(
        		new Drone("D2", 120, 120, 120, 60, 60, 60, "Parrot", 4)));
        
        String result = w.collisions();
        assertTrue(result.contains("In leaf node"));
    }
    
    /**
     * Test LeafNode.intersect without overlap - covers Image 6 line 124
     */
    public void testLeafIntersectNoOverlap() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Add object in one area
        assertTrue(w.add(new Balloon("B1", 10, 10, 10, 20, 20, 20, "hot", 5)));
        
        // Query far away area
        String result = w.intersect(800, 800, 800, 100, 100, 100);
        assertNotNull(result);
        assertFalse(result.contains("Balloon B1"));
    }
    
    /**
     * Test InternalNode.intersect without overlap - covers Image 13 line 605
     */
    public void testInternalIntersectNoOverlap() {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        
        // Create internal node
        assertTrue(w.add(new Rocket("R1", 10, 10, 10, 50, 50, 50, 100, 45.0)));
        assertTrue(w.add(new Rocket("R2", 600, 10, 10, 50, 50, 50, 100, 45.0)));
        
        // Query area with no overlap
        String result = w.intersect(400, 400, 400, 50, 50, 50);
        assertNotNull(result);
        // Should visit root but not find matches
        assertTrue(result.contains("nodes were visited"));
    }
}