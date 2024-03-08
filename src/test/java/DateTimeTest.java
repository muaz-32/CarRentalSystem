import code.DateTime;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateTimeTest {

    @Test
    public void diffDays_shouldReturnCorrectDifference() {
        DateTime startDate = new DateTime(1, 1, 2022);
        DateTime endDate = new DateTime(1, 2, 2022);
        int diff = DateTime.diffDays(endDate, startDate);
        assertEquals(31, diff);
    }

    @Test
    public void diffDays_shouldReturnZeroForSameDates() {
        DateTime startDate = new DateTime(1, 1, 2022);
        DateTime endDate = new DateTime(1, 1, 2022);
        int diff = DateTime.diffDays(endDate, startDate);
        assertEquals(0, diff);
    }

    @Test
    public void diffDays_shouldReturnNegativeForEndDateBeforeStartDate() {
        DateTime startDate = new DateTime(1, 2, 2022);
        DateTime endDate = new DateTime(1, 1, 2022);
        int diff = DateTime.diffDays(endDate, startDate);
        assertEquals(-31, diff);
    }

    @Test
    public void getNameOfDay_shouldReturnCorrectDayName() {
        DateTime dateTime = new DateTime(1, 1, 2022); // This is a Saturday
        assertEquals("Saturday", dateTime.getNameOfDay());
    }

    @Test
    public void getFormattedDate_shouldReturnCorrectFormat() {
        DateTime dateTime = new DateTime(1, 1, 2022);
        assertEquals("01/01/2022", dateTime.getFormattedDate());
    }

    @Test
    public void getEightDigitDate_shouldReturnCorrectFormat() {
        DateTime dateTime = new DateTime(1, 1, 2022);
        assertEquals("01012022", dateTime.getEightDigitDate());
    }

    @Test
    public void constructor_shouldInitializeWithCurrentTimeWhenNoArguments() {
        DateTime dateTime = new DateTime();
        assertNotNull(dateTime.getTime());
    }

    @Test
    public void constructor_shouldInitializeWithAdvancedTimeWhenDaysProvided() {
        DateTime dateTime = new DateTime(1);
        long expectedTime = System.currentTimeMillis() + ((1 * 24L) * 60L) * 60000L;
        assertEquals(expectedTime, dateTime.getTime(), 1000);
    }

    @Test
    public void constructor_shouldInitializeWithAdvancedTimeWhenStartDateAndDaysProvided() {
        DateTime startDate = new DateTime();
        DateTime dateTime = new DateTime(startDate, 1);
        long expectedTime = startDate.getTime() + ((1 * 24L) * 60L) * 60000L;
        assertEquals(expectedTime, dateTime.getTime(), 1000);
    }

    @Test
    public void constructor_shouldInitializeWithSpecificDateWhenDayMonthYearProvided() {
        DateTime dateTime = new DateTime(1, 1, 2022);
        assertEquals("01/01/2022", dateTime.getFormattedDate());
    }

    @Test
    public void getTime_shouldReturnCorrectTime() {
        long currentTime = System.currentTimeMillis();
        DateTime dateTime = new DateTime();
        assertTrue(Math.abs(currentTime - dateTime.getTime()) < 1000);
    }

    @Test
    public void getCurrentTime_shouldReturnFormattedCurrentTime() {
        String currentTime = DateTime.getCurrentTime();
        DateTime dateTime = new DateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String expectedDate = sdf.format(new Date(dateTime.getTime()));
        assertEquals(expectedDate, currentTime);
    }

    @Test
    public void setAdvance_shouldAdvanceTimeBySpecifiedDaysHoursAndMinutes() {
        DateTime dateTime = new DateTime();
        dateTime.setAdvance(1, 1, 1);
        long advancedTime = System.currentTimeMillis() + ((1 * 24L + 1) * 60L + 1) * 60000L;
        assertTrue(Math.abs(advancedTime - dateTime.getTime()) < 1000);
    }
}