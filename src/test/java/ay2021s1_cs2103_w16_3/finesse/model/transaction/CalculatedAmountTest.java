package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount.CalculatedAmount;

public class CalculatedAmountTest {

    private CalculatedAmount zero = new CalculatedAmount();
    private CalculatedAmount positive = new CalculatedAmount(new Amount("1"));
    private CalculatedAmount negative = zero.subtract(positive);

    @Test
    public void zeroValue() {
        assertEquals(BigDecimal.ZERO, zero.getValue());
        assertTrue(zero.isNonNegative());
    }

    @Test
    public void isNonNegative() {
        assertTrue(positive.isNonNegative());
        assertFalse(negative.isNonNegative());
    }

    @Test
    public void arithmeticIdentity() {
        // zero is an additive identity
        assertEquals(zero, zero.add(zero));
        assertEquals(zero, zero.subtract(zero));
        assertEquals(positive, positive.add(zero));
        assertEquals(negative, negative.add(zero));
        assertEquals(positive, positive.subtract(zero));
        assertEquals(negative, negative.subtract(zero));
    }

    @Test
    public void commutativeLaw() {
        // addition is commutative
        assertEquals(positive.add(zero), zero.add(positive));
        // subtraction is not commutative
        assertNotEquals(positive.subtract(zero), zero.subtract(positive));
    }

    @Test
    public void associativeLaw() {
        // addition is associative
        assertEquals(zero.add(positive).add(positive), zero.add(positive.add(positive)));
        // subtraction is not associative
        assertNotEquals(zero.subtract(positive).subtract(positive), zero.subtract(positive.subtract(positive)));
    }

    @Test
    public void stringFormat() {
        assertEquals("$0.00", zero.toString());
        assertEquals("$1.00", positive.toString());
        assertEquals("$1.00", negative.toString());
    }

}
