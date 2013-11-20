package englishNumbers;

import java.io.PrintStream;

/**
 * A singleton for managing errors during English-to-number
 * parsing. Logs error messages to a specified
 * stream.
 * 
 */
class ErrorManager {
    
    /** The singleton */
    private static ErrorManager instance = new ErrorManager();

    /* Constants for output display */
    private static final String preErr  = "[ERROR] ";
    private static final String preInternal = 
            "Internal error. Please e-mail the following to help@case.edu :\n";

    /* Exit codes */
    private static int EXIT_OK = 0;
    private static int EXIT_ERR = 7;
    
    /** Where to display error messages */
    private PrintStream stderr = System.err;
    /** Number of error and exception messages written */
    private int errorsWritten = 0;

    /** Creates the singleton */
    private ErrorManager(){ }
    
    /** Returns the ErrorManager (a singleton) */
    static ErrorManager getInstance(){
        return instance;
    }
    
    /**
     * Sets the stream for which error messages
     * should go. By default, this is System.err.
     * 
     * @param stderr the stream to direct error messages
     */
    void setErrorOutput(PrintStream stderr){
        assert (stderr != null) : "Cannot set the error output stream to null";
        this.stderr = stderr;
    }
    
    /**
     * Writes the given message to the error
     * output, as an error.
     * 
     * @param format a format string (use of %n is discouraged)
     * @param args the arguments of the formatted string
     */
    void error(String format, Object... args) {
        String message = String.format(format, args);
        stderr.println(preErr + message);
        errorsWritten++;
    }
    
    /**
     * Displays the message and stack
     * trace of an exception to the
     * error output.
     * 
     * @param e the exception to log
     */
    void exception(Exception e){
        assert (e != null);
        
        stderr.print(preErr + preInternal);
        // Message printed with this call
        e.printStackTrace(stderr);
        errorsWritten++;
    }
    
    /**
     * Clean up all error-reporting
     * resources.
     */
    void cleanup(){
        stderr.close();
    }
    
    /** Separated for testing */
    int getExitCode(){
        return (errorsWritten > 0) ? EXIT_ERR : EXIT_OK;
    }
    
}
