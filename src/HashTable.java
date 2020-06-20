/**
 * Title: HashTable.java
 * Description: File that creates a hash table.
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class that implements a hash table and its associated methods
 */
public class HashTable implements IHashTable {
    private LinkedList<String>[] array; // Array that stores LinkedLists
    private int numElem; // Number of element in the hash table
    private int expand; // Number of expansions
    private int collision; // Number of collisions occurs
    private String statsFileName;
    private boolean printStats = false;
    private int numLetters = 27;
    private int multiplier = 2;
    private int[] collisionArray; // used to later calculate longest collision chain

    /**
     * Constructor for hash table
     * @param size : int to denote size of hash table
     */
    @SuppressWarnings("unchecked")
    public HashTable(int size) {
        this.numElem = 0;
        this.expand = 0;
        this.collision = 0;
        this.array = new LinkedList[size]; // creating an array with size 'size'
        this.collisionArray = new int[size]; // creating a collision array with size 'size'
        this.statsFileName = "None";

        for (int i = 0; i < size; i++) {
            // adding empty LinkedList to each index of array
            this.array[i] = new LinkedList<>();
        }

        for (int i = 0; i < size; i++) {
            // adding a '1' placeholder for each index in the collision array
            this.collisionArray[i] = 1;
        }
    }

    /**
     * Another constructor for hash table
     * @param size : int to denote size of hash table
     * @param fileName:
     */
    @SuppressWarnings( "unchecked" )
    public HashTable(int size, String fileName){
        this.numElem = 0;
        this.expand = 0;
        this.collision = 0;
        this.array = new LinkedList[size]; // creating an array with size 'size'
        this.collisionArray = new int[size]; // creating a collision array with size 'size'
        this.statsFileName = fileName;
        this.printStats = true; // we want to print stats if this constructor is used

        for (int i = 0; i < size; i++) {
            // adding empty LinkedList to each index of array
            this.array[i] = new LinkedList<>();
        }

        for (int i = 0; i < size; i++) {
            // adding a '1' placeholder for each index in the collision array
            this.collisionArray[i] = 1;
        }
    }

    /**
     * Insert 'value' into the hash table
     * @param value: value to insert
     * @return true if the value was inserted, false if the value was already present
     */
    @Override
    public boolean insert(String value) {
        // throws exception if null value is passed
        if (value == null) {
            throw new NullPointerException();
        }
        // continue if value is not in the hash table
        if (!lookup(value)) {
            float loadFactor = (float) Math.round((double) (this.numElem + 1) / array.length * 100) / 100;
            if (loadFactor > 0.67) {
                // expand and rehash if load factor > 2/3
                this.rehash();
            }
            // index : hashed value of input value
            // if index is not empty, collision increases by 1
            int index = hashString(value);
            if (!this.array[index].isEmpty()) {
                this.collision++;
                this.collisionArray[index]++;
            }
            // add value to LinkedList located at index 'index' of array
            this.array[index].addLast(value);
            this.numElem++;
            return true;
        }
        // return false if value is in the hash table already
        return false;
    }

    /**
     * Delete 'value from the hash table
     * @param value: value to delete
     * @return true if the value was deleted, false if the value was not found
     */
    @Override
    public boolean delete(String value) {
        // throws exception if null value is passed
        if (value == null) {
            throw new NullPointerException();
        }
        // if 'value' in hash table, continue
        if (lookup(value)) {
            int index = hashString(value);
            array[index].remove(value);
            this.numElem--;
            return true;
        }
        // if 'value' is not in hash table, return false
        return false;
    }

    /**
     * Check if 'value' is in the hash table
     * @param value: value to look up
     * @return true if the value was found, false if the value was not found
     */
    @Override
    public boolean lookup(String value) {
        // throws exception if null value is passed
        if (value == null) {
            throw new NullPointerException();
        }
        int index = hashString(value);
        // if 'index' of hash table is empty, return false
        if (array[index].isEmpty()) {
            return false;
        } else if (array[index].contains(value)) {
            return true;
        }
        // return false if LinkedList at 'index' of hash table doesn't contain 'value'
        return false;
    }

    /**
     * Prints the contents of the hash table to stdout.
     */
    @Override
    public void printTable() {
        for (int i = 0; i < array.length; i++) {
            // empty indices print out as 'index:'
            if (array[i].isEmpty()) {
                System.out.println(i + ":");
                continue;
            }
            // filled indices print out as 'index: value1, value2, ...'
            String word = array[i].remove();
            System.out.print(i + ": " + word);
            while (!array[i].isEmpty()) {
                word = array[i].remove();
                System.out.print(", " + word);
            }
            System.out.println();
        }
    }
    /**
     * @return the number of elements stored in the hash table
     */
    @Override
    public int getSize() {
        return this.numElem;
    }

    /**
     * @return the hashed value of the input string
     */
    private int hashString(String value) {
        int hashValue = 0;
        for (int i = 0; i < value.length(); i++) {
            char letter = value.charAt(i);
            int asciiLetter = (int) letter;
            hashValue = (hashValue + asciiLetter) % array.length;
        }
        return hashValue;
    }
    
    /**
     * Expands the array and rehashes all values.
     */
    @SuppressWarnings( "unchecked" )
    private void rehash() {
        // if second constructor was used and rehash is called
        // must print statistics
        if (this.printStats) {
            this.expand++;
            this.printStatistics();
        }
        // new hash table has length of old length * multiplier
        int prevLength = this.array.length;
        int newLength = prevLength * multiplier;
        // temp array now contains contents of this.array
        LinkedList<String>[] temp = new LinkedList[prevLength];
        System.arraycopy(this.array, 0, temp, 0, this.array.length);
        // reset variables as needed
        this.array = new LinkedList[newLength];
        this.collision = 0;
        this.collisionArray = new int[newLength];

        // adding an empty LinkedList to every index of new hash table
        for (int i = 0; i < newLength; i++) {
            this.array[i] = new LinkedList<>();
        }

        for (int i = 0; i < temp.length; i++) {
            int loops = temp[i].size();
            for (int j = 0; j < loops; j++) {
                String removed = temp[i].remove();
                int index = hashString(removed);
                if (!this.array[index].isEmpty()) {
                    this.collision++;
                    this.collisionArray[index]++;
                }
                this.array[index].addLast(removed);
            }
        }
    }
    /**
     * Print statistics to the given file.
     * @return True if successfully printed statistics, false if the file
     *         could not be opened/created.
     */
    //@Override
    public boolean printStatistics() {
        if (this.statsFileName.equals("None")) {
            PrintStream printThis = null;
            try {
                printThis = new PrintStream(new FileOutputStream(this.statsFileName, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int r = this.expand; // number of resizes or expansions of hash table
            float a = (float) Math.round((double) (this.numElem + 1) / array.length * 100) / 100; // load factor
            int c = this.collision; // number of total collisions
            int n = getMaxCollision(); // longest collision chain

            String output = Integer.toString(r) + " resizes, load factor "
                    + Float.toString(a) + ", " + Integer.toString(c) + " collisions, "
                    + Integer.toString(n) + " longest chain \n";

            printThis.print(output);
            return true;
        }
        return false;
    }

    /**
     * Calculates the longest collision chain
     * @return number of collisions in longest collision chain
     */
    private int getMaxCollision() {
        int maxValue = this.collisionArray[0];
        // loops through the array to find the largest value
        for (int i = 1; i < this.collisionArray.length; i++) {
            if (this.collisionArray[i] > maxValue) {
                maxValue = this.collisionArray[i];
            }
        }
        return maxValue;
    }
}
