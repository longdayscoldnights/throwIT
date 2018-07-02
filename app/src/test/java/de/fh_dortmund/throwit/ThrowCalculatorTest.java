package de.fh_dortmund.throwit;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import de.fh_dortmund.throwit.menu.ThrowCalculator;

public class ThrowCalculatorTest {


    @Test
    public void throwCalculator_NoValues_ReturnZero() {
        assertTrue(new ThrowCalculator().calculateHeight() == 0.0);
    }

    @Test
    public void throwCalculator_OneValue() {
        ThrowCalculator tc = new ThrowCalculator();
        tc.add(2.1, 1L);
        assertTrue(tc.calculateHeight() == 0.0);
    }


    @Test
    public void throwCalculator_10Values() {
        ThrowCalculator tc = new ThrowCalculator();
        tc.add(2.1, 1L);
        tc.add(2.2, 2L);
        tc.add(2.3, 3L);
        tc.add(2.4, 4L);
        tc.add(2.3, 5L);
        tc.add(2.2, 6L);
        tc.add(2.1, 7L);
        tc.add(2.0, 8L);
        tc.add(1.9, 9L);
        tc.add(1.7, 10L);
        tc.add(1.5, 11L);

        assertTrue(tc.calculateHeight() == 0.0);
    }

}
