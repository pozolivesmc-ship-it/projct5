import java.util.Random;
import student.TestCase;

/**
 * @author {Giovanni Garcia}
 * @version {12.4.2025}
 */
public class AirControlTest extends TestCase {

    /**
     * Sets up the tests that follow. In general, used for initialization.
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
     * This test for when delete deals with a null value
     */
    public void testDeleteNull()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertNull(w.delete(null));
    }
    /**
     * This test for when print deals with a null value
     */
    public void testPrintNull()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertTrue(w.add(new Balloon("B1",
            10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertNull(w.print(null));
        
    }
    /**
     * This test for bad bird or drone input
     */
    public void testBadInput2()
    {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertFalse(w.add(new Drone("Air2",
            100, 1010, 101, 924, 2, 0, "Droners", 3)));
        assertFalse(w.add(new Drone("Air2",
            2000, 1010, 101, 924, 2, 0, "Droners", 3)));
        assertFalse(w.add(new Bird("pterodactyl",
            -1, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertFalse(w.add(new Bird("pterodactyl",
            1, 100, 20, 0, 50, 50, "Dinosaur", 1)));
    }
    

    /**
     * Extra coverage for Bintree helper methods and null inserts.
     */
    public void testBintreeHelpers()
    {
        assertTrue(Bintree.overlap(0, 0, 0, 2, 2, 2, 1, 1, 1, 2, 2, 2));
        assertFalse(Bintree.overlap(0, 0, 0, 1, 1, 1, 2, 2, 2, 1, 1, 1));
        assertTrue(Bintree.containsPoint(1, 1, 1, 1, 1, 1, 2, 2, 2));
        assertFalse(Bintree.containsPoint(5, 5, 5, 0, 0, 0, 2, 2, 2));

        Bintree tree = new Bintree();
        tree.insert(null);
        assertFuzzyEquals(
            "E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                + "1 Bintree nodes printed\r\n",
            tree.printTree());
    }

    /**
     * Covers ordering, expansion, removal, and intersection checks in LeafNode.
     */
    public void testLeafNodeOperations()
    {
        LeafNode leaf = new LeafNode();
        AirObject z = new Balloon("Z", 0, 0, 0, 10, 10, 10, "hot", 1);
        AirObject a = new Balloon("A", 20, 20, 20, 10, 10, 10, "hot", 2);
        AirObject m = new Balloon("M", 40, 40, 40, 10, 10, 10, "hot", 3);
        AirObject b = new Balloon("B", 60, 60, 60, 10, 10, 10, "hot", 4);
        AirObject c = new Balloon("C", 80, 80, 80, 10, 10, 10, "hot", 5);

        leaf.addObject(z);
        leaf.addObject(a);
        leaf.addObject(m);
        leaf.addObject(b);
        leaf.addObject(c);

        assertEquals(5, leaf.getSize());
        assertEquals(a, leaf.getObject(0));
        assertEquals(z, leaf.getObject(4));

        BinNode afterRemove = leaf.remove(a, 0, 0, 0, 0, 0, 0, 0);
        assertSame(leaf, afterRemove);
        assertEquals(4, leaf.getSize());

        afterRemove = leaf.remove(z, 0, 0, 0, 0, 0, 0, 0);
        afterRemove = afterRemove.remove(m, 0, 0, 0, 0, 0, 0, 0);
        afterRemove = afterRemove.remove(b, 0, 0, 0, 0, 0, 0, 0);
        BinNode empty = afterRemove.remove(c, 0, 0, 0, 0, 0, 0, 0);
        assertSame(Bintree.FLYWEIGHT, empty);

        LeafNode overlapLeaf = new LeafNode();
        overlapLeaf.addObject(new AirPlane("AA", 0, 0, 0, 10, 10, 10,
            "A", 1, 1));
        overlapLeaf.addObject(new AirPlane("AB", 5, 5, 5, 10, 10, 10,
            "A", 1, 1));
        assertTrue(overlapLeaf.hasCommonIntersection());

        LeafNode disjointLeaf = new LeafNode();
        disjointLeaf.addObject(new Drone("DA", 0, 0, 0, 5, 5, 5,
            "D", 1));
        disjointLeaf.addObject(new Drone("DB", 20, 20, 20, 5, 5, 5,
            "D", 1));
        assertFalse(disjointLeaf.hasCommonIntersection());
    }

    /**
     * Ensures leaf splitting creates an InternalNode and removal merges back.
     */
    public void testLeafSplitAndInternalMerge()
    {
        AirObject a = new Balloon("A1", 0, 0, 0, 10, 10, 10, "hot", 1);
        AirObject b = new Balloon("B1", 100, 0, 0, 10, 10, 10, "hot", 1);
        AirObject c = new Balloon("C1", 200, 0, 0, 10, 10, 10, "hot", 1);
        AirObject d = new Balloon("D1", 300, 0, 0, 10, 10, 10, "hot", 1);

        LeafNode leaf = new LeafNode();
        BinNode node = leaf.insert(a, 0, 0, 0, Bintree.WORLD_SIZE,
            Bintree.WORLD_SIZE, Bintree.WORLD_SIZE, 0);
        node = node.insert(b, 0, 0, 0, Bintree.WORLD_SIZE, Bintree.WORLD_SIZE,
            Bintree.WORLD_SIZE, 0);
        node = node.insert(c, 0, 0, 0, Bintree.WORLD_SIZE, Bintree.WORLD_SIZE,
            Bintree.WORLD_SIZE, 0);
        node = node.insert(d, 0, 0, 0, Bintree.WORLD_SIZE, Bintree.WORLD_SIZE,
            Bintree.WORLD_SIZE, 0);

        assertTrue(node instanceof InternalNode);
        StringBuilder sb = new StringBuilder();
        node.print(sb, 0, 0, 0, Bintree.WORLD_SIZE, Bintree.WORLD_SIZE,
            Bintree.WORLD_SIZE, 0);
        assertTrue(sb.toString().contains("I ("));

        Bintree tree = new Bintree();
        tree.insert(a);
        tree.insert(b);
        tree.insert(c);
        tree.insert(d);
        tree.remove(d);

        String merged = tree.printTree();
        assertTrue(merged.contains("Leaf with 3 objects"));
        assertFalse(merged.startsWith("I ("));
    }
    
    
    
    
    
}