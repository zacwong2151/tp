package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateTimeTest {

    @Test
    public void isValidDateTime_notTwoComponents_returnFalse() {
        String testDate1 = "some";
        String testDate2 = "31/12/2023";
        String testDate3 = "31/12/2023 2359 latest";

        assertFalse(DateTime.isValidDateTime(testDate1));
        assertFalse(DateTime.isValidDateTime(testDate2));
        assertFalse(DateTime.isValidDateTime(testDate3));
    }

    @Test
    public void isValidDateTime_dateNotThreeComponents_returnFalse() {
        String testDate1 = "31/12 2359";
        String testDate2 = "31/12/230/2 2359";
        String testDate3 = "31 2359";

        assertFalse(DateTime.isValidDateTime(testDate1));
        assertFalse(DateTime.isValidDateTime(testDate2));
        assertFalse(DateTime.isValidDateTime(testDate3));
    }

    @Test
    public void isValidDateTime_timeNotNumber_returnFalse() {
        String testDate1 = "31/12/2023 11:59am";
        String testDate2 = "31/12/2023 11:59";
        String testDate3 = "31/12/2023 bruh";

        assertFalse(DateTime.isValidDateTime(testDate1));
        assertFalse(DateTime.isValidDateTime(testDate2));
        assertFalse(DateTime.isValidDateTime(testDate3));
    }

    @Test
    public void isValidDateTime_timeNotFourDigit_returnFalse() {
        String testDate1 = "31/12/2023 11595";
        String testDate2 = "31/12/2023 100";
        String testDate3 = "31/12/2023 -221";

        assertFalse(DateTime.isValidDateTime(testDate1));
        assertFalse(DateTime.isValidDateTime(testDate2));
        assertFalse(DateTime.isValidDateTime(testDate3));
    }

    @Test
    public void isValidDateTime_null_returnFalse() {
        assertFalse(DateTime.isValidDateTime(null));
    }

    @Test
    public void isValidDateTime_validParams_success() {
        assertTrue(DateTime.isValidDateTime("1/1/2023 2359"));
        assertTrue(DateTime.isValidDateTime("31/12/2023 2359"));
        assertTrue(DateTime.isValidDateTime("31/1/2023 1100"));
        assertTrue(DateTime.isValidDateTime("1/11/2023 1100"));
    }

    @Test
    public void toString_validParams_success() {

        // array of { expected, actual }
        String[][] testDates = {
                {"1/1/2023 2359", "1/1/2023 2359"},
                {"31/12/2023 2359", "31/12/2023 2359"},
                {"31/1/2023 1100", "31/1/2023 1100"},
                {"1/11/2023 1100", "1/11/2023 1100"},
                {"01/01/2023 2359", "1/1/2023 2359"},
                {"31/01/2023 1100", "31/1/2023 1100"},
                {"01/11/2023 1100", "1/11/2023 1100"}
        };

        for (String[] testDate : testDates) {
            DateTime d = new DateTime(testDate[0]);
            assertEquals(d.toString(), testDate[1]);
        }
    }
}
