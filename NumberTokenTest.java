package englishNumbers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NumberTokenTest {
	
	NumberToken digit, teen, nty, thousand, million, minus, zero;
	

	@Before
	public void setUp() throws Exception {
		digit = new NumberToken("six");
		teen = new NumberToken("twelve");
		nty = new NumberToken("forty");
		thousand = new NumberToken("thousand");
		million = new NumberToken("million");
		minus = new NumberToken("negative");
		zero = new NumberToken("naught");
	}

	/*
	 * Test all hash codes against each other to verify uniqueness
	 */
	@Test 
	public void testHashCode() {
		NumberToken[] tokens = {digit, teen, nty, thousand, million, minus, zero};
		for(int i = 0; i < tokens.length; i++){
			for(int j = 0; j < tokens.length; j++){
				if( i != j)
					assertNotSame("Shared hashcode"/*"NumberToken " + tokens[i].toString() + " shares hashcode with " + tokens[i].toString()*/, 
							tokens[i].hashCode(), tokens[j].hashCode());
			}
		}
	}


	@Test
	public void testToString() {
		NumberToken[] tokens = {digit, teen, nty, thousand, million, minus, zero};
		String[] stringTokens = {"six", "twelve", "forty", "thousand", "million", "negative", "naught"};
		for(int i = 0; i < tokens.length; i++){
			assertEquals("toString() and input string do not match ", stringTokens[i], tokens[i].toString());
		}
		
	}

	@Test
	public void testEqualsObject() {
		assertTrue(digit.equals(new NumberToken("six")));
		assertTrue(teen.equals(new NumberToken("twelve")));
		assertTrue(nty.equals(new NumberToken("forty")));
		assertTrue(thousand.equals(new NumberToken("thousand")));
		assertTrue(million.equals(new NumberToken("million")));
		assertTrue(minus.equals(new NumberToken("minus")));
		assertTrue(zero.equals(new NumberToken("zero")));
	}

}
