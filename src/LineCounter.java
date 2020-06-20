import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A class that counts common lines in various files
 */

public class LineCounter {


    /**
     * Method to print the filename to console
     */
    public static void printFileName(String filename) {
        System.out.println("\n" + filename + ":");
    }

    /**
     * Method to print the statistics to console
     */

    public static void printStatistics(String compareFileName, int percentage) {
        System.out.println(percentage + "% of lines are also in " + compareFileName);
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Invalid number of arguments passed");
            return;
        }

        int numArgs = args.length;

        //Create a hash table for every file
        HashTable[] tableList = new HashTable[numArgs];

        //Pre-processing: Read every file and create a HashTable
        for (int i = 0; i < numArgs; i++) {
            // create new hash table for file
            // add hash table into tableList
            HashTable file = new HashTable(20);
            tableList[i] = file;
            Scanner scanner = null;
            try {
                scanner = new Scanner(new File(args[i]));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (scanner.hasNextLine()) {
                // while the file has another line
                // add that line into the hash table
                String line = scanner.nextLine();
                file.insert(line);
            }
        }

        //Find similarities across files
        for (int i = 0; i < numArgs; i++) {
            // print once for every file
            printFileName(args[i]);

            int totalLines = 0;
            int[] overlappedLines = new int[numArgs];
            double percentage;

            for (int k = 0; k < numArgs; k++) {
                overlappedLines[k] = 0;
            }

            Scanner scanner2 = null;
            try {
                scanner2 = new Scanner(new File(args[i]));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (scanner2.hasNextLine()) {
                totalLines++;
                // while the file has another line
                String line = scanner2.nextLine();
                for (int j = 0; j < numArgs; j++) {
                    if (i != j && tableList[j].lookup(line)) {
                        overlappedLines[j]++;
                    }
                }
            }

            for (int j = 0; j < numArgs; j++) {
                if (i != j) {
                    percentage = (double) (overlappedLines[j] * 100) / totalLines;
                    // print once for every comparison
                    printStatistics(args[j], (int) Math.floor(percentage));
                }
            }
        }
    }
}

