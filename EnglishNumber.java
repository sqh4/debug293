package englishNumbers;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a number in English.
 * Only supports integers between
 * -999999999 and 999999999,
 * inclusive.
 * 
 */
public class EnglishNumber {
    /*
     * IMPORTANT!
     * Within this class, the wrapper type
     * Boolean (capital B) is sometimes used as a
     * triple-valued return type. It means:
     * 
     * true - success, we are done parsing
     * false - failure, we have to stop parsing
     * null - this routine was successful, but
     *        we should continue parsing
     */
    
    /**
     * The English representation as a set of tokens.
     * Will be null if not initialized properly 
     */
    private List<NumberToken> tokens = null;
    /** Where in the tokens we are currently parsing */
    private int position;
    
    /** Is this number negative? */
    private boolean negative = false;
    /** Digits for millions group */
    private int[] millions  = {0, 0, 0};
    /** Digits for thousands group */
    private int[] thousands = {0, 0, 0};
    /** Digits for units group */
    private int[] units     = {0, 0, 0};
    
    /* Digit indexes for the above */
    static final int HUNDREDS_DIG = 0;
    static final int TENS_DIG = 1;
    static final int ONES_DIG = 2;
    
    /** Error manager */
    private static ErrorManager em = ErrorManager.getInstance();
    /** Cached numeric value */
    private int numericValue = Integer.MIN_VALUE;
    
    /**
     * Parses an English Number from a list
     * of Strings.
     * 
     * @param in the words representing the number
     * @return true if there was an error parsing, false otherwise
     */
    public boolean initialize(List<String> in) {
        assert (in != null) : "Cannot initialize with a null list";
        assert (tokens == null) : "Cannot reinitialize EnglishNumber";
        
        // Tokenize input
        tokens = toTokens(in);
        if (tokens == null){
            return false;
        }
        position = 0;
        
        // Parse
        boolean result = parse();
        if (!result){
            tokens = null;
            return false;
        }
        
        // Success! Cache as int
        numericValue = interpetAsInt();
        return true;
    }
    
    /**
     * Returns the numeric value,
     * or {@link Integer#MIN_VALUE} if
     * not successfully initialized.
     */
    public int toInt(){
        if (tokens == null){ return Integer.MAX_VALUE; }
        
        return numericValue;
    }
    
    /**
     * Returns the sequence of original words,
     * separated by one space (" ") each,
     * or "uninitialized" if not successfully
     * initialized.
     */
    @Override
    public String toString(){
        if (tokens == null){ return "uninitialized"; }
        
        StringBuilder sb = new StringBuilder();
        for (NumberToken nt : tokens){
            sb.append(nt.original + " ");
        }
        return sb.toString().trim();
    }
    
    /**
     * Interprets this EnglishNumber as an
     * integer.
     * 
     * @return the value of this 
     */
    private int interpetAsInt(){
        
        int toReturn = 0;
        toReturn += millions[HUNDREDS_DIG] * 100 * 1000000;
        toReturn += millions[TENS_DIG] * 10 * 1000000;
        toReturn += millions[ONES_DIG] * 1 * 1000000;
        
        toReturn += thousands[HUNDREDS_DIG] * 100 * 1000;
        toReturn += thousands[TENS_DIG] * 10 * 1000;
        toReturn += thousands[ONES_DIG] * 1 * 1000;
        
        toReturn += units[HUNDREDS_DIG] * 100;
        toReturn += units[TENS_DIG] * 10;
        toReturn += units[ONES_DIG] * 1;
        
        return negative ? -toReturn : toReturn;
    }
    
    /*
     * Lexing
     */
    
    /**
     * Converts a list of words to
     * NumberTokens.
     * 
     * @param in
     * @return null if there was a lexing error,
     *         a list of NumberTokens otherwise.
     */
    private static List<NumberToken> toTokens(List<String> in){
        assert (in != null) : "Cannot tokenize a null list";
        
        List<NumberToken> toReturn = new ArrayList<NumberToken>(in.size()-1);
        
        for (String s : in){
            NumberToken toAdd = new NumberToken(s);
            
            // If we couldn't create a valid token, return failure
            if (toAdd.type == null){
                return null;
            }
            assert(toAdd.value != 0) : "No value should be zero - it should be -1 if N/A";
            toReturn.add(toAdd);
        }
        
        if (toReturn.size() == 0){
            em.error("No tokens.");
            return null;
        }
        
        return toReturn;
    }

    /*
     * Primary Parsing
     */
    
    /**
     * Returns the current token without
     * affecting the token list.
     * 
     * @return null if at end of list,
     *         the token otherwise
     */
    private NumberToken peek(){
        if (position < tokens.size()){
            return tokens.get(position);
        }
        return null;
    }
    
    /**
     * Returns the current token
     * and advances to the next token.
     * 
     * @return null if at end of list,
     *         the token otherwise
     */
    private NumberToken consume(){
        if (position < tokens.size()){
            return tokens.get(position++);
        }
        return null;
    }
    
    /**
     * Parses the set of tokens.
     * 
     * @return true iff parsing was successful
     */
    private boolean parse(){
        // Parse tokens that can only come at the start
        Boolean success = parseZeroOrMinus();
        if (success != null){
            return success;
        }
        
        // Parse the rest of the tokens
        return parseStart();
    }
    
    /**
     * Processes tokens that can only occur
     * at the start of the list, ZERO and MINUS.
     * 
     * @return Boolean.True if caller should return success
     *         Boolean.False if caller should return failure
     *         null if the caller should continue processing 
     */
    private Boolean parseZeroOrMinus(){
        // Is only token ZERO?
        if (peek().type == TokenType.ZERO){
            return consumeZero();
        }
        
        // Is first token MINUS?
        if (peek().type == TokenType.MINUS){
            return consumeMinus();
        }
        
        return null;
    }
    
    /**
     * Given that the next token is ZERO,
     * consumes it and handles the requirement
     * that no more tokens must exist.
     * 
     * @return whether the parsing was successful
     */
    private Boolean consumeZero() {
        NumberToken zero = consume();
        // all triplets are already zero
        
        if (peek() != null){
            em.error("After \"%s\", additional token detected: \"%s\"", zero, peek());
            return false;
        }
        return true;
    }
    
    /**
     * Given that the next token is MINUS,
     * consumes it and handles the requirement
     * that more tokens must exist.
     * 
     * @return false if parsing has failed, null if it should continue
     */
    private Boolean consumeMinus(){
        NumberToken minus = consume();
        negative = true;
        
        if (peek() == null){
            em.error("Expected additional tokens after \"%s\"", minus);
            return false;
        }
        return null;
    }
    
    /**
     * Parses the first set of tokens,
     * which can be the millions, thousands, or
     * units based on what we find afterward.
     * 
     * @return true iff parsing was successful
     */
    private boolean parseStart(){
        // There MUST be tokens left at the start
        
        int[] parsed = parsePrefix();
        if (parsed == null){
            // Error parsing
            return false;
        }
        
        NumberToken next = peek();
        if (next == null){
            // Units, can't parse any more
            units = parsed;
            return true;
        } else if (next.type == TokenType.MILLION){
            // Millions, parse again for thousands and below
            millions = parsed;
            consume();
            return parseAfterMillion();
        } else if (next.type == TokenType.THOUSAND){
            // Thousands, parse again for units
            thousands = parsed;
            consume();
            return parseAfterThousand();
        }
        
        em.error("Expected million, thousand, or end-of-file; got \"%s\"", next);
        return false;
    }
    
    /**
     * Parses a set of tokens after seeing
     * MILLION, which can be the thousands or units
     * based on what we find afterward.
     * 
     * @return true iff parsing was successful
     */
    private boolean parseAfterMillion(){
        // If no more tokens, it's still valid
        if (peek() == null){
            return true;
        }
        
        int[] parsed = parsePrefix();
        if (parsed == null){
            // Error parsing
            return false;
        }
        
        NumberToken next = peek();
        if (next == null){
            // Units, can't parse any more
            units = parsed;
            return true;
        } else if (next.type == TokenType.THOUSAND){
            // Thousands, parse again for units
            thousands = parsed;
            consume();
            return parseAfterThousand();
        }
        
        em.error("Expected thousand or end-of-file; got \"%s\"", next);
        return false;
    }
    
    /**
     * Parses a set of tokens after seeing
     * THOUSAND, which means this set can only
     * be units
     * 
     * @return true iff parsing was successful
     */
    private boolean parseAfterThousand(){
        // If no more tokens, it's still valid
        if (peek() == null){
            return true;
        }
        
        int[] parsed = parsePrefix();
        if (parsed == null){
            // Error parsing
            return false;
        }
        
        if (peek() == null){
            units = parsed;
            return true;
        }
        
        System.out.println("Expected end of file; got \"%s\" " + peek());
        return false;
    }
    
    /*
     * Parsing of triplets
     * (what would be separated by commas in conventional
     *  American usage)
     */
    
    /**
     * Parse the "prefix" part of a triplet
     * (e.g., <prefix> thousand).
     * 
     * @return null if there was a parsing error;
     *         otherwise, a representation of the digits,
     *         indexed by {@link #HUNDREDS_DIG}, {@link #TENS_DIG},
     *         and {@link #ONES_DIG}.
     */
    private int[] parsePrefix() {
        assert (peek() != null) : "parsePrefix() can't start at EOF";
        
        int[] toReturn = {0, 0, 0};
        
        // First, parse a digit, if we begin with one
        Boolean quitNow = parseFirstDigit(toReturn);
        if (quitNow == Boolean.TRUE){
            return toReturn;
        }
        
        // Parse whatever's left
        switch(peek().type){
            case NTY:
                parseNty(toReturn);
                break;
            case TEEN:
                toReturn[TENS_DIG] = 1;
                toReturn[ONES_DIG] = consume().value;
                break;
            case DIGIT:
                assert(toReturn[HUNDREDS_DIG] != 0) : "We should've parsed this above";
                toReturn[ONES_DIG] = consume().value;
                break;
            default:
                if (toReturn[HUNDREDS_DIG] == 0){
                    em.error("Unexpected token: \"%s\"", peek());
                    return null;
                }
                break;
        }
        
        return toReturn;
    }
    
    /**
     * Parse the first digit of a triplet,
     * if one exists.
     * 
     * @param triplet the triplet array to modify
     * @return true if we are done parsing this triplet,
     *         null if we should continue
     */
    private Boolean parseFirstDigit(int[] triplet){
        if (peek().type != TokenType.DIGIT){ return null; }
        
        // If we start with a digit, it could either
        // be the entire triplet or it could be before a HUNDRED
        int dValue = consume().value;
        
        // If next token isn't HUNDRED, this digit is in the one's place
        if (peek() == null || peek().type != TokenType.HUNDRED){
            triplet[ONES_DIG] = dValue;
            return true;
        }
        
        // Otherwise it's in the hundred's place
        triplet[HUNDREDS_DIG] = dValue;
        consume(); // HUNDRED consumed
        
        // If we're EOF after that, we're done with triplet
        NumberToken next = peek();
        if (next == null){
            return true;
        }
        
        return null;
    }
    
    /**
     * Parses the last two digits of a triplet, given
     * that the next token is NTY.
     * 
     * @param triplet the triplet array to modify
     */
    private void parseNty(int[] triplet){
        assert(peek().type == TokenType.NTY);
        
        triplet[TENS_DIG] = consume().value;
        if (peek() != null && peek().type == TokenType.DIGIT){
            triplet[ONES_DIG] = consume().value;
        }
    }

}
