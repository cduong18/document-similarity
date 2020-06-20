/**
 * Title: HashTableTester.java
 * Description: File that can tests the implementation of HashTable.java
 */


import org.junit.Test;
import static org.junit.Assert.*;

public class HashTableTester {

    HashTable noFile = new HashTable(10);
    HashTable withFile = new HashTable(4, "testingFile");
    // tested using different sizes to see if varying outputs changed


    @Test(expected = NullPointerException.class)
    public void testNullPointerExceptionInInsert() {
        noFile.insert(null);
        withFile.insert(null);
    }

    @Test
    public void testInsert() {
        assertEquals(0, noFile.getSize());
        noFile.insert("mom"); //9
        noFile.insert("dad"); //7
        noFile.insert("....."); //0
        noFile.insert("10142000"); //2
        noFile.insert("testing123"); //6
        noFile.insert("omm"); //9
        assertEquals(6, noFile.getSize());
        noFile.insert("pneumonoultramicroscopicsilicovolcanoconiosis..."); //18
        assertEquals(7, noFile.getSize());
        // should resize to be 20 buckets and rehash
        noFile.insert("!"); // 13
        noFile.insert("3"); // 11
        assertEquals(9, noFile.getSize());
        assertFalse(noFile.insert("....."));
        assertTrue(noFile.insert("hello"));
        assertEquals(10, noFile.getSize());

        assertEquals(0, withFile.getSize());
        withFile.insert("ahh"); // 5
        withFile.insert("hello mate"); // 12
        withFile.insert("... dsc30 is hard ..."); // 12
        withFile.insert("IDONTKNOWIFIWILLPASS"); // 10
        withFile.insert("123456789");
        withFile.insert("spring break");
        withFile.insert("ayokay!");
        withFile.insert("tEsTiNg");
        withFile.insert("SHIBAINU");
        withFile.insert("0987654321");
        assertFalse(withFile.insert("SHIBAINU"));
        withFile.insert("this should cause a rehash");
        assertEquals(11, withFile.getSize());
        withFile.printTable();
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerExceptionInLookup() {
        noFile.lookup(null);
        withFile.lookup(null);
    }

    @Test
    public void testLookup() {
        assertEquals(0, noFile.getSize());
        noFile.insert("mom");
        assertTrue("mom", noFile.lookup("mom"));
        assertFalse("dad", noFile.lookup("dad"));
        noFile.insert("dad");
        assertTrue("dad", noFile.lookup("dad"));
        assertFalse("!", noFile.lookup("!"));
        noFile.delete("mom");
        assertFalse("mom", noFile.lookup("mom"));
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerExceptionInDelete() {
        noFile.delete(null);
        withFile.delete(null);
    }

    @Test
    public void testDelete() {
        assertEquals(0, noFile.getSize());
        noFile.insert("mom");
        assertTrue("mom", noFile.lookup("mom"));
        noFile.insert("dad");
        noFile.insert(".....");
        noFile.insert("10142000");
        noFile.insert("testing123");
        noFile.insert("omm");
        noFile.insert("pneumonoultramicroscopicsilicovolcanoconiosis...");
        assertEquals(7, noFile.getSize());
        assertTrue(noFile.delete("mom"));
        assertFalse(noFile.delete("mom"));
        assertFalse("mom", noFile.lookup("mom"));
    }

    @Test
    public void testGetSize() {
        assertEquals(0, noFile.getSize());
        noFile.insert("mom");
        assertEquals(1, noFile.getSize());
        noFile.insert("dad");
        assertEquals(2, noFile.getSize());
        noFile.insert(".....");
        assertEquals(3, noFile.getSize());
        noFile.insert("10142000");
        assertEquals(4, noFile.getSize());
        noFile.insert("testing123");
        assertEquals(5, noFile.getSize());
        noFile.insert("omm");
        assertEquals(6, noFile.getSize());
        noFile.insert("pneumonoultramicroscopicsilicovolcanoconiosis...");
    }
}