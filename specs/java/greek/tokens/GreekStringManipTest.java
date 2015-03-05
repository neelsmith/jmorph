package greek.tokens;

//import org.concordion.integration.junit4.ConcordionRunner;
//import org.junit.runner.RunWith;
import org.concordion.integration.junit3.ConcordionTestCase;

import edu.holycross.shot.greekutils.GreekString;

/* Run this class as a JUnit test. */

//@RunWith(ConcordionRunner.class)
public class GreekStringManipTest extends ConcordionTestCase {


    public boolean isAlphabetic(String str) {
	return GreekString.isAlphabetic(str);
    }

    public boolean isConsonant(String str) {
	return GreekString.isConsonant(str);
    }
    
    public boolean isVowel(String str) {
	return GreekString.isVowel(str);
    }


    public boolean isAccent(String str) {
	return GreekString.isAccent(str);
    }


    public boolean isBreathing(String str) {
	return GreekString.isBreathing(str);
    }



    public boolean isValid(String str) {
	return GreekString.isValidChar(str);
    }

    public boolean isPunctuation(String str) {
	return GreekString.isPunctuation(str);
    }



    public boolean isDiphthong(String str) {
	return GreekString.isDiphthong(str);
    }
}
