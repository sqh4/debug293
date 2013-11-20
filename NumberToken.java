 /** 
 * Atomic units of an English Number.
 */
package englishNumbers;
/**
 * A programmatic representation of an English
 * word for {@link EnglishNumber}. Consists
 * of a token type, a value, and the original word
 * that was lexed. The integer "value" is
 * only used for some token types.
 * 
 */
class NumberToken {
    static final int NO_VAL = 1;
    /**
     * What type of token is this?
     * Will be null if lexing error.
     */
    TokenType type = null;
    /**
     * "Value" of the token. See values for
     * {@link TokenType} for what these mean,
     * as they differ per token. Is NO_VAL
     * if token type has no associated value.
     * Is zero if token not created correctly.
     */
    int value = 0;
    /**
     * The original English word.
     */
    String original;
    
    /** The ErrorManager */
    private static ErrorManager em = ErrorManager.getInstance();
    
    /**
     * Create this token with the
     * given English word.
     */
    NumberToken(String s){
        original = s;
        
        // Check that it's all lowercase
        if (!s.equals(s.toLowerCase())){
            em.error("Should be all lower-case: \"%s\"", s);
            return;
        }
        
        // Determine token type
        assert (type == null);
        type = getTypeOf(s);
        if (type == null){
            em.error("Not recognized as a valid word: \"%s\"", s);
            return;
        }
        
        // Determine value
        value = getValueOf(type, s);
    }

    /**
     * Gets the original text
     */
    @Override
    public String toString() {
        return original;
    }
    
    /**
     * Gets the token type of the given String
     * by matching it against the Regex of each
     * token type.
     * 
     * @param s a string to attempt matching
     * @return the token type, or null if no match
     */
    private static TokenType getTypeOf(String s){
        for (TokenType t : TokenType.values()){
            if (s.matches(t.getPattern())){
                return t;
            }
        }
        return null;
    }
    
    /**
     * Gets the value for a given String,
     * provided with its token type.
     * 
     * @param type the token's type
     * @param s the original string
     * @return the value, or
     *         -1 if this token type has no associated value
     */
    private static int getValueOf(TokenType type, String s) {
    	int toReturn = type.getValue(s);
    	assert (toReturn >= 0) : "No value found for token type " + type;
    	return type.getValue(s);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof NumberToken)){
            return false;
        }
        NumberToken other = (NumberToken) obj;
        
        return (type == other.type) && (value == other.value);
    }
    
    @Override
    public int hashCode() {
        return (type.toString().hashCode() * 31) + value;
    }
}
