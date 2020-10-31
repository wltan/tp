package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // blank date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(Date.isValidDate("04/11")); // missing year
        assertFalse(Date.isValidDate("06102020")); // missing separators

        // invalid parts
        assertFalse(Date.isValidDate("01/02/03/04")); // more than 3 parts
        assertFalse(Date.isValidDate("ab/cd/efgh")); // not numeric
        assertFalse(Date.isValidDate("01/Jan/1970")); // not numeric
        assertFalse(Date.isValidDate("6/10/2020")); // leading zeroes are required
        assertFalse(Date.isValidDate("06/10/20")); // year must be 4 digits
        assertFalse(Date.isValidDate("32/09/2020")); // day is not valid
        assertFalse(Date.isValidDate("30/02/2019")); // day is not valid
        assertFalse(Date.isValidDate("03/13/2020")); // month is not valid
        assertFalse(Date.isValidDate("01/01/0000")); // year is not valid

        // date from the future is not allowed
        LocalDate pastDate = LocalDate.of(2020, 10, 16);
        assertFalse(Date.isValidDate("17/10/2020", Clock.fixed(
                pastDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault())));

        // valid date
        assertTrue(Date.isValidDate("01/01/0001")); // earliest allowed date
        assertTrue(Date.isValidDate("06/10/2020")); // 6 October 2020
    }

    @Test
    public void equals_distinctDatesWithSameAttributes_returnsTrue() {
        Date firstDate = new Date("22/09/2000");
        Date secondDate = new Date("22/09/2000");
        assertNotSame(firstDate, secondDate);
        assertEquals(firstDate, secondDate);
    }

    @Test
    public void hashCode_distinctDatesWithSameAttributes_returnsTrue() {
        Date firstDate = new Date("22/09/2000");
        Date secondDate = new Date("22/09/2000");
        assertNotSame(firstDate, secondDate);
        assertEquals(firstDate.hashCode(), secondDate.hashCode());
    }
}
