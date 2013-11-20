/**
 * 
 */
package englishNumbers;

import static org.junit.Assert.*;
import java.util.*;


import org.junit.Before;
import org.junit.Test;

/**
 * @author Shehab
 *
 */
public class EnglishNumberTest {
	
	EnglishNumber en1, en10, enZero, enNeg, enNtys, enHundreds, enThousands, enComboPrefix, enMillions;
	String[] test1 = {"one"};
	String[] test10 = {"ten"};
	String[] testZero = {"zero"};
	String[] testNeg = {"minus", "one"};
	String[] testNtys = {"sixty", "seven"};
	String[] testHundreds = {"three","hundred","twenty","six"};
	String[] testThousands = {"two", "thousand", "five"};
	String[] testComboPrefix = {"negative", "five", "hundred","thirteen", "thousand", "one", "hundred", "twelve"};
	String[] testMillions = {"nine", "hundred", "ninety", "nine", "million", "nine", "hundred", "ninety", "nine", "thousand", "nine", "hundred", "ninety", "nine"}; 


	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		en1 = new EnglishNumber();
		en10 = new EnglishNumber();
		enZero = new EnglishNumber();		
		enNeg = new EnglishNumber();
		enNtys = new EnglishNumber();
		enHundreds = new EnglishNumber();
		enThousands = new EnglishNumber();
		enComboPrefix = new EnglishNumber();
		enMillions = new EnglishNumber();
		en1.initialize(Arrays.asList(test1));
		en10.initialize(Arrays.asList(test10));
		enZero.initialize(Arrays.asList(testZero));
		enNeg.initialize(Arrays.asList(testNeg));
		enNtys.initialize(Arrays.asList(testNtys));
		enHundreds.initialize(Arrays.asList(testHundreds));
		enThousands.initialize(Arrays.asList(testThousands));
		enComboPrefix.initialize(Arrays.asList(testComboPrefix));
		enMillions.initialize(Arrays.asList(testMillions));
	}

	/**
	 * Test method for {@link englishNumbers.EnglishNumber#initialize(java.util.List)}.
	 */
	@Test
	public void testInitialize() {
		assertTrue("Error Initializing " + test1.toString(), en1.initialize(Arrays.asList(test1)));
		assertTrue("Error Initializing " + test10.toString(), en10.initialize(Arrays.asList(test10)));
		assertTrue("Error Initializing " + testZero.toString(), enZero.initialize(Arrays.asList(testZero)));
		assertTrue("Error Initializing " + testNeg.toString(), enNeg.initialize(Arrays.asList(testNeg)));
		assertTrue("Error Initializing " + testNtys.toString(), enNtys.initialize(Arrays.asList(testNtys)));
		assertTrue("Error Initializing " + testHundreds.toString(), enHundreds.initialize(Arrays.asList(testHundreds)));
		assertTrue("Error Initializing " + testThousands.toString(), enThousands.initialize(Arrays.asList(testThousands)));
		assertTrue("Error Initializing " + testComboPrefix.toString(), enComboPrefix.initialize(Arrays.asList(testComboPrefix)));
		assertTrue("Error Initializing " + testMillions.toString(), enMillions.initialize(Arrays.asList(testMillions)));
	}

	/**
	 * Test method for {@link englishNumbers.EnglishNumber#toInt()}.
	 */
	@Test
	public void testToInt() {
		assertEquals("Does not match expected toInt() value of 1", 1, en1.toInt());
		assertEquals("Does not match expected toInt() value of 10", 10, en10.toInt());
		assertEquals("Does not match expected toInt() value of 0", 0, enZero.toInt());
		assertEquals("Does not match expected toInt() value of -1", -1, enNeg.toInt());
		assertEquals("Does not match expected toInt() value of 67", 67, enNtys.toInt());
		assertEquals("Does not match expected toInt() value of 326", 326, enHundreds.toInt());
		assertEquals("Does not match expected toInt() value of 2005", 2005, enThousands.toInt());
		assertEquals("Does not match expected toInt() value of -513112",  -513112, enComboPrefix.toInt());
		assertEquals("Does not match expected toInt() value of 999999999", 999999999, enMillions.toInt());
	}

	/**
	 * Test method for {@link englishNumbers.EnglishNumber#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals("Does not match expected toString() matching 1", "one", en1.toString());
		assertEquals("Does not match expected toString() matching 10", "ten", en10.toString());
		assertEquals("Does not match expected toString() matching 0", "zero", enZero.toString());
		assertEquals("Does not match expected toString() matching -1", "minus one", enNeg.toString());
		assertEquals("Does not match expected toString() matching 67", "sixty seven", enNtys.toString());
		assertEquals("Does not match expected toString() matching 326", "three hundred twenty six", enHundreds.toString());
		assertEquals("Does not match expected toString() matching 2005", "two thousand five", enThousands.toString());
		assertEquals("Does not match expected toString() matching -513112", 
				"negative five hundred thirteen thousand one hundred twelve", enComboPrefix.toString());
		assertEquals("Does not match expected toString() matching 999999999", 
				"nine hundred ninety nine million nine hundred ninety nine thousand nine hundred ninety nine", enMillions.toString());
	}

}
