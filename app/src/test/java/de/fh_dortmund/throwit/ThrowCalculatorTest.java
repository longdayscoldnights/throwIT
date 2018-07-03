package de.fh_dortmund.throwit;


import org.apache.commons.math3.util.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
        tc.add(2.1, 0L);
        assertTrue(tc.calculateHeight() == 0.0);
    }

    @Test
    public void throwCalculator_addTest() {
        ThrowCalculator tc = new ThrowCalculator();
        assertTrue(tc.add(1.2,0L));
    }


    @Test
    public void throwCalculator_10Values() {
        ThrowCalculator tc = new ThrowCalculator();
        tc.add(2.4, 0L);
        tc.add(2.5, 10L);
        tc.add(2.6, 20L);
        tc.add(2.5, 30L);
        tc.add(2.4, 40L);
        tc.add(2.3, 50L);
        tc.add(2.2, 60L);
        tc.add(2.1, 70L);
        tc.add(2.0, 80L);
        tc.add(1.9, 90L);
        tc.add(1.7, 100L);
        for(Pair<Double, Long> p: tc.getList())
            System.out.println(""+ p.getFirst() + " " + p.getSecond());
        assertEquals(21, tc.calculateHeight(), 1.0);
    }

}
