package nopackage;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for English-to-int
 * number parser. Accepts input
 * until standard in closes (^D).
 * 
 */
public class EntryPoint {    
    private static ErrorManager em = ErrorManager.getInstance();
    
    /**
     * Entry point to English number parser.
     * Exits with the appropriate exit code.
     * 
     * @param args ignored
     */
    public static void main(String[] args) {
        run(System.in, System.out, System.err);
        System.exit(em.getExitCode());
    }
    
    /**
     * Parses an English Number from the given InputStream.
     * If successful, writes the numeric value to the first
     * given PrintStream. Errors are written to
     * the second given PrintStream.
     * <p>
     * This is separated from the {@link #main(String[])}
     * because the latter exits the JVM. This also
     * makes it much easier to test.
     * 
     * @param input the input stream to read from until it
     *        is closed
     */
    public static void run(InputStream input, PrintStream output, PrintStream error){
        em.setErrorOutput(error);
        
        // Get the words from standard in
        List<String> words = new ArrayList<String>();
        Scanner in = new Scanner(input);
        while (in.hasNext()){
            String toAdd = in.next();
            words.add(toAdd);
        }
            
        // Create a number and use the words to initialize
        EnglishNumber theNumber = new EnglishNumber();
        boolean success = theNumber.initialize(words);
        if (success){
            output.println(theNumber.toInt());
        }
    }
}
