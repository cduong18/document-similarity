/*
 * NAME:
 * PID:
 */

public class LineCounter {
    
    
    /**
    * Method to print the filename to console
    */
    public static void printFileName(String filename) {
        System.out.println("\n"+filename+":");
    }

    /**
    * Method to print the statistics to console
    */
    
    public static void printStatistics(String compareFileName, int percentage) {
        System.out.println(percentage+"% of lines are also in "+compareFileName);
    }
    
    public static void main(String[] args) {
        
        if(args.length < 2) {
            System.err.println("Invalid number of arguments passed");
            return;
        }
        
        int numArgs = args.length;
        
        //Create a hash table for every file
        HashTable[] tableList = new HashTable[numArgs];
        
        //Preprocessing: Read every file and create a HashTable
        
        for(int i=0; i < numArgs; i++) {
            
            //TODO
        }
        
        //Find similarities across files
        
        for(int i=0; i < numArgs; i++) {
            
            //TODO
            
        }
    }

}
